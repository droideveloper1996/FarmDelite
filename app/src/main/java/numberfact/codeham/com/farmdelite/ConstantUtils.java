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

    /**
     * SENDER IDs Approved by TRAI.
     */
    public static final String MY_SENDER_ID1 = "TECHAD";
    public static final String MY_SENDER_ID2 = "XAAMPP";
    public static final String MY_SENDER_ID3 = "XMPSLR";
    public static final String MY_SENDER_ID4 = "XPCARE";
    public static final String MY_SENDER_ID5 = "XMPSUP";
    public static final String MY_SENDER_ID6 = "FARMDE";
    public static final String MY_SENDER_ID7 = "TECHAD";
    public static final String MY_SENDER_ID8 = "DELITE";
    public static final String MY_SENDER_ID9 = "FDCARE";


    public static final String MESSAGE = "message";

    public static final String TO = "to";

    public static final String SMS_ALERT_BASE_URL = "http://sms.xpertgroup.org/API/WebSMS/Http/v1.0a/index.php";


    public void createUrl(String mess, String number) {
        String message = (mess);
        Uri Uri = android.net.Uri.parse(SMS_ALERT_BASE_URL).buildUpon().appendQueryParameter(USERNAME, MY_USER)
                .appendQueryParameter(PASSWORD, My_PASS)
                .appendQueryParameter(SENDER, MY_SENDER_ID1)
                .appendQueryParameter(MESSAGE, message)
                .appendQueryParameter(TO, number).build();
        String Url = Uri.toString();
        Log.i("MessageString", Url);

        NetworkUtils networkUtils = new NetworkUtils(mContext);
        networkUtils.fetchResponse(Url);
    }


    public static final String REGISTERED_EMAIL_ADDRESS = "email";
    public static final String REGISTERED_MOBILE_NUMBER = "mobile";
    public static final String REGISTERED_FIRST_NAME = "first_name";
    public static final String REGISTERED_PASSWORD = "password";
    public static final String REGISTERED_COUNTRY_CODE = "country-code";
    public static final String REGISTERED_LAST_NAME = "last_name";
    public static final String PROFILE_PIC_URL = "profile_url";
    public static final String USER_STATUS = "user_status";


    public static final String CART = "cart";
    public static final String WISH = "wish";
    public static final String ACCOUNT = "account";
    public static final String ORDERS = "orders";
    public static final String PRODUCTS = "products";

    public static final String BRAND = "brand";
    public static final String DETAIL = "detail";
    public static final String MRP = "mrp";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String PRODUCT_URL = "product_url";
    public static final String STOCK = "stock";


    public static final String PRODUCT_NAME_KEY = "product_name_key";
    public static final String PRODUCT_PRICE_KEY = "product_price_key";
    public static final String PRODUCT_DETAIL_KEY = "product_detail_key";

    public static final String PRODUCT_ID = "product_id";

    public static final String ADDRESS = "addresses";



}
