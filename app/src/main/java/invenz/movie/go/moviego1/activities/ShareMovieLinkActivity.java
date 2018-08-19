package invenz.movie.go.moviego1.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import invenz.movie.go.moviego1.R;
import invenz.movie.go.moviego1.utils.Constants;
import invenz.movie.go.moviego1.utils.NetworkUtils;

public class ShareMovieLinkActivity extends AppCompatActivity {

    private static final String TAG = "ROY";
    private EditText etMovieName, etMovieLink;
    private Spinner spinnerCatagory;
    private TextView btSendRequest;

    private String[] catagories = {"Bangla Movies", "Chinese Movies", "English Movies", "Hindi Movies", "Korean Movies", "Kolkata Bangla Movies", "South Indian Movies", "Tv Series", "Others"};
    private ArrayAdapter<String> arrayAdapterCatagories;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_movie_link);

        Toolbar toolbar = findViewById(R.id.idMyAppBar);
        setSupportActionBar(toolbar);

        /*############### Internet Connection Checking ################*/
        boolean isConnected = NetworkUtils.isNetworkConnected(this);
        if(isConnected == false){
            Toast.makeText(this, "Internet is not connected", Toast.LENGTH_SHORT).show();
            finish();
        }


        etMovieName = findViewById(R.id.idMovieName_shareMovie);
        etMovieLink = findViewById(R.id.idMovieLink_shareMovie);
        spinnerCatagory = findViewById(R.id.idMovieCatagory_shareMovie);
        btSendRequest = findViewById(R.id.idSend_shareMovie);

        progressDialog = new ProgressDialog(ShareMovieLinkActivity.this);
        progressDialog.setTitle("Sending Request");
        progressDialog.setMessage("Please Wait");

        arrayAdapterCatagories = new ArrayAdapter<String>(ShareMovieLinkActivity.this, android.R.layout.simple_list_item_1, catagories);
        spinnerCatagory.setAdapter(arrayAdapterCatagories);



        btSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String sMovieName = etMovieName.getText().toString().toLowerCase().trim();
                final String sMovieLink = etMovieLink.getText().toString().trim();
                final String sCatagory = spinnerCatagory.getSelectedItem().toString();

                if (!sMovieName.isEmpty()  &&  !sCatagory.isEmpty()){

                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.SHARE_MOVIE_LINK_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        String code = jsonObject.getString("code");
                                        String message = jsonObject.getString("message");

                                        progressDialog.dismiss();
                                        etMovieName.setText("");
                                        etMovieLink.setText("");
                                        Toast.makeText(ShareMovieLinkActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse (RequestMovieActivity): "+error);
                            progressDialog.dismiss();
                            Toast.makeText(ShareMovieLinkActivity.this, "Failed to send request. Try later...", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> movieRequestMap = new HashMap<>();
                            movieRequestMap.put("name", sMovieName);
                            movieRequestMap.put("link", sMovieLink);
                            movieRequestMap.put("catagory", sCatagory);
                            return movieRequestMap;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(ShareMovieLinkActivity.this);
                    requestQueue.add(stringRequest);

                }else {
                    Toast.makeText(ShareMovieLinkActivity.this, "Please provide required information", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    /*############### ActionBar ###################*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.normal_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


}
