package com.example.applicationquiz.ecran

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.applicationquiz.R

@Composable
fun EcranResultat(scores: Map<String, Int>,
                  onRestartQuiz: () -> Unit){
    Surface(modifier = Modifier
        .fillMaxSize(),
        color = colorResource(R.color.beige_background),
    ) {
        val classement = scores
            .entries
            .sortedByDescending { it.value }
        val top3 = classement.take(3)
        val dernier = classement.lastOrNull()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = stringResource(R.string.titre_resultat),
                modifier = Modifier.padding(top = 90.dp),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(R.color.marron)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.sous_titre_resultat),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 32.dp, top = 32.dp),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Start,
                color = colorResource(R.color.marron_clair)
            )
            top3.forEachIndexed { index, profil ->
                val medaille = when (index) {
                    0 -> "🥇"
                    1 -> "🥈"
                    else -> "🥉"
                }
                Card(
                    colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.marron_container)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "$medaille ${profil.key}\n${profil.value} points",
                        modifier = Modifier.fillMaxWidth()
                            .padding(16.dp),
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            if (dernier != null) {
                Text(
                    text = stringResource(R.string.dernier_profil) +
                            "\n         ${dernier.key} : ${dernier.value} points",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Start,
                    color = Color.Black
                )
            }
            Button(
                onClick = {
                    onRestartQuiz()
                },
                modifier = Modifier.padding(top = 50.dp)
                    .size(width = 300.dp, height = 60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.marron_fonce),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Recommencer le quiz",
                    fontSize = 18.sp
                )
            }
        }
    }
}