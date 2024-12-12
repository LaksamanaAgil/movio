package com.laksamana.movio.di

import android.app.Application
import androidx.room.Room
import com.laksamana.movio.ListFilm.Data.Local.Film.DatabaseFilm
import com.laksamana.movio.ListFilm.Data.Remote.ApiFilm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ModulApp {

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY //TODO: Intercfeptor untuk menyimpan log
    }

    private val client: OkHttpClient = OkHttpClient.Builder() //TODO: Mem-build OK Http Client dengan Interceptornya
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesMovieApi() : ApiFilm { //TODO: Function untuk menyediakana API dar ApiFilm.kt untuk app
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiFilm.BASE_URL)
            .client(client)
            .build()
            .create(ApiFilm::class.java)
    }

    @Provides
    @Singleton
    fun providesMovieDatabase(app: Application): DatabaseFilm { //TODO: Function untuk Menyediakan Database untuk data film
        return Room.databaseBuilder(
            app,
            DatabaseFilm::class.java,
            "moviedb.db"
        ).build()
    }

}