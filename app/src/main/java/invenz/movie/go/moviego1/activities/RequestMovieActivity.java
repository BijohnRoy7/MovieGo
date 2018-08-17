package invenz.movie.go.moviego1.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import invenz.movie.go.moviego1.R;
import invenz.movie.go.moviego1.utils.Constants;
import invenz.movie.go.moviego1.utils.NetworkUtils;

public class RequestMovieActivity extends AppCompatActivity {

    private static final String TAG = "ROY" ;
    private EditText etMovieName;
    private Spinner spinnerCatagory;
    private TextView btSendRequest;

    private String[] catagories = {"Bangla Movies", "Chinese Movies", "English Movies", "Hindi Movies", "Korean Movies", "Kolkata Bangla Movies", "South Indian Movies", "Tv Series"};
    private ArrayAdapter<String> arrayAdapterCatagories;
    private ProgressDialog progressDialog;
    private AdView mAdView;;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_movie);

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


        etMovieName = findViewById(R.id.idMovieName_requestMovie);
        spinnerCatagory = findViewById(R.id.idMovieCatagory_requestMovie);
        btSendRequest = findViewById(R.id.idSend_requestMovie);

        progressDialog = new ProgressDialog(RequestMovieActivity.this);
        arrayAdapterCatagories = new ArrayAdapter<String>(RequestMovieActivity.this, android.R.layout.simple_list_item_1, catagories);
        spinnerCatagory.setAdapter(arrayAdapterCatagories);

        btSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String sMovieName = etMovieName.getText().toString().toLowerCase();
                final String sCatagory = spinnerCatagory.getSelectedItem().toString();

                if (!sMovieName.isEmpty()  &&  !sCatagory.isEmpty()){

                    Toast.makeText(RequestMovieActivity.this, ""+sCatagory, Toast.LENGTH_SHORT).show();

                    StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.REQUEST_MOVIE_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        String code = jsonObject.getString("code");
                                        String message = jsonObject.getString("message");

                                        Toast.makeText(RequestMovieActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse (RequestMovieActivity): "+error);
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> movieRequestMap = new HashMap<>();
                            movieRequestMap.put("name", sMovieName);
                            movieRequestMap.put("catagory", sCatagory);
                            return movieRequestMap;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(RequestMovieActivity.this);
                    requestQueue.add(stringRequest);

                }else {
                    Toast.makeText(RequestMovieActivity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
