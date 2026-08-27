package com.example.applicationquiz.ecran

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.applicationquiz.R

@Composable
fun CollecteInfo(onStartQuiz: () -> Unit,
                 modifier: Modifier = Modifier){
    var nom by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var emailErreur by remember { mutableStateOf(false) }
    var numero by remember { mutableStateOf("") }
    var numeroErreur by remember { mutableStateOf(false) }
    val numeroSansEspace = numero.replace(" ", "")
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.testpersonnalite),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.7F,
            modifier = Modifier.fillMaxSize()
        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.beige_background)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(28.dp)
                .align(Alignment.Center)
        ) {
            Column(
                modifier = modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(emailErreur){
                    Text(
                        text = "Adresse mail invalide",
                        color = Color.Red
                    )
                }
                if (numeroErreur){
                    Text(
                        text = "Numéro invalide",
                        color = Color.Red
                    )
                }
                Text(
                    text = stringResource(R.string.titre),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.marron),
                    modifier = modifier.padding(bottom = 50.dp)
                )
                MonTextField(
                    valeur = nom,
                    contenu = {nom = it},
                    label = stringResource(R.string.field_nom),
                    erreur = false
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                MonTextField(
                    valeur = email,
                    contenu = { email = it },
                    label = stringResource(R.string.field_mail),
                    erreur = emailErreur
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                MonTextField(
                    valeur = numero,
                    contenu = {nouveauText ->
                        if (nouveauText.all { it.isDigit() || it == ' '}){
                            numero = nouveauText
                        }
                    },
                    label = stringResource(R.string.field_numero),
                    erreur = numeroErreur
                )
                Button(
                    onClick = {
                         emailErreur = !Patterns.EMAIL_ADDRESS
                            .matcher(email)
                            .matches()
                        numeroErreur =
                            numeroSansEspace.length != 10 ||
                                    !numeroSansEspace.all { it.isDigit() }
                        if (!emailErreur && !numeroErreur){
                            onStartQuiz()
                        }
                    },
                    modifier
                        .padding(top = 32.dp, end = 8.dp)
                        .align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.marron),
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(R.string.valider))
                }
            }
        }
    }
}

@Composable
fun MonTextField(
    valeur: String,
    contenu: (String) -> Unit,
    label: String,
    erreur: Boolean
){
    OutlinedTextField(
        value = valeur,
        onValueChange = contenu,
        label = {Text(label)},
        isError = erreur,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = colorResource(R.color.marron_container),
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedIndicatorColor = colorResource(R.color.marron),
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        )
    )
}