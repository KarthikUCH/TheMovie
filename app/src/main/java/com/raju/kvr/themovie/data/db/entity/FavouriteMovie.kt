package com.raju.kvr.themovie.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raju.kvr.themovie.asList
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

fun FavouriteMovie.asDomainModel(): MovieDetail{
    return MovieDetail(
        id = movieId,
        poster = poster,
        title = title,
        genres = genres.asList(), // TODO: JSON conversion
        releaseData = releaseData,
        voteAverage = voteAverage,
        voteCount = voteCount,
        backdrop = backdrop,
        tagline = tagline,
        overview = overview,
        languages = languages.asList(), // TODO: JSON conversion
        status = status
    )
}
