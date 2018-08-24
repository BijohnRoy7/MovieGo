package invenz.movie.go.moviego1.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import invenz.movie.go.moviego1.R;
import invenz.movie.go.moviego1.adapters.MovieInfoCustomAdapter;
import invenz.movie.go.moviego1.models.Movie;
import invenz.movie.go.moviego1.models.MovieLinks;

public class MovieActivity extends YouTubeBaseActivity {

    private static final String TAG = "ROY";
    private RecyclerView movieInfoRecyclerView;
    private MovieInfoCustomAdapter customAdapter;
    private MovieLinks movieLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);


        movieInfoRecyclerView = findViewById(R.id.idMovieInfo_movieactivity);

        /*###### Getting bundle ########*/
        Bundle movieInfo = getIntent().getExtras().getBundle("MovieInfo");

        /*########## Getting values from bundle ##############*/
        int movieId = movieInfo.getInt("id");
        String movieName = movieInfo.getString("MovieName");
        String movieDesc = movieInfo.getString("MovieDesc");
        String movieLink1 = movieInfo.getString("MovieLink1");
        String movieLink2 = movieInfo.getString("MovieLink2");
        String movieLink3 = movieInfo.getString("MovieLink3");
        String movieLink4 = movieInfo.getString("MovieLink4");
        String movieYear = movieInfo.getString("MovieYear");
        String imageURL = movieInfo.getString("MovieImage");
        String youtubeVideoId = movieInfo.getString("YoutubeVideoId");
        String subtitle1 = movieInfo.getString("subtitle1");
        String subtitle2 = movieInfo.getString("subtitle2");

        //Toast.makeText(this, ""+movieInfo.getString("MovieName"), Toast.LENGTH_SHORT).show();

        /*############ MOVIE LINKS ############*/
        movieLink = new MovieLinks(movieLink1, movieLink2, movieLink3, movieLink4, subtitle1, subtitle2);

        /*######### MOVIE INFO ##########*/
        Movie movie = new Movie(movieId, movieName, movieDesc, "1","2", "3","4", movieYear,imageURL, youtubeVideoId, "1", "1");

        /*############ CONFIGURING RecyclerView #############*/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        customAdapter = new MovieInfoCustomAdapter(getApplicationContext(), movie, movieLink);
        movieInfoRecyclerView.setLayoutManager(linearLayoutManager);
        movieInfoRecyclerView.setAdapter(customAdapter);

    }
}
