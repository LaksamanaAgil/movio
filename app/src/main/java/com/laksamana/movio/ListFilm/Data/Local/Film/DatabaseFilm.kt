package com.laksamana.movio.ListFilm.Data.Local.Film

import androidx.room.Database
import androidx.room.RoomDatabase
//TODO: Database yang akan digunakan untuk menyimpan data yang dikirim EntityFilm
@Database(
    entities = [EntityFilm::class],
    version = 1
)
abstract class DatabaseFilm: RoomDatabase() { //TODO: menetapkan DatabaseFilm sebagai ROOM database
    abstract val DaoFilm: DaoFilm //TODO: Mengambil DaoFilm.kt sebagai Data Access Object
}