package invenz.movie.go.moviego1;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import invenz.movie.go.moviego1.models.Movie;
import invenz.movie.go.moviego1.utils.Constants;

public class DoBackgroundTask {

    private Context mContext;
    private List<Movie> movies ;
    private String TAG = "ROY";

    public DoBackgroundTask(Context mContext) {
        this.mContext = mContext;
        movies = new ArrayList<>();
    }


    /*############# Method for getAllMovies ##################*/
    public List<Movie> getAllMovies(){


        return movies;
    }
    /*######################## getAllMovies ends ###########################*/



}
