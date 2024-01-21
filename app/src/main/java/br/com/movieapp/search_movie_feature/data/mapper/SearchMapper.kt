package br.com.movieapp.search_movie_feature.data.mapper

import br.com.movieapp.core.domain.MovieSearch
import br.com.movieapp.core.data.remote.model.SearchResult
import br.com.movieapp.core.util.toPostUrl

fun List<SearchResult>.toMovieSearch() = map {
    MovieSearch(
        id = it.id,
        title = it.title ?: "",
        voteAverage = it.voteAverage,
        imageUrl = it.posterPath.toPostUrl()
    )
}

fun SearchResult.toMovieSearch() : MovieSearch {
    return MovieSearch(
        id = id,
        title = title ?: "",
        imageUrl = posterPath.toPostUrl(),
        voteAverage = voteAverage
    )
}