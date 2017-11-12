package numberfact.codeham.com.farmdelite;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Abhishek on 29/06/2017.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "farm";


    private static final String USER_MOBILE_NUMBER = "mobile_number";

    public static final String USERNAME_EMAIL = "user_email";
    public static final String USER_NAME = "user_name";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }




    public void setMobileNumber(String mobileNumber) {
        if (mobileNumber != null && mobileNumber != "") {
            editor.putString(USER_MOBILE_NUMBER, mobileNumber);
            editor.commit();
        }
    }

    public void setEmailAddress(String emailAddress) {
        if (emailAddress != null && emailAddress != "") {
            editor.putString(USERNAME_EMAIL, emailAddress);
            editor.commit();
        }
    }

    public void setUserName(String userName) {
        if (userName != null && userName != "") {
            editor.putString(USER_NAME, userName);
            editor.commit();
        }
    }


    public String getMobileNumber() {
        return pref.getString(USER_MOBILE_NUMBER, "null");
    }

    public String getUserEmail() {
        return pref.getString(USERNAME_EMAIL, "null");
    }

    public String getUserName() {
        return pref.getString(USER_NAME, "null");
    }


}