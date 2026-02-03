package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.test.ui.quiz.QuizScreen
import com.example.test.ui.quiz.QuizStateHolder
import com.example.test.ui.quiz.QuestionScreen
import com.example.test.ui.quiz.ResultScreen
import com.example.test.ui.quiz.WelcomeScreen
import com.example.test.ui.theme.TestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun QuizApp(
    modifier: Modifier = Modifier
) {
    // Используем rememberSaveable для сохранения состояния при повороте экрана
    var currentQuestionIndex by rememberSaveable { mutableStateOf(0) }
    var selectedAnswerIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var correctAnswersCount by rememberSaveable { mutableStateOf(0) }
    
    // Создаем state holder один раз, восстанавливая из сохраненного состояния
    val stateHolder = remember {
        QuizStateHolder(
            initialQuestionIndex = currentQuestionIndex,
            initialSelectedAnswerIndex = selectedAnswerIndex,
            initialCorrectAnswersCount = correctAnswersCount
        )
    }
    
    // Синхронизируем сохраненное состояние с состоянием state holder при его изменении
    androidx.compose.runtime.LaunchedEffect(stateHolder.uiState.currentQuestionIndex, stateHolder.uiState.selectedAnswerIndex, stateHolder.uiState.correctAnswersCount) {
        currentQuestionIndex = stateHolder.uiState.currentQuestionIndex
        selectedAnswerIndex = stateHolder.uiState.selectedAnswerIndex
        correctAnswersCount = stateHolder.uiState.correctAnswersCount
    }
    
    val uiState = stateHolder.uiState
    
    when (val screen = uiState.currentScreen) {
        is QuizScreen.Welcome -> {
            WelcomeScreen(
                onStartClick = { stateHolder.startQuiz() },
                modifier = modifier
            )
        }
        is QuizScreen.Question -> {
            QuestionScreen(
                question = screen.question,
                questionNumber = screen.questionNumber,
                totalQuestions = screen.totalQuestions,
                selectedAnswerIndex = uiState.selectedAnswerIndex,
                onAnswerSelected = { stateHolder.selectAnswer(it) },
                onNextClick = { stateHolder.nextQuestion() },
                modifier = modifier
            )
        }
        is QuizScreen.Result -> {
            ResultScreen(
                correctAnswers = screen.correctAnswers,
                totalQuestions = screen.totalQuestions,
                onTryAgainClick = { stateHolder.resetQuiz() },
                modifier = modifier
            )
        }
    }
}