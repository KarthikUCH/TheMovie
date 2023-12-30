package com.raju.kvr.themovie.data.repository

import com.raju.kvr.themovie.data.db.dao.FavouriteMovieDao
import com.raju.kvr.themovie.data.db.entity.FavouriteMovie
import com.raju.kvr.themovie.data.remote.MovieApi
import com.raju.kvr.themovie.data.remote.model.*
import com.raju.kvr.themovie.domain.model.asDbModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

@RunWith(MockitoJUnitRunner::class)
internal class MoviesRepositoryTest {

    @Mock
    private lateinit var movieApi: MovieApi

    @Mock
    private lateinit var favouriteMovieDao: FavouriteMovieDao


    private lateinit var moviesRepository: MoviesRepository

    private val genreListResponse: GenreListResponse = GenreListResponse(
        listOf(
            GenreResponse(1, "Action"),
            GenreResponse(2, "Adventure"),
            GenreResponse(3, "Animation"),
            GenreResponse(4, "Comedy"),
            GenreResponse(5, "Crime")
        )
    )

    private val movieResponse = MovieResponse(
        id = 1081893,
        poster = "poster_path",
        title = "title",
        genreIdList = listOf(1, 2, 3),
        releaseData = "2022-11-28",
        voteAverage = 0.0,
        voteCount = 0.0,
    )

    private val movieDetailResponse = MovieDetailResponse(
        id = 1081893,
        poster = "poster_path",
        title = "title",
        genres = listOf(
            GenreResponse(1, "Action"),
            GenreResponse(2, "Adventure"),
            GenreResponse(3, "Animation")
        ),
        releaseData = "2022-10-28",
        voteAverage = 0.0,
        voteCount = 0.0,
        backdrop = "backdrop_path",
        tagline = "tagline",
        overview = "overview",
        languages = listOf(
            LanguageResponse("English", "English"),
            LanguageResponse("Deutsch", "German"),
        ),
        status = "status"
    )

    private val favouriteMovie = FavouriteMovie(
        id = 1,
        movieId = 1081893,
        poster = "poster_path",
        title = "title",
        genres = "[{\"id\": 1,\"name\": \"Action\"},{\"id\": 3,\"name\": \"Animation\"}]",
        releaseDate = "release_date",
        voteAverage = 0.0,
        voteCount = 0.0,
        backdrop = "backdrop_path",
        tagline = "tagline",
        overview = "overview",
        languages = "[{\"englishName\": \"English\",\"name\": \"English\"},{\"englishName\": \"German\",\"name\": \"Deutsch\"}]",
        status = "status"
    )

    @Before
    fun setUp() {
        moviesRepository = MoviesRepositoryImpl(movieApi, favouriteMovieDao)
    }

    @After
    fun tearDown() {

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getGenres_Success() = runTest {

        whenever(movieApi.getGenreList()).then { genreListResponse }
        val genreMapResult = moviesRepository.getGenres()

        verify(movieApi, times(1)).getGenreList()

        Assert.assertTrue(genreMapResult is Result.Success)
        Assert.assertEquals(true, (genreMapResult as Result.Success).response)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getGenres_Failure() = runTest {

        whenever(movieApi.getGenreList()).then { GenreListResponse(emptyList()) }
        val genreMapResult = moviesRepository.getGenres()

        verify(movieApi, times(1)).getGenreList()

        Assert.assertTrue(genreMapResult is Result.Success)
        Assert.assertEquals(false, (genreMapResult as Result.Success).response)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getGenres_Exception() = runTest {

        whenever(movieApi.getGenreList()).then { throw RuntimeException() }
        val genreMapResult = moviesRepository.getGenres()

        verify(movieApi, times(1)).getGenreList()

        Assert.assertTrue(genreMapResult is Result.Failure)
        Assert.assertEquals("Pls Try Again Later", (genreMapResult as Result.Failure).message)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getMovies() = runTest {
        val list: List<MovieResponse> = listOf(movieResponse, movieResponse.copy(id = 1081894))
        val movieListResponse = MovieListResponse(1, 20, list)

        whenever(movieApi.getGenreList()).then { genreListResponse }
        whenever(movieApi.getMovieList("category", 1)).then { movieListResponse }

        val movies = moviesRepository.getMovies("category", 1).first()

        verify(movieApi, times(1)).getMovieList("category", 1)

        Assert.assertEquals(movies.page, movieListResponse.page)
        Assert.assertEquals(movies.totalPages, movieListResponse.totalPages)
        Assert.assertEquals(movies.movieList.size, movieListResponse.movies.size)
    }

    @Test
    fun searchMovies() = runTest {
        val list: List<MovieResponse> = listOf(movieResponse, movieResponse.copy(id = 1081894))
        val movieListResponse = MovieListResponse(1, 20, list)

        whenever(movieApi.getGenreList()).then { genreListResponse }
        whenever(movieApi.searchMovies("query", 1)).then { movieListResponse }

        val moviesResult = moviesRepository.searchMovies("query", 1).first()

        verify(movieApi, times(1)).searchMovies("query", 1)

        Assert.assertEquals(moviesResult.page, movieListResponse.page)
        Assert.assertEquals(moviesResult.totalPages, movieListResponse.totalPages)
        Assert.assertEquals(moviesResult.movieList.size, movieListResponse.movies.size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getMovieDetail() = runTest {
        whenever(movieApi.getMovieDetail(1081893)).then { movieDetailResponse }

        val movieDetailResult = moviesRepository.getMovieDetail(1081893).first()

        verify(movieApi, times(1)).getMovieDetail(1081893)

        Assert.assertEquals(movieDetailResponse.id, movieDetailResult.id)
        Assert.assertEquals(movieDetailResponse.poster, movieDetailResult.poster)
        Assert.assertEquals(movieDetailResponse.title, movieDetailResult.title)
        Assert.assertEquals(movieDetailResponse.genres.size, movieDetailResult.genres.size)
        Assert.assertEquals("28/Oct/2022", movieDetailResult.releaseDate)
        Assert.assertEquals(movieDetailResponse.voteAverage, movieDetailResult.voteAverage, 0.0)
        Assert.assertEquals(movieDetailResponse.voteCount, movieDetailResult.voteCount, 0.0)
        Assert.assertEquals(movieDetailResponse.backdrop, movieDetailResult.backdrop)
        Assert.assertEquals(movieDetailResponse.tagline, movieDetailResult.tagline)
        Assert.assertEquals(movieDetailResponse.overview, movieDetailResult.overview)
        Assert.assertEquals(
            movieDetailResponse.languages.size, movieDetailResult.languages.size
        )
        Assert.assertEquals(movieDetailResponse.status, movieDetailResult.status)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addToFavourite() = runTest {
        val movieDetail = movieDetailResponse.asDomainModel()
        val favouriteMovie = movieDetail.asDbModel()
        whenever(favouriteMovieDao.insert(favouriteMovie)).then { }

        moviesRepository.addToFavourite(movieDetail)

        verify(favouriteMovieDao, times(1)).insert(favouriteMovie)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteFromFavourite() = runTest {
        val movieDetail = movieDetailResponse.asDomainModel()
        whenever(favouriteMovieDao.delete(movieDetail.id)).then { }

        moviesRepository.deleteFromFavourite(movieDetail.id)

        verify(favouriteMovieDao, times(1)).delete(movieDetail.id)
    }

    /*@Test
    fun getMovieLiveDataFromDb() {

        val favouriteMovieData: LiveData<FavouriteMovie> = MutableLiveData(favouriteMovie)
        whenever(favouriteMovieDao.getMovieLiveData(1081893)).then { favouriteMovieData }

        moviesRepository.getMovieLiveDataFromDb(1081893)

        verify(favouriteMovieDao, times(1)).getMovieLiveData(1081893)

        // TODO : Test Transformation by observing livedata
    }*/

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getMovieFromDb() = runTest {
        val favouriteMovieFlow: Flow<FavouriteMovie?> = flow { emit(favouriteMovie) }
        whenever(favouriteMovieDao.getMovie(1081893)).then { favouriteMovieFlow }

        val movieDetailResult = moviesRepository.getMovieFromDb(1081893).first()

        verify(favouriteMovieDao, times(1)).getMovie(1081893)

        Assert.assertTrue(movieDetailResult != null)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getMovieFromDb_invalidMovieId_returnsNullMovieDetail() = runTest {
        val invalidMovie: Flow<FavouriteMovie?> = flow { emit(null) }
        whenever(favouriteMovieDao.getMovie(-1)).then { invalidMovie }

        val movieDetailResult = moviesRepository.getMovieFromDb(-1).first()

        verify(favouriteMovieDao, times(1)).getMovie(-1)

        Assert.assertTrue(movieDetailResult == null)
    }

    @Test
    fun getFavouriteMovies() = runTest {
        val favouriteMovieList: Flow<List<FavouriteMovie>> = flow {
            emit(
                listOf(
                    favouriteMovie,
                    favouriteMovie.copy(id = 2, movieId = 1081894),
                    favouriteMovie.copy(id = 3, movieId = 1081895)
                )
            )
        }
        whenever((favouriteMovieDao.getMovieList())).then { favouriteMovieList }

        val moviesList = moviesRepository.getFavouriteMovies().first()

        verify(favouriteMovieDao, times(1)).getMovieList()

        Assert.assertEquals(3, moviesList.size)
        Assert.assertEquals(1081893, moviesList[0].id)
        Assert.assertEquals(1081894, moviesList[1].id)
        Assert.assertEquals(1081895, moviesList[2].id)

    }
}