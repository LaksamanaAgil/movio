package com.laksamana.movio.ListFilm.Util

sealed class Screen(val rout: String) { //TODO: Class screen berisi objek- objek yang dapat dipanggil yang akan digunakan untuk menampilkan tampilan berbeda
    object Home : Screen("main") //TODO: Halaman default saat membuka app
    object PopularMovieList : Screen("popularMovie") //TODO: HAlaman berdasarkan film populer
    object UpcomingMovieList : Screen("upcomingMovie") //TODO: HAlaman berdasarkan film yang akan datang
    object Details : Screen("details") //TODO: Detail sebuah film spesifik
}
