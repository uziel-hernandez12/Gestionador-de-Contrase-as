package com.example.gestionadordecontraseas.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.gestionadordecontraseas.R

data class Componente(

    @DrawableRes val imageResourceId: Int,
    val nombre: String,
    val contraseña: String,
    val esFavorita: Boolean
) {

}

val componentes = listOf(
    Componente(R.drawable.facebook_icon,"Facebook", "uziel1290", true),
    Componente(R.drawable.instagram_icon,"Instagram","uzielitomix", false),
    Componente(R.drawable.tiktok_icon,"Tik Tok", "uziel1228", false),
    Componente(R.drawable.mercado_pago_icon,"Mercado Pago", "jonathan78", true),
    Componente(R.drawable.google_icon,"Google", "hernandez58", true),

    )