package com.raju.kvr.themovie.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raju.kvr.themovie.asList
import com.raju.kvr.themovie.domain.model.Movie
import com.raju.kvr.themovie.domain.model.MovieDetail

@Entity(tableName = "favourite_movie")
data class FavouriteMovie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "movie_id") val movieId: Long,
    @ColumnInfo(name = "poster") val poster: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "genres") val genres: String,
    @ColumnInfo(name = "release_date") val releaseData: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") val voteCount: Double,
    @ColumnInfo(name = "backdrop") val backdrop: String,
    @ColumnInfo(name = "tagline") val tagline: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "languages") val languages: String,
    @ColumnInfo(name = "status") val status: String
)

fun FavouriteMovie.asDomainModel(): MovieDetail {
    return MovieDetail(
        id = movieId,
        poster = poster,
        title = title,
        genres = if (genres.isBlank()) emptyList() else genres.asList(),
        releaseData = releaseData,
        voteAverage = voteAverage,
        voteCount = voteCount,
        backdrop = backdrop,
        tagline = tagline,
        overview = overview,
        languages = if (languages.isBlank()) emptyList() else languages.asList(),
        status = status
    )
}

fun List<FavouriteMovie>.asDomainModal(): List<Movie> {
    return map {
        Movie(
            id = it.movieId,
            poster = it.poster,
            title = it.title,
            genres = if (it.genres.isBlank()) emptyList() else it.genres.asList(),
            releaseData = it.releaseData,
            voteAverage = it.voteAverage,
            voteCount = it.voteCount
        )
    }
}
