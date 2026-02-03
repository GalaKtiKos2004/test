package com.example.test.ui.quiz

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import com.example.test.data.Question

sealed class QuizScreen {
    object Welcome : QuizScreen()
    data class Question(val question: com.example.test.data.Question, val questionNumber: Int, val totalQuestions: Int) : QuizScreen()
    data class Result(val correctAnswers: Int, val totalQuestions: Int) : QuizScreen()
}

data class QuizUiState(
    val currentScreen: QuizScreen = QuizScreen.Welcome,
    val currentQuestionIndex: Int = 0,
    val selectedAnswerIndex: Int? = null,
    val correctAnswersCount: Int = 0,
    val questions: List<Question> = emptyList()
) {
    val isAnswerSelected: Boolean
        get() = selectedAnswerIndex != null
    
    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)
    
    val isLastQuestion: Boolean
        get() = currentQuestionIndex >= questions.size - 1
    
    companion object {
        // Для rememberSaveable сохраняем только примитивные значения
        // Экран определяется по currentQuestionIndex и наличию questions
        val Saver: Saver<QuizUiState, *> = listSaver(
            save = { state ->
                listOf(
                    state.currentQuestionIndex,
                    state.selectedAnswerIndex ?: -1,
                    state.correctAnswersCount,
                    if (state.currentScreen is QuizScreen.Result) 1 else 0
                )
            },
            restore = { saved ->
                // Восстанавливаем только базовые значения, questions будут загружены заново
                QuizUiState(
                    currentQuestionIndex = saved[0] as Int,
                    selectedAnswerIndex = (saved[1] as Int).takeIf { it >= 0 },
                    correctAnswersCount = saved[2] as Int,
                    questions = emptyList() // Будет загружено заново
                )
            }
        )
    }
}
