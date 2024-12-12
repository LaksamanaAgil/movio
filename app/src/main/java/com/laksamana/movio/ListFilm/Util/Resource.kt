package com.laksamana.movio.ListFilm.Util

sealed class Resource<T>(
    val data: T? = null, //TODO: Mdnetapkan nilai 'Data' sebagai null yang dapat diisi dengan input
    val message: String? = null //TODO: Meneta[lan nilai message sebagai strung yang secara default null dan dapat diisi input
) {
    class Success<T>(data: T?) : Resource<T>(data) //TODO: Jika koneksi berhasil maka mengirim data yang diambil dari API
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message) //TODO: Jika eroror, maka mengirim pesan error dan data yang sudah ada di database local
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>(null) //TODO: Saat masih loading, data sementara secara default null.
}