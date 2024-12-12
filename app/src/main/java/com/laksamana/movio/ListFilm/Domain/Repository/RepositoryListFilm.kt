package com.laksamana.movio.ListFilm.Domain.Repository

import com.laksamana.movio.ListFilm.Domain.Model.Film
import com.laksamana.movio.ListFilm.Util.Resource
import kotlinx.coroutines.flow.Flow


interface RepositoryListFilm {
    suspend fun getMovieList( //TODO: Fungsi untuk mengambil data list film berupa kategori, halaman, dan boolean apakah harus mengambil dari remote
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Film>>> //TODO: Menetapkan Resource.kt sebagai flow untuk mengurus loading dan error saat kegagalan loading

    suspend fun getMovie(id: Int): Flow<Resource<Film>> //TODO: FUngsi untuk mengambil Film spesifik berdasarkan ID, dengan resource.kt sebagai flow
}