package com.laksamana.movio.ListFilm.Presentasi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laksamana.movio.ListFilm.Domain.Repository.RepositoryListFilm
import com.laksamana.movio.ListFilm.Util.Kategori
import com.laksamana.movio.ListFilm.Util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewModelListFilm @Inject constructor( //TODO: Constructor Injection yaitu menggunakan constructor untuk menambahkan dependency esbagai parameter didalam constructor
    private val movieListRepository: RepositoryListFilm
) : ViewModel() {

    private var _movieListState = MutableStateFlow(StateListFilm()) //TODO: Menetapkan StateListFilm sebagai private var mutableliststate, artinya nilai dalam list state dapat berubah
    val movieListState = _movieListState.asStateFlow()

    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
    }

    fun onEvent(event: UIEventListFilm) {
        when (event) {
            UIEventListFilm.Navigate -> { //TODO: Untuk memastikan agar list yang ditampilkan dalam halaman default(halaman popiler) up-to date
                _movieListState.update {
                    it.copy(
                        isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen
                    )
                }
            }

            is UIEventListFilm.Paginate -> { //TODO: Paginate berfungsi mengambil data list film kategori populer dan upcoming dari API agar data up-to-date
                if (event.category == Kategori.POPULAR) {
                    getPopularMovieList(true)
                } else if (event.category == Kategori.UPCOMING) {
                    getUpcomingMovieList(true)
                }
            }
        }
    }

    private fun getPopularMovieList(forceFetchFromRemote: Boolean) { //TODO: Fungsi untuk engambil data list film populer
        viewModelScope.launch {
            _movieListState.update {//TODO: Memulai loading untuk mengambil data list Film populer
                it.copy(isLoading = true)
            }

            movieListRepository.getMovieList( //TODO: Mengambil List Film populer dari RepositoryListFilm
                forceFetchFromRemote,
                Kategori.POPULAR,
                movieListState.value.popularMovieListPage
            ).collectLatest { result -> //TODO: Mengambil data list film Populer terbaru dan up-to date
                when (result) {
                    is Resource.Error -> { //TODO: Jika hasil loading gagal/error, maka loading dihentikan
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> { //TODO: Jika hasil loading sukses, maka List Film populer diupdate dengan data baru
                        result.data?.let { popularList ->
                            _movieListState.update {
                                it.copy(
                                    popularMovieList = movieListState.value.popularMovieList
                                            + popularList.shuffled(),
                                    popularMovieListPage = movieListState.value.popularMovieListPage + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> { //TODO: Jika resource masih loading, maka terus menlanjutkan proses loading
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) { //TODO: Fungsi untuk engambil data list film upcoming
        viewModelScope.launch {
            _movieListState.update {//TODO: Memulai loading untuk mengambil data list Film upcoming
                it.copy(isLoading = true)
            }

            movieListRepository.getMovieList( //TODO: Mengambil List Film upcoming dari RepositoryListFilm
                forceFetchFromRemote,
                Kategori.UPCOMING,
                movieListState.value.upcomingMovieListPage
            ).collectLatest { result -> //TODO: Mengambil data list film upcoming terbaru dan up-to date
                when (result) {
                    is Resource.Error -> { //TODO: Jika hasil loading gagal/error, maka loading dihentikan
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success -> { //TODO: Jika hasil loading sukses, maka mengupdate list film upcoming dengan data terbaru
                        result.data?.let { upcomingList ->
                            _movieListState.update {
                                it.copy(
                                    upcomingMovieList = movieListState.value.upcomingMovieList
                                            + upcomingList.shuffled(),
                                    upcomingMovieListPage = movieListState.value.upcomingMovieListPage + 1
                                )
                            }
                        }
                    }

                    is Resource.Loading -> { //TODO: Jika masih loading, proses loading berlanjut
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

}
