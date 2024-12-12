package com.laksamana.movio.ListFilm.Presentasi


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.laksamana.movio.ListFilm.Util.Kategori


@Composable
fun Upcoming(
    movieListState: StateListFilm,
    navController: NavHostController,
    onEvent: (UIEventListFilm) -> Unit
) {

    if (movieListState.upcomingMovieList.isEmpty()) { //TODO: Pengaturan tampilan jika List Film dibagian Upcoming masih kosong/belum selesai loading
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator() //TODO: Indikator Loading
        }
    } else {
        LazyVerticalGrid( //TODO: Agar dapat menampilkan daftar film secara vertikal dan dapat discroll(lazy)
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ) {
            items(movieListState.upcomingMovieList.size) { index ->
                ItemFilm(
                    movie = movieListState.upcomingMovieList[index],
                    navHostController = navController
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (index >= movieListState.upcomingMovieList.size - 1 && !movieListState.isLoading) {
                    onEvent(UIEventListFilm.Paginate(Kategori.UPCOMING)) //TODO: Paginate untuk mengambil data dari API agar up-to-date
                }

            }
        }
    }

}
