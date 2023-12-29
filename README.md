# The Movie
## 1. Home Screen
The user is expected to land on this page every time the app is opened. Home page consists
of 4 tabs that lists movies as listed below,
1. Now Playing 
2. Popular 
3. Top Rated 
4. Upcoming

### Genre
Use Genre API to fetch the list of available genres and store them locally to map them to the
movie objects before displaying the data

https://api.themoviedb.org/3/genre/movie/list

### Images

Backdrop, Poster image paths can be loaded using any image loading library by prefixing the
path with https://image.tmdb.org/t/p/original

Path -> /ysJte1iqN8pFQ470tumnViB1wHP.jpg
Image URL -> https://image.tmdb.org/t/p/original/ysJte1iqN8pFQ470tumnViB1wHP.jpg

### Requirements
1. Endless scrolling to be enabled on each page, {page} param can be modified on the
API to get results for different pages
2. Each movie card should be designed to show information such as, poster, title,
genre, release date (dd/mmm/yyyy), vote average and vote count
3. Clicking on a movie card should redirect the user to the movie details page

## 2. Movie Details Page
Use the following API to get the movie details for a given {movie-id}
https://api.themoviedb.org/3/movie/{movie-id}

### Requirements
1. The details page is expected to have detailed information about the movie such as,
backdrop, poster, title, tagline, overview, genre, release date (dd/mmm/yyyy), vote
average, vote count, spoken languages and status in a user-friendly design
2. The details page should provide a CTA for the user to mark a movie as favourite

## 3. Search Page 
The home page toolbar or any other CTA should enable the user to search across movies for
a given {query}.
Note: This is not a category specific search and it is outside the scope of the home page
Use the following API to perform the search operation
https://api.themoviedb.org/3/search/movie?query={query}&page=1

### Requirements
1. Endless scrolling to be enabled on each page, {page} param can be modified on the
API to get results for different pages
2. Each movie card should be designed to show information such as, poster, title,
genre, release date (dd/mmm/yyyy), vote average and vote count
3. Clicking on a movie card should redirect the user to the movie details page

## 4. Favourites
Design and develop a page to list all the movies that were favourited by the user from the
movie details page
This page should involve fetching the data from a local data source (preferably Room)

### Requirements
1. Each movie card should be designed to show information such as, poster, title,
genre, release date (dd/mmm/yyyy), vote average and vote count
2. Clicking on a movie card should redirect the user to the movie details page

Your assignment submission will be evaluated based on the architecture, UI/UX, frameworks
being used and code reusability

