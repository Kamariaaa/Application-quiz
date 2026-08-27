package com.example.applicationquiz.ecran

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.applicationquiz.R
import com.example.applicationquiz.donnee.RepQuiz

@Composable
fun EcranQuiz(onQuizFinished: (Map<String, Int>) -> Unit,
              modifier: Modifier = Modifier) {
    Surface(
        color = colorResource(R.color.beige_background)
    ) {
        val context = LocalContext.current
        val repository = RepQuiz(context)
        val questions = repository.lectureQuestion()
        var questionActuelle by remember { mutableIntStateOf(0) }
        val progression = (questionActuelle + 1).toFloat() / questions.size
        val scores = remember {
            mutableStateMapOf<String, Int>()
        }
        val reponsesChoisies = remember { mutableStateMapOf <Int, Int>() }
        Column(
            modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = { progression },
                modifier = Modifier.fillMaxWidth(),
                color = colorResource(R.color.marron)
            )

            Text(
                text = "${questionActuelle + 1} / ${questions.size}",
                modifier
                    .fillMaxWidth()
                    .padding(end = 12.dp),
                textAlign = TextAlign.End,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = questions[questionActuelle].text,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.marron)
                )
            }

            Spacer(
                modifier.height(32.dp)
            )

            repository.reponses.forEach { reponse ->
                val selection =
                    reponsesChoisies[questionActuelle] == reponse.points
                Button(
                    onClick = {
                        val question = questions[questionActuelle]
                        val ancienneReponse = reponsesChoisies[questionActuelle]

                        if (ancienneReponse != null) {
                            scores[question.profil] =
                                (scores[question.profil] ?: 0) - ancienneReponse
                        }

                        scores[question.profil] =
                            (scores[question.profil] ?: 0) + reponse.points
                        reponsesChoisies[questionActuelle] = reponse.points

                        if (questionActuelle < questions.size - 1) {
                            questionActuelle++
                        } else {
                            onQuizFinished(scores)
                        }
                    },
                    modifier
                        .size(width = 350.dp, height = 70.dp)
                        .padding(7.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selection)
                            colorResource(R.color.marron_container)
                        else
                            colorResource(R.color.marron_clair),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        reponse.text,
                        fontSize = 18.sp
                    )
                }
            }
            Button(
                onClick = {
                    if (questionActuelle > 0) {
                        questionActuelle--
                    }
                    },
                enabled = questionActuelle > 0,
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier.align(Alignment.End)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.marron_fonce),
                    contentColor = Color.White
                )
            ){
                Text(stringResource(R.string.precedent))
            }

        }
    }
}
