package br.com.movieapp.movie_favorite_screen.presentancion.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.movieapp.R
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.ui.theme.black
import br.com.movieapp.ui.theme.white
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MovieFavoriteItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: (Int) -> Unit = {}
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(movie.id) }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = black),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.imageUrl)
                        .crossfade(true)
                        .error(R.drawable.ic_error_image)
                        .placeholder(R.drawable.ic_placeholder)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                text = movie.title,
                maxLines = 1,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = white,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun MovieFavoriteItemPreview() {
    MovieFavoriteItem(
        movie = Movie(
            id = 1,
            title = "Homem Aranha",
            voteAverage = 7.89,
            imageUrl = ""
        )
    )
}