package numberfact.codeham.com.farmdelite;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhishek on 08/10/2017.
 */

public class NetworkUtils {

    Context mContext;

    public NetworkUtils(Context context) {
        this.mContext = context;
    }

    public void fetchResponse(String apiendpoint) {
        if (apiendpoint.equals("") || apiendpoint == null) {
            return;
        }

        String url = apiendpoint;
        Log.i("Response", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response", response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Oops! This Didn't worked. Try Next", Toast.LENGTH_SHORT).show();

                        try {
                            Log.i("Response", error.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }


    private String giveResponse(String response) {

        return response;
    }


    private static String makeHttpRequest(URL urls) throws IOException {
        String jsonString = "";
        // URL url = createUrl(urls);
        Log.i("started", "makeHttprequest()");
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        if (urls == null) {
            return jsonString;
        }

        try {
            Log.i("started", "Connection");
            connection = (HttpURLConnection) urls.openConnection();
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                Log.i("started Status", "OK");
                inputStream = connection.getInputStream();
                jsonString = getFromInputStream(inputStream);
                Log.i("started Status", Integer.toString(connection.getResponseCode()));
            } else {
                Log.i("started message", Integer.toString(connection.getResponseCode()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonString;

    }

    private static URL createUrl(String stringUrl) {
        Log.i("started", "createUrl()-" + stringUrl);
        URL url = null;
        if (stringUrl == null) {
            Log.i("started", "null");
            return null;

        }
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String getFromInputStream(InputStream inputStream) throws IOException {
        Log.i("started", "getFromInputStream()");
        StringBuilder output = new StringBuilder();
        InputStreamReader inputStreamReader;
        if (inputStream != null) {
            inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        Log.i("started", output.toString());
        //   extractFeatureFromJson(output.toString());
        return output.toString();
    }

    private static ArrayList<String> extractFeatureFromJson(ArrayList<String> s) {
        Log.i("started", "extractFeatureFromJson()");
        ArrayList<String> news = new ArrayList<String>();
        Log.i("started", "Trying to parse JsonString");

        news = s;

        return news;
    }

    public static List<String> fetchData(ArrayList<String> urls) {
        List<String> news = new ArrayList<>();
        ArrayList<String> jsonResponse = new ArrayList<>();

        for (String requestUrl : urls
                ) {
            Log.i("started", "fetchBooksData()");
            URL url = createUrl(requestUrl);
            try {
                jsonResponse.add(makeHttpRequest(url));
            } catch (IOException e) {
                Log.e("Problem ", e.toString());
            }
            news = extractFeatureFromJson(jsonResponse);
        }

        return news;

    }

}


