package invenz.movie.go.moviego1.models;

import com.google.firebase.storage.StorageReference;

public class Movie {

    private int id;
    private String movieName, movieDesc, movieLink, movieLink1, movieLink2,movieLink3, releaseDate, imageLink, youtubeVideoId;

    public Movie(int id, String movieName, String movieDesc, String movieLink, String movieLink1, String movieLink2, String movieLink3, String releaseDate, String imageLink, String youtubeVideoId) {
        this.id = id;
        this.movieName = movieName;
        this.movieDesc = movieDesc;
        this.movieLink = movieLink;
        this.movieLink1 = movieLink1;
        this.movieLink2 = movieLink2;
        this.movieLink3 = movieLink3;
        this.releaseDate = releaseDate;
        this.imageLink = imageLink;
        this.youtubeVideoId = youtubeVideoId;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    public String getMovieLink3() {
        return movieLink3;
    }

    public void setMovieLink3(String movieLink3) {
        this.movieLink3 = movieLink3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }

    public String getMovieLink() {
        return movieLink;
    }

    public void setMovieLink(String movieLink) {
        this.movieLink = movieLink;
    }

    public String getMovieLink1() {
        return movieLink1;
    }

    public void setMovieLink1(String movieLink1) {
        this.movieLink1 = movieLink1;
    }

    public String getMovieLink2() {
        return movieLink2;
    }

    public void setMovieLink2(String movieLink2) {
        this.movieLink2 = movieLink2;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

}
