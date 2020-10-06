package Services;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrferenceServiceForInsta extends Application {

    public static final String my_Preference="Insta.pref";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Context context;

    public SharedPrferenceServiceForInsta(Context context) {
        this.context=context;
        sharedpreferences=context.getSharedPreferences(my_Preference, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();



    }




    public void setString(String key, String value){
        editor.putString(key,value);
        editor.apply();

    }


  /*  public void saveSession(User user){
        setString("userName",user.getUserName());
        setString("userEmail",user.getUserEmailID());


    }

    public User getSessionDetails(){
        User user = new User();
        user.setUserEmailID(getString("userEmail",null));
        user.setUserName(getString("userName",null));
        return user;
    }
*/

    public String getString(String key, String defaultValue){

        return sharedpreferences.getString(key,defaultValue);
    }


    public void clear(){
        editor.clear();
        editor.apply();
    }


}
