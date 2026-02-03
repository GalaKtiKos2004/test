package com.example.test.ui.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.test.data.QuizRepository

class QuizStateHolder(
    initialQuestionIndex: Int = 0,
    initialSelectedAnswerIndex: Int? = null,
    initialCorrectAnswersCount: Int = 0
) {
    private val questions = QuizRepository.getQuestions()
    
    var uiState by mutableStateOf(
        createInitialState(initialQuestionIndex, initialSelectedAnswerIndex, initialCorrectAnswersCount)
    )
        private set
    
    private fun createInitialState(
        questionIndex: Int,
        selectedAnswerIndex: Int?,
        correctAnswersCount: Int
    ): QuizUiState {
        return when {
            questionIndex >= questions.size -> {
                // На экране результатов
                QuizUiState(
                    currentScreen = QuizScreen.Result(
                        correctAnswers = correctAnswersCount,
                        totalQuestions = questions.size
                    ),
                    currentQuestionIndex = questionIndex,
                    selectedAnswerIndex = selectedAnswerIndex,
                    correctAnswersCount = correctAnswersCount,
                    questions = questions
                )
            }
            questionIndex >= 0 -> {
                // На экране вопроса
                val question = questions[questionIndex]
                QuizUiState(
                    currentScreen = QuizScreen.Question(
                        question = question,
                        questionNumber = questionIndex + 1,
                        totalQuestions = questions.size
                    ),
                    currentQuestionIndex = questionIndex,
                    selectedAnswerIndex = selectedAnswerIndex,
                    correctAnswersCount = correctAnswersCount,
                    questions = questions
                )
            }
            else -> {
                // На экране приветствия
                QuizUiState(
                    currentScreen = QuizScreen.Welcome,
                    questions = questions
                )
            }
        }
    }

    fun startQuiz() {
        uiState = QuizUiState(
            currentScreen = QuizScreen.Question(
                question = QuizRepository.getQuestions().first(),
                questionNumber = 1,
                totalQuestions = QuizRepository.getQuestions().size
            ),
            currentQuestionIndex = 0,
            questions = QuizRepository.getQuestions()
        )
    }

    fun selectAnswer(answerIndex: Int) {
        uiState = uiState.copy(selectedAnswerIndex = answerIndex)
    }

    fun nextQuestion() {
        val currentState = uiState
        val currentQuestion = currentState.currentQuestion ?: return
        
        // Проверяем правильность ответа
        val isCorrect = currentState.selectedAnswerIndex == currentQuestion.correctAnswerIndex
        val newCorrectCount = if (isCorrect) {
            currentState.correctAnswersCount + 1
        } else {
            currentState.correctAnswersCount
        }

        if (currentState.isLastQuestion) {
            // Переходим на экран результатов
            uiState = QuizUiState(
                currentScreen = QuizScreen.Result(
                    correctAnswers = newCorrectCount,
                    totalQuestions = currentState.questions.size
                ),
                questions = currentState.questions
            )
        } else {
            // Переходим к следующему вопросу
            val nextIndex = currentState.currentQuestionIndex + 1
            val nextQuestion = currentState.questions[nextIndex]
            uiState = QuizUiState(
                currentScreen = QuizScreen.Question(
                    question = nextQuestion,
                    questionNumber = nextIndex + 1,
                    totalQuestions = currentState.questions.size
                ),
                currentQuestionIndex = nextIndex,
                correctAnswersCount = newCorrectCount,
                questions = currentState.questions
            )
        }
    }

    fun resetQuiz() {
        uiState = QuizUiState(questions = QuizRepository.getQuestions())
    }
}
