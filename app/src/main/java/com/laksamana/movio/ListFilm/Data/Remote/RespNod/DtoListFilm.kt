package com.laksamana.movio.ListFilm.Data.Remote.RespNod
//TODO: DTO atau Data Transfer Object berfungsi untuk mentransfer data dari database API yang digunakan, kedalam Entity
data class DtoListFilm( //TODO: Data yang ditransfer yaitu halaman, halaman total, hasiil/result film yang ada, dan jumlah total result
    val page: Int,
    val results: List<DtoFilm>,
    val total_pages: Int,
    val total_results: Int
)