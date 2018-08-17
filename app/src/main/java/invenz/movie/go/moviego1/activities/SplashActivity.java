package invenz.movie.go.moviego1.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rampo.updatechecker.UpdateChecker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import invenz.movie.go.moviego1.R;
import invenz.movie.go.moviego1.notification.MyFirebaseInstanceIdService;
import invenz.movie.go.moviego1.utils.Constants;
import invenz.movie.go.moviego1.utils.NetworkUtils;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "ROY" ;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        boolean isConnected = NetworkUtils.isNetworkConnected(this);

        if(isConnected == false){
            //Toast.makeText(this, "Internet is not connected", Toast.LENGTH_SHORT).show();

            buildDialog(SplashActivity.this).show();

        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    /*####### Check For update #########*/
                    UpdateChecker checker = new UpdateChecker(SplashActivity.this); // If you are in a Activity or a FragmentActivity
                    //UpdateChecker checker = new UpdateChecker(getActivity()); // If you are in a Fragment
                    checker.start();



                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 1000);

        }

    }

    /*################### METHOD FOR SHOWING DIALOG WHEN INTERNET IS NOT CONNECTED ###############################*/
    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this.\n\nPress ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        return builder;
    }



}
