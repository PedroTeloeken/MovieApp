package br.com.movieapp.core.domain.model

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import br.com.movieapp.core.domain.MovieDetails

class MovieDetailsFactory {

    fun create(poster : Poster) = when (poster){

        Poster.Avengers -> {
            MovieDetails(
                id = 1,
                title = "Avengers",
                voteAverage = 7.1,
                genres = listOf("Ação e aventura" , "Ficção Cientifica"),
                overview = LoremIpsum(100).values.first(),
                backdropPathUrl = "url",
                releaseDate = "04/50/2022",
                duration = 143,
                voteCount = 8
            )
        }

        Poster.JohnWick -> {
            MovieDetails(
                id = 1,
                title = "John Wick",
                voteAverage = 9.1,
                genres = listOf("Ação e aventura" , "Ficção Cientifica"),
                overview = LoremIpsum(100).values.first(),
                backdropPathUrl = "url",
                releaseDate = "04/56/2022",
                duration = 143,
                voteCount = 8
            )
        }
    }

    sealed class Poster{
        object Avengers: Poster()
        object JohnWick: Poster()
    }

}