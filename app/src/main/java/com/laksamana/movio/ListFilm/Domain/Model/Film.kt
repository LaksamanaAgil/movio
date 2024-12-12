package com.laksamana.movio.ListFilm.Domain.Model



data class Film( //TODO: Kelas Film berfungsi sebagai domain layer, yang mengambil data dari data layer dan menjalankan logika bisnis agar dapat digunakan ulang berkali -kali di beberapa class file
    val adult: Boolean, //TODO: Value yang terdapat dalam sebuah detail film, apakah film dewasa
    val backdrop_path: String, //TODO: value lain: ID film, id genre film, bahasa, judul, popularitas, dsb
    val genre_ids: List<Int>,
    val id: Int,
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

    val category: String, //TODO: kategori film, apakah film yang sudah dirilis atau upcoming
)
