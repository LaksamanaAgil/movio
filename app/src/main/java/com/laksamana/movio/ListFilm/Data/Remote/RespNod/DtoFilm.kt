package com.laksamana.movio.ListFilm.Data.Remote.RespNod
//TODO: DTO ini berfungsi mentransfer data mengenai detail sebuah film
data class DtoFilm( //TODO: Data dibawah yang akan ditransfer: Age Rating Film, backdrop, bahasa film, popularitas, skor, jumlah review,dsb
    val adult: Boolean?, //Rating umur
    val backdrop_path: String?, // backdrop
    val genre_ids: List<Int>?, //genre
    val id: Int?, //id film
    val original_language: String?, //bahasa asli film
    val original_title: String?, //judul asli
    val overview: String?, //pembahasan film yang singkat
    val popularity: Double?, //popularitas
    val poster_path: String?, //poster/gambar
    val release_date: String?, // tanggal rilis
    val title: String?, //judul
    val video: Boolean?, //video
    val vote_average: Double?, // mean skor
    val vote_count: Int? // total vote skor
)