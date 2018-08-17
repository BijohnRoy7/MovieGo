package invenz.movie.go.moviego1.notification;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import invenz.movie.go.moviego1.activities.MainActivity;
import invenz.movie.go.moviego1.utils.Constants;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG =  "ROY";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.TOKEN_KEY, refreshedToken);
        editor.commit();

        sendRegistrationToServer(refreshedToken);
    }





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
                            Toast.makeText(MyFirebaseInstanceIdService.this, ""+message, Toast.LENGTH_SHORT).show();

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
