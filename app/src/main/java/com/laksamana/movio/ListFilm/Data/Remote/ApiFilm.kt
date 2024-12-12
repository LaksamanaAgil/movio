package com.laksamana.movio.ListFilm.Data.Remote

import com.laksamana.movio.ListFilm.Data.Remote.RespNod.DtoListFilm
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//TODO: MERUPAKAN API YANG AKAN DIGUNAKAN UNTUK MEMBUAT APLIKASI INI
interface ApiFilm {
//TODO: Request data, serta mendefinisikan jenis data variabel kategori, page/halaman, dan Key API
    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
        ): DtoListFilm
    //TODO: Link untuk pengambilan data, serta key API untuk mengakses API
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "f0362ecb1bbb9fcaf4dc1439d1d6e6a7"
    }

}