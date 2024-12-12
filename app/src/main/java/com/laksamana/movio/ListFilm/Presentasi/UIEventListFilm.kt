package com.laksamana.movio.ListFilm.Presentasi


sealed interface UIEventListFilm {
    data class Paginate(val category: String) : UIEventListFilm //TODO: Untuk membantu loading list film berdasarkan halaman kategori yang sedang ditampilkan, agar lebih efisien dalam loading data(tidak loading semua data secara langsung)
    object Navigate : UIEventListFilm //TODO: Navigasi dari tampilan ke tampilan lain
}