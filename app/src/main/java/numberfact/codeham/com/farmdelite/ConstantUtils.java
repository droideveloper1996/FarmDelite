package numberfact.codeham.com.farmdelite;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Abhishek on 22/10/2017.
 */

public class ConstantUtils {
    public Context mContext;

    ConstantUtils(Context mCtx) {
        mContext = mCtx;
    }

    public static final String USERNAME = "username";
    public static final String MY_USER = "TECHADDICT";

    public static final String PASSWORD = "password";
    public static final String My_PASS = "abhishek";

    public static final String SENDER = "sender";
    public static final String MY_SENDER = "TECHAD";

    public static final String MESSAGE = "message";

    public static final String TO = "to";

    public static final String SMS_ALERT_BASE_URL = "http://sms.xpertgroup.org/API/WebSMS/Http/v1.0a/index.php";


    public void createUrl(String mess, String number) {
        String message =  (mess);
        Uri Uri = android.net.Uri.parse(SMS_ALERT_BASE_URL).buildUpon().appendQueryParameter(USERNAME, MY_USER)
                .appendQueryParameter(PASSWORD, My_PASS)
                .appendQueryParameter(SENDER, MY_SENDER)
                .appendQueryParameter(MESSAGE, message)
                .appendQueryParameter(TO, number).build();
        String Url = Uri.toString();
        Log.i("MessageString", Url);

        NetworkUtils networkUtils = new NetworkUtils(mContext);
        networkUtils.fetchResponse(Url);
    }
}
