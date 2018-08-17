package invenz.movie.go.moviego1.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import invenz.movie.go.moviego1.DoBackgroundTask;
import invenz.movie.go.moviego1.R;
import invenz.movie.go.moviego1.adapters.MovieListCUstomAdapterr;
import invenz.movie.go.moviego1.models.Header;
import invenz.movie.go.moviego1.models.Movie;
import invenz.movie.go.moviego1.utils.Constants;
import invenz.movie.go.moviego1.utils.NetworkUtils;

public class MovieListActivity extends AppCompatActivity {

    private static final String TAG = "ROY";
    private RecyclerView moviesRecyclerView;
    private String catagoryName;
    private EditText etSearch;
    private List<Movie> movies;
    //private MovieListCustomAdapter customAdapter;
    private MovieListCUstomAdapterr customAdapter;
    private ProgressDialog progressDialog;
    private AdView mAdView;;

    private StorageReference mStorageRef;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);


        catagoryName = getIntent().getExtras().getString("catagory");
        //Toast.makeText(this, ""+catagoryName, Toast.LENGTH_SHORT).show();


        /*############### Internet Connection Checking ################*/
        boolean isConnected = NetworkUtils.isNetworkConnected(this);

        if(isConnected == false){
            Toast.makeText(this, "Internet is not connected", Toast.LENGTH_SHORT).show();
            finish();
        }

        /*############# Ad Mob ################*/
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("075B7286D27BE68C4802E48264C1254A").build();
        mAdView.loadAd(adRequest);

        /*############# Initializing variables ################*/
        movies = new ArrayList<>();
        moviesRecyclerView = findViewById(R.id.idMovieListRecView_movie_list);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        etSearch = findViewById(R.id.idSearchEditText);


        /*################### Search on the ListView ####################*/
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.d(TAG, "onTextChanged: "+movies.get(0).getMovieName());
                //Toast.makeText(MovieListActivity.this, "Yeah: "+movies.get(0).getMovieName(), Toast.LENGTH_SHORT).show();

                if (s.toString().equals("")){
                    //Toast.makeText(MovieListActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                    customAdapter = new MovieListCUstomAdapterr( new Header(catagoryName), movies, getApplicationContext());
                    moviesRecyclerView.setAdapter(customAdapter);
                }else {
                    String text = s.toString().toLowerCase();
                    //Toast.makeText(MovieListActivity.this, "Yeah: "+text, Toast.LENGTH_SHORT).show();
                    List<Movie> searchedMovies = new ArrayList<>();

                    for (Movie m: movies){
                        if (m.getMovieName().toLowerCase().trim().contains(text)){
                            //Toast.makeText(MovieListActivity.this, ""+m.getMovieName(), Toast.LENGTH_SHORT).show();
                            searchedMovies.add(m);
                        }
                    }

                    customAdapter = new MovieListCUstomAdapterr( new Header(catagoryName), searchedMovies, getApplicationContext());
                    moviesRecyclerView.setAdapter(customAdapter);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /*################# Searched EditText Ends ############################*/


        /*########## ProgressDialog ##########*/
        progressDialog = new ProgressDialog(MovieListActivity.this);
        progressDialog.setTitle("Getting "+catagoryName+"...");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();

        /*############# Configuring GridLayoutManager ##############*/
        //GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), 3);
        GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), calculateNumberOfColumns(2));
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override public int getSpanSize(int position) {
                return (position == 0) ? calculateNumberOfColumns(2) : 1;
            }
        });
        moviesRecyclerView.setLayoutManager(glm);



        /*############## StringRequest ###############*/
        final StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.GET_ALL_MOVIES_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            /*############### getting json array ###############*/
                            JSONArray jsonArray = new JSONArray(response);
                            //Log.d(TAG, "onResponse(MovieListActivity)1 : "+jsonArray);

                            for (int i=0; i<response.length(); i++){
                                JSONObject jsonObject = null;
                                try {
                                /*######## getting objects one by one ###########*/
                                    jsonObject = jsonArray.getJSONObject(i);
                                    int id = jsonObject.getInt("id");
                                    String movieName = jsonObject.getString("movie_name");
                                    String movieDesc = jsonObject.getString("movie_desc");
                                    String movieLink = jsonObject.getString("movie_link");
                                    String movieLink1 = jsonObject.getString("movie_link1");
                                    String movieLink2 = jsonObject.getString("movie_link2");
                                    String movieLink3 = jsonObject.getString("movie_link3");
                                    String movieReleaseYear = jsonObject.getString("release_year");
                                    String image_link = jsonObject.getString("image_link");
                                    String youttubeVideoId = jsonObject.getString("video_id");


                                    Movie movie = new Movie(id, movieName, movieDesc, movieLink, movieLink1, movieLink2,movieLink3, movieReleaseYear, image_link, youttubeVideoId);
                                    movies.add(movie);

                                    //Log.d(TAG, "onResponse: "+movie.getId()+", "+movie.getMovieName());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            /*################ Sorting Movie Arraylist########################*/
                            Collections.sort(movies, new Comparator<Movie>() {
                                @Override
                                public int compare(Movie movie2, Movie movie1)
                                {

                                    return  movie2.getMovieName().compareTo(movie1.getMovieName());
                                }
                            });



                            /*########## Setting adapter to recyclerview ###########*/
                            customAdapter = new MovieListCUstomAdapterr( new Header(catagoryName), movies, getApplicationContext());
                            moviesRecyclerView.setAdapter(customAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse(MovieListActivity)1: "+error);
                }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                /*######### Sending catagoryMap to the php file ###########*/
                Map<String, String> catagoryMap = new HashMap<>();
                catagoryMap.put("catagory", catagoryName);
                return catagoryMap;
            }
        };

        /*################ RequestQueue ###################*/
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }




    /*######## Custom method to calculate number of columns for grid type recycler view ############*/
    protected int calculateNumberOfColumns(int base){
        int columns = base;
        String screenSize = getScreenSizeCategory();

        if(screenSize.equals("small")){
            if(base!=1){
                columns = columns-1;
            }
        }else if (screenSize.equals("normal")){
            // Do nothing
        }else if(screenSize.equals("large")){
            columns += 2;
        }else if (screenSize.equals("xlarge")){
            columns += 3;
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            columns = (int) (columns * 1.5);
        }

        return columns;
    }

    /*################### Custom method to get screen current orientation #############*/
    protected String getScreenOrientation(){
        String orientation = "undefined";

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            orientation = "landscape";
        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            orientation = "portrait";
        }

        return orientation;
    }

    /*####### Custom method to get screen size category #############*/
    protected String getScreenSizeCategory(){
        int screenLayout = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenLayout){
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                // small screens are at least 426dp x 320dp
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                // normal screens are at least 470dp x 320dp
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                // large screens are at least 640dp x 480dp
                return "large";
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                // xlarge screens are at least 960dp x 720dp
                return "xlarge";
            default:
                return "undefined";
        }
    }




}
