package com.laksamana.movio.ListFilm.Util

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
@Composable
fun RatingBar( //TODO: FUNGSI UNTUK RATING BAR/ BARISAN RATING BERBENTUK BINTANG
    modifier: Modifier = Modifier,
    starsModifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow,
) {
    //TODO: Pengaturan satu bintang penuh- bintang kosong, dan bintang setengah penuh bersama perhitungan skornya
    val filledStars = kotlin.math.floor(rating).toInt()
    val unfilledStars = (stars - kotlin.math.ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    Row(modifier = modifier) { //TODO: Row untuk barisan 5 bintang
        repeat(filledStars) {
            Icon( //TODO: Pengaturan untuk bentuk dan warna ikon bintang penuh
                modifier = starsModifier,
                imageVector = Icons.Rounded.Star,
                contentDescription = null,
                tint = starsColor
            )
        }
        if (halfStar) { //TODO: Pengaturan untuk bentuk dan warna ikon bintang setengah penuh
            Icon(
                modifier = starsModifier,
                imageVector = Icons.Rounded.StarHalf,
                contentDescription = null,
                tint = starsColor
            )
        }
        repeat(unfilledStars) {//TODO: Pengaturan untuk bentuk dan warna ikon bintang kosong
            Icon(
                modifier = starsModifier,
                imageVector = Icons.Rounded.StarOutline,
                contentDescription = null,
                tint = starsColor
            )
        }
    }
}