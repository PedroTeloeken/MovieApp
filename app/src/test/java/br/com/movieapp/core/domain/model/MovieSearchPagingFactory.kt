package br.com.movieapp.core.domain.model

import br.com.movieapp.core.domain.MovieSearch
import br.com.movieapp.core.domain.MovieSearchPaging

class MovieSearchPagingFactory {

   fun create() = MovieSearchPaging(
       page = 1,
       totalPage = 2,
       totalResults = 2,
       movies = listOf(
           MovieSearch(
               id = 1,
               title = "Avengers",
               voteAverage = 7.1,
               imageUrl = "Url"
           ),
           MovieSearch(
               id = 1,
               title = "John Wick",
               voteAverage = 9.1,
               imageUrl = "Url"
           )
       )
   )
}