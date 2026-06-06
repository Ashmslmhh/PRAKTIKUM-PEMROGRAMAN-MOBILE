package com.example.composeModul5.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE category = :category")
    fun getMoviesByCategory(category: String): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies WHERE category = :category")
    suspend fun clearMoviesByCategory(category: String)

    @Query("SELECT cachedAt FROM movies WHERE category = :category ORDER BY cachedAt DESC LIMIT 1")
    suspend fun getLastCachedTime(category: String): Long?
}