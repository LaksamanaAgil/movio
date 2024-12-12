package com.laksamana.movio.ListFilm.Data.Local.Film

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
//TODO: DAO/ Data Access Object digunakan untuk mengambil data dari ROOM database
@Dao
interface DaoFilm {
    @Upsert //TODO: Mengupdate dan memasukkan data dari Entity ke ListFilm
    suspend fun upsertMovieList(movieList: List<EntityFilm>)

    @Query("SELECT * FROM EntityFilm WHERE id = :id") //TODO: Pencarian Film berdasarkan ID
    suspend fun getMovieById(id: Int): EntityFilm

    @Query("SELECT * FROM EntityFilm WHERE category = :category") //TODO: Pencarian film berdasarkan kategori film
    suspend fun getMovieListByCategory(category: String): List<EntityFilm>
}
