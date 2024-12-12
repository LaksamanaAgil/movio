package com.laksamana.movio.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.laksamana.movio.ListFilm.Domain.Repository.RepositoryListFilm
import com.laksamana.movio.ListFilm.Util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewModelDetail @Inject constructor( //TODO: Constructor Injection yaitu menggunakan constructor untuk menambahkan dependency esbagai parameter didalam constructor
    private val movieListRepository: RepositoryListFilm, //TODO: Menetapkan RepositoryListFilm sebagai repository
    private val savedStateHandle: SavedStateHandle //TODO: Digunakan agar suatu nilai query tetap ada/tidak ter-reset meskipun aplikasi di-exit
) : ViewModel() {

    private val movieId = savedStateHandle.get<Int>("movieId") //TODO: Mengambil ID Film yang telah disimpan

    private var _detialsState = MutableStateFlow(StateDetail()) //TODO: Menetapkan StateDetail.kt sebagai variabel state yang dapat diupdate(Mutable state of flow)
    val detailsState = _detialsState.asStateFlow() //TODO:

    init {
        getMovie(movieId ?: -1)
    }

    private fun getMovie(id: Int) { //TODO: Mengambil Data film berdasarkan ID film
        viewModelScope.launch {
            _detialsState.update {
                it.copy(isLoading = true)
            }

            movieListRepository.getMovie(id).collectLatest { result ->
                when (result) {
                    is Resource.Error -> { //TODO: Pada kasus error, Menghentikan Loading
                        _detialsState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Loading -> { //TODO: Pada kasus masih loading, diupdate sesuai hasil yang telah diload
                        _detialsState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> { //TODO: Pada kasus loading sukses, state detail diupdate sesuai hasil data yang masuk ke domain film.kt
                        result.data?.let { movie ->
                            _detialsState.update {
                                it.copy(movie = movie)
                            }
                        }
                    }
                }
            }
        }
    }
}