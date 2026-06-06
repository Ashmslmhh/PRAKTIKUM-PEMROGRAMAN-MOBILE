package com.example.composeModul5.data.repository

import android.content.Context
import com.example.composeModul5.data.LanguagePreferences
import com.example.composeModul5.data.local.MovieDatabase
import com.example.composeModul5.data.local.MovieEntity
import com.example.composeModul5.data.remote.ApiService
import com.example.composeModul5.data.remote.RetrofitInstance
import com.example.composeModul5.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(private val db: MovieDatabase, context: Context) {

    private val dao = db.movieDao()
    private val api = RetrofitInstance.api
    private val languagePrefs = LanguagePreferences(context)

    private val cacheTimeout = 60 * 60 * 1000L

    fun getMovies(category: String): Flow<List<Movie>> =
        dao.getMoviesByCategory(category)
            .map { entities -> entities.map { it.toMovie() } }

    suspend fun refreshMovies(category: String): Result<Unit> {
        return try {
            val language = languagePrefs.getLanguage()
            val lastCached = dao.getLastCachedTime(category)
            val now = System.currentTimeMillis()

            if (lastCached != null && now - lastCached < cacheTimeout) {
                return Result.success(Unit)
            }

            val response = when (category) {
                ApiService.CATEGORY_POPULAR -> api.getPopularMovies(language = language)
                ApiService.CATEGORY_TOP_RATED -> api.getTopRatedMovies(language = language)
                ApiService.CATEGORY_NOW_PLAYING -> api.getNowPlayingMovies(language = language)
                else -> api.getPopularMovies(language = language)
            }

            val entities = response.movies.map { it.toEntity(category) }
            dao.clearMoviesByCategory(category)
            dao.insertMovies(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun forceRefreshMovies(category: String): Result<Unit> {
        return try {
            val language = languagePrefs.getLanguage()
            val response = when (category) {
                ApiService.CATEGORY_POPULAR -> RetrofitInstance.api.getPopularMovies(language = language)
                ApiService.CATEGORY_TOP_RATED -> RetrofitInstance.api.getTopRatedMovies(language = language)
                ApiService.CATEGORY_NOW_PLAYING -> RetrofitInstance.api.getNowPlayingMovies(language = language)
                else -> RetrofitInstance.api.getPopularMovies(language = language)
            }
            val entities = response.movies.map { it.toEntity(category) }
            dao.clearMoviesByCategory(category)
            dao.insertMovies(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

fun MovieEntity.toMovie() = Movie(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage
)

fun Movie.toEntity(category: String) = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    category = category
)