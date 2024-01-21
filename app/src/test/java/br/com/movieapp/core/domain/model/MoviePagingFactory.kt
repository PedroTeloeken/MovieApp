package br.com.movieapp.core.domain.model

import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.domain.MoviePaging

class MoviePagingFactory {

   fun create() = MoviePaging(
       page = 1,
       totalPage = 2,
       totalResults = 2,
       movies = listOf(
           Movie(
               id = 1,
               title = "Avengers",
               voteAverage = 7.1,
               imageUrl = "Url"
           ),
           Movie(
               id = 1,
               title = "John Wick",
               voteAverage = 9.1,
               imageUrl = "Url"
           )
       )
   )
}