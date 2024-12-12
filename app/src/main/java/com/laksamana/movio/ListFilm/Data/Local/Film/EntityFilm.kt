package com.laksamana.movio.ListFilm.Data.Local.Film

import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO: Struktur EntityFilm mirip dengan DtoFilm, perbedaaannya yaitu dimana DtoFilm melakukan transfer data, Entity berfungsi menyimpan data didalam database app
@Entity
data class EntityFilm(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
//TODO: Menetapkan id pada list film sebagai primary key.
    @PrimaryKey
    val id: Int,

    val category: String,
)

