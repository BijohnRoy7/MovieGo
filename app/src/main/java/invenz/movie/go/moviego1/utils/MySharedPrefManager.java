package invenz.movie.go.moviego1.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static invenz.movie.go.moviego1.utils.Constants.SHARED_PREF_NAME;


public class MySharedPrefManager {

    private Context mContext;
    private static MySharedPrefManager mInstance;


    /*#### Constructor #####*/
    public MySharedPrefManager(Context mContext) {
        this.mContext = mContext;
    }

    /*###### Get instance ######*/
    public static synchronized MySharedPrefManager getInstance(Context context){
        if (mInstance==null){
            mInstance = new MySharedPrefManager(context);
        }
        return mInstance;
    }

    /*###### Store token #####*/
    public boolean storeToken(String token){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.TOKEN_KEY, token);
        editor.apply();
        editor.commit();
        return true;
    }

    /*##### get token #######*/
    public String getToken() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.TOKEN_KEY, null);
    }
}
