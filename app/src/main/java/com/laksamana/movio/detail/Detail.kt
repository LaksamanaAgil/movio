package com.laksamana.movio.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.laksamana.movio.ListFilm.Data.Remote.ApiFilm
import com.laksamana.movio.ListFilm.Util.RatingBar
import com.laksamana.movio.R


@Composable
fun Detail() {

    val detailsViewModel = hiltViewModel<ViewModelDetail>() //TODO: Menetapkan nilai viewmodel untuk halaman detail
    val detailsState = detailsViewModel.detailsState.collectAsState().value ///TODO: Mengambil nilai viewmodel dan menyimpannya di StateDetail.kt

    val backDropImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(ApiFilm.IMAGE_BASE_URL + detailsState.movie?.backdrop_path)
            .size(Size.ORIGINAL)
            .build()
    ).state

    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(ApiFilm.IMAGE_BASE_URL + detailsState.movie?.poster_path)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column( //TODO: Kolom berisi  backdrop image dan pengaturannya
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (backDropImageState is AsyncImagePainter.State.Error) { //TODO: PENGATURAN GAMBAR BACKDROP/BACKGROUP DI LAMAN DETAIL Saat gagal mengambil image dari API
            Box( //TODO: Settingan box pengganti backdrop image
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon( //TODO: Ikon pengganti saat gagal mengambil gambar
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = detailsState.movie?.title
                )
            }
        }

        if (backDropImageState is AsyncImagePainter.State.Success) { //TODO: PENGATURAN GAMBAR BACKDROP/BACKGROUP DI LAMAN DETAIL Saat berhasil mengambil image dari API
            Image( //TODO: PENGATURAN UKURAN GAMBAR BACKDROP DI LAMAN Detail
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                painter = backDropImageState.painter,
                contentDescription = detailsState.movie?.title,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) //TODO: Spacing antara backdrop image dengan konten lain di halaman detail

        Row( //TODO: Row berisi poster gambar film
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box( //TODO: Box untuk poster gambar film
                modifier = Modifier
                    .width(160.dp)
                    .height(240.dp)
            ) {
                if (posterImageState is AsyncImagePainter.State.Error) { //TODO: Pengaturan poster jika gagal mengambil gambar
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(70.dp),
                            imageVector = Icons.Rounded.ImageNotSupported,
                            contentDescription = detailsState.movie?.title
                        )
                    }
                }

                if (posterImageState is AsyncImagePainter.State.Success) { //TODO: Pengaturan jika loading gambar sukses
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        painter = posterImageState.painter,
                        contentDescription = detailsState.movie?.title,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            detailsState.movie?.let { movie -> //TODO: Dengan detailState mengambil gambar dari film.kt untuk kolom info film
                Column( //TODO: Kolom disamping poster gambar film (keduanya dibawah backdrop image)
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = movie.title,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row( //TODO: Baris horizontal berisi rating bar dan mean skor
                        modifier = Modifier
                            .padding(start = 16.dp)
                    ) {
                        RatingBar( //TODO: Pengaturan Rating Bar
                            starsModifier = Modifier.size(18.dp),
                            rating = movie.vote_average / 2
                        )

                        Text( //TODO: Pengaturan Mean skor film
                            modifier = Modifier.padding(start = 4.dp),
                            text = movie.vote_average.toString().take(3),
                            color = Color.LightGray,
                            fontSize = 14.sp,
                            maxLines = 1,
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp)) //TODO: Untuk spacing

                    Text( //TODO: Pengaturan tampilan Informasi bahasa film
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(R.string.language) + movie.original_language
                    )

                    Spacer(modifier = Modifier.height(10.dp)) //TODO: Untuk spacing

                    Text( //TODO: Pengaturan tanggal rilis
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(R.string.release_date) + movie.release_date
                    )

                    Spacer(modifier = Modifier.height(10.dp)) //TODO: Untuk spacing

                    Text( //TODO: Pengaturan tampilan jumlah vote
                        modifier = Modifier.padding(start = 16.dp),
                        text = movie.release_date + stringResource(R.string.votes)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp)) //TODO: Spacing

        Text( //TODO: Pengaturan jUDUL tampilan overview
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.overview),
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp)) //TODO:

        detailsState.movie?.let { //TODO: Pengaturan tampilan overview / Penjelasan singkat tentang film
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = it.overview,
                fontSize = 16.sp,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))


    }

}
