package br.com.movieapp.movie_details_feature.domain.usecase

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.movieapp.core.domain.Movie
import br.com.movieapp.core.domain.MovieDetails
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movie_details_feature.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

interface GetMovieDetailsUseCase {
    operator fun invoke(params: Params): Flow<ResultData<Pair<Flow<PagingData<Movie>>, MovieDetails>>>
    data class Params(val movieId: Int)
}

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val repository: MovieDetailsRepository
) : GetMovieDetailsUseCase {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun invoke(params: GetMovieDetailsUseCase.Params): Flow<ResultData<Pair<Flow<PagingData<Movie>>, MovieDetails>>> {
        return flow {
            try {
                emit(ResultData.Loading)
                val movieDetails = repository.getMovieDetails(params.movieId)
                val movieSimilar = repository.getMovieSimilar(
                    params.movieId,
                    pagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 20)
                )
                emit(ResultData.Success(movieSimilar to movieDetails))
            } catch (e : HttpException){
                emit(ResultData.Failure(e))
            }catch (e : IOException){
                emit(ResultData.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

}