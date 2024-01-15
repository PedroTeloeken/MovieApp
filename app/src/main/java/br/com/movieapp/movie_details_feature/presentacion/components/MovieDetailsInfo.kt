package br.com.movieapp.movie_details_feature.presentacion.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.movieapp.R
import br.com.movieapp.core.domain.MovieDetails
import java.time.LocalDate


@Composable
fun MovieInfoContent(
    modifier: Modifier = Modifier,
    movieDetails: MovieDetails?,
) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        MovieDetailsInfo(
            name = stringResource(id = R.string.vote_average),
            value = movieDetails?.voteAverage.toString()
        )
        MovieDetailsInfo(
            name = stringResource(id = R.string.duration),
            value = stringResource(
                id = R.string.duration_minutes,
                movieDetails?.duration.toString()
            )
        )
        MovieDetailsInfo(
            name = stringResource(id = R.string.release_date),
            value = movieDetails?.releaseDate.toString()
        )
    }

}

@Composable
private fun MovieDetailsInfo(
    name: String,
    value: String
) {

    Column {
        Text(
            text = name,
            style = MaterialTheme.typography.subtitle2.copy(fontSize = 13.sp, letterSpacing = 1.sp),
            color = Color.DarkGray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.SemiBold),
            color = Color.DarkGray,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp)
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun MovieInfoContentPreview(

) {
    MovieInfoContent(
        modifier = Modifier.fillMaxWidth(),
        movieDetails = MovieDetails(
            id = 10,
            title = "Filme",
            genres = listOf("Aventura", "comédia"),
            overview = "",
            releaseDate = LocalDate.now().toString(),
            voteAverage = 9.0,
            duration = 90,
            voteCount = 100,
            backdropPathUrl = ""
        )
    )

}