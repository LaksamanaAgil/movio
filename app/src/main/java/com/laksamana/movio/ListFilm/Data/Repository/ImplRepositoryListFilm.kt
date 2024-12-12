package com.laksamana.movio.ListFilm.Data.Repository

import com.laksamana.movio.ListFilm.Data.Local.Film.DatabaseFilm
import com.laksamana.movio.ListFilm.Data.Mapper.toMovie
import com.laksamana.movio.ListFilm.Data.Mapper.toMovieEntity
import com.laksamana.movio.ListFilm.Data.Remote.ApiFilm
import com.laksamana.movio.ListFilm.Domain.Model.Film
import com.laksamana.movio.ListFilm.Domain.Repository.RepositoryListFilm
import com.laksamana.movio.ListFilm.Util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class ImplRepositoryListFilm @Inject constructor(  //TODO: Constructor Injection yaitu menggunakan constructor untuk menambahkan dependency esbagai parameter didalam constructor
    private val movieApi: ApiFilm, //TODO: Menetapkan file Pengaturan API yang akan digunakan
    private val movieDatabase: DatabaseFilm //TODO: Menetapkan database untuk film
) : RepositoryListFilm { //TODO: Menggunakan repository List FIlm agar dapat diimplementasikan

    override suspend fun getMovieList( //ToDO: Fungsi untuk mengambil List Film
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Film>>> { //TODO: Flow untuk mengatur aksi loading , serta mengambil Data Film dari Film.kt
        return flow {

            emit(Resource.Loading(true)) //TODO: Memanggil class loading dari Resource.kt

            val localMovieList = movieDatabase.DaoFilm.getMovieListByCategory(category) //TODO: Mengambil data dari database app (Yg diakses dengan DAO) untuk mengambil list film berdasarkan kategori

            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote //TODO: Mengambil data dari database local jika ada dalam database

            if (shouldLoadLocalMovie) { //TODO: Setelah loading sukses, data ditransfer dari Entity ke Domain Film
                emit(Resource.Success(
                    data = localMovieList.map { movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromApi = try { //TODO: Mencoba mengambil data list film berupa kategori dan halaman dari API
                movieApi.getMoviesList(category, page)
            } catch (e: IOException) { //TODO: Menangkap kasus error IO Exception, return flow untuk mengembalikan proses aksi dalam resource menjadi seperti semula
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: HttpException) { //TODO: Untuk kasus error HTTP Exception return flow untuk mengembalikan proses aksi dalam resource menjadi seperti semula
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: Exception) { //TODO: Untuk kasus error Exception return flow untuk mengembalikan proses aksi dalam resource menjadi seperti semula
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            val movieEntities = movieListFromApi.results.let {//TODO: Menggunakan DTO untuk transfer data dari API ke Entity Film
                it.map { DtoFilm ->
                    DtoFilm.toMovieEntity(category)
                }
            }

            movieDatabase.DaoFilm.upsertMovieList(movieEntities) //TODO: Untuk Update / Insert data film ke database

            emit(Resource.Success( //TODO: Jika berhasil loading, mengirim data dari entity ke Domain Film.kt
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false)) //TODO: Menghentikan loading

        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Film>> { //TODO: Suspend function yang akan digunakan untuk mengambil film berdasarkan ID
        return flow {

            emit(Resource.Loading(true)) //TODO: Menjalankan aksi loading data

            val movieEntity = movieDatabase.DaoFilm.getMovieById(id) //TODO: Mengambil data film berdasarkan ID dari database, diakses dengan DAO

            if (movieEntity != null) {
                emit(
                    Resource.Success(movieEntity.toMovie(movieEntity.category)) //TODO: Jika data dalam Entity sudah kosong( ditransfer ke domain Film.kt), maka proses sukses
                )

                emit(Resource.Loading(false)) //TODO: Menghentikan loading dan mengembalikan flow ke semula
                return@flow
            }

            emit(Resource.Error("Error no such movie")) //TODO: Mengembalikan pesan error saat tidak menemukan film

            emit(Resource.Loading(false)) //TODO: Menghentikan loading


        }
    }
}
