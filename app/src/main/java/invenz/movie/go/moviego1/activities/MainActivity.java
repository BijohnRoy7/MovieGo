package invenz.movie.go.moviego1.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import invenz.movie.go.moviego1.R;
import invenz.movie.go.moviego1.adapters.CatagoryCustomAdapter;
import invenz.movie.go.moviego1.models.Catagory;
import invenz.movie.go.moviego1.utils.Constants;
import invenz.movie.go.moviego1.utils.MySharedPrefManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ROY";
    private BroadcastReceiver broadcastReceiver;

    private RecyclerView catagoryRecyclerView;
    private List<Catagory> catagories;
    private CatagoryCustomAdapter customAdapter;

    private TextView tvToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.idMyAppBar);
        setSupportActionBar(toolbar);
        //toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));



        /*#################################*/
        catagories = new ArrayList<>();
        catagories.add(new Catagory("Bangla Movies", R.drawable.pic1));
        catagories.add(new Catagory("Chinese Movies", R.drawable.chine));
        catagories.add(new Catagory("English Movies", R.drawable.holly));
        catagories.add(new Catagory("Hindi Movies", R.drawable.bollywood));
        catagories.add(new Catagory("Korean Movies", R.drawable.korea));
        catagories.add(new Catagory("Kolkata Bangla Movies", R.drawable.kolkata));
        catagories.add(new Catagory("South Indian Movies", R.drawable.south_indian));
        catagories.add(new Catagory("Tv Series", R.drawable.tv));
        catagories.add(new Catagory("Others", R.drawable.others));

        customAdapter = new CatagoryCustomAdapter(MainActivity.this, catagories);

        catagoryRecyclerView = findViewById(R.id.idCatagoryRecView);
        catagoryRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
        catagoryRecyclerView.setAdapter(customAdapter);
        /*####################################*/


        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.TOKEN_KEY, "token");
        //Toast.makeText(this, ""+token, Toast.LENGTH_SHORT).show();

        if (!token.equals("token")){
            //Toast.makeText(MainActivity.this, "Not null", Toast.LENGTH_SHORT).show();
            sendRegistrationToServer(token);
        }


    }






    /*############### ActionBar ###################*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.idShare:


                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Movie Go");
                    String sAux = "\nLet me recommend you this application\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=the.package.id \n\n"; /*###### PLAYSTORE lINK ######*/
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));

                } catch(Exception e) {
                    //e.toString();
                    Log.d(TAG, "onOptionsItemSelected (MainActivity): "+e);
                }

                //Toast.makeText(this, "Share app", Toast.LENGTH_SHORT).show();

                break;

            case R.id.idSaherMovieLink:

                //Toast.makeText(this, "Check update", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ShareMovieLinkActivity.class));
                break;

            case R.id.idRequest:

                startActivity(new Intent(MainActivity.this, RequestMovieActivity.class));
                //Toast.makeText(this, "Request for movie", Toast.LENGTH_SHORT).show();
                break;

            case R.id.idExit:
                finish();
                //Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();
                break;


        }

        return super.onOptionsItemSelected(item);
    }
    /*#########################################*/





    /*####                  sending token to the server                   ####*/
    private void sendRegistrationToServer(final String refreshedToken) {

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.STORE_TOKEN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject myJsonObject = new JSONObject(response);
                            String connection = myJsonObject.getString("connection");
                            String code = myJsonObject.getString("code");
                            String message = myJsonObject.getString("message");

                            Log.d(TAG, "onResponse1 (MainActivity): "+connection+", "+code+", "+message);
                            Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            /*####### TO GET THE ERROR ###########*/
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse1 (MainActivity): "+error);
            }
        }){
            /*###### METHOD FOR SENDING DATA TO THE PHP FILE #######*/
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> tokenMap = new HashMap<>();
                tokenMap.put("user_token", refreshedToken);
                //tokenMap.put("user_token", "JOHN");
                return tokenMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }





}
