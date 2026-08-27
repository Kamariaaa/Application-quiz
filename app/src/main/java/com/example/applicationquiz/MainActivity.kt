package com.example.applicationquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.applicationquiz.ecran.CollecteInfo
import com.example.applicationquiz.ecran.EcranQuiz
import com.example.applicationquiz.ecran.EcranResultat
import com.example.applicationquiz.ui.theme.ApplicationQuizTheme

//ANDRIANJAFY Zaraïna N°05 L2 EXAMEN ANDROID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApplicationQuizTheme {
                Surface (modifier = Modifier.fillMaxSize()) {
                    QuizApp()
                }
            }
        }
    }
}

@Composable
fun QuizApp() {
    val navController = rememberNavController()
    var scores by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    NavHost(
        navController = navController,
        startDestination = "CollecteInfo"
    ) {
        composable("CollecteInfo") {
            CollecteInfo(
                onStartQuiz = {
                    navController.navigate("Quiz")
                }
            )
        }
        composable("Quiz") {
            EcranQuiz(
                onQuizFinished = { nouveauxScores ->
                    scores = nouveauxScores
                    navController.navigate("Resultat")
                }
            )
        }
        composable("Resultat") {
            EcranResultat(
                scores = scores,
                onRestartQuiz = {
                    scores = emptyMap()
                    navController.navigate("Quiz"){
                        popUpTo("Quiz"){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizAppPreview() {
    ApplicationQuizTheme {
        QuizApp()
    }
}