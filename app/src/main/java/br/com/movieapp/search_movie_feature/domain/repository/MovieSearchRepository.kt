package br.com.movieapp.search_movie_feature.domain.repository

import androidx.paging.PagingSource
import br.com.movieapp.core.domain.MovieSearch

interface MovieSearchRepository {

    fun getSearchMovies(query: String): PagingSource<Int , MovieSearch>

}