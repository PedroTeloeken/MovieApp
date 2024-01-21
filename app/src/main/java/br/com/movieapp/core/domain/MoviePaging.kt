package br.com.movieapp.core.domain

data class MoviePaging(
    val page: Int,
    val totalPage: Int,
    val totalResults: Int,
    val movies: List<Movie>
)
