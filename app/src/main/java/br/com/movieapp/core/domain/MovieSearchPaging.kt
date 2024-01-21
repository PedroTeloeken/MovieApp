package br.com.movieapp.core.domain

data class MovieSearchPaging(
    val page: Int,
    val totalPage: Int,
    val totalResults: Int,
    val movies: List<MovieSearch>
)
