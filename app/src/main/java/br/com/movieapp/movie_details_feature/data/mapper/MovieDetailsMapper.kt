package br.com.movieapp.movie_details_feature.data.mapper

import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.domain.MovieDetails

fun MovieDetails.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        imageUrl = backdropPathUrl.toString(),
        voteAverage = voteAverage
    )
}