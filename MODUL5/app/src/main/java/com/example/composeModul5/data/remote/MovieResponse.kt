package com.example.composeModul5.data.remote

import com.example.composeModul5.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val page: Int,
    @SerialName("results") val movies: List<Movie>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)