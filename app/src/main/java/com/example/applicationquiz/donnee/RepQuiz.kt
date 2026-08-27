package com.example.applicationquiz.donnee

import android.content.Context
import com.example.applicationquiz.R
import com.example.applicationquiz.models.Answer
import com.example.applicationquiz.models.Question
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RepQuiz (private val context: Context) {

    val reponses = listOf(
        Answer(context.getString(R.string.reponse1), 1),
        Answer(context.getString(R.string.reponse2), 2),
        Answer(context.getString(R.string.reponse3), 3),
        Answer(context.getString(R.string.reponse4), 4),
        Answer(context.getString(R.string.reponse5), 5)
    )

    fun lectureQuestion(): List<Question>{
        val json = context.assets
            .open("Profils.json")
            .bufferedReader()
            .use { it.readText() }
        val type = object : TypeToken<Map<String, List<String>>>() {}.type
        val data: Map<String, List<String>> = Gson().fromJson(
            json,
            type
        )

        return data.flatMap { (profile, questions) ->
            questions.map { question ->
                val id = question.substringBefore(" ")
                val text = question.substringAfter(" ")

                Question(
                    id = id,
                    text = text,
                    profil = profile
                )
            }
        }.sortedBy {
            it.id.substring(1).toInt()
        }
    }
}