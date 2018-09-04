package com.example.stores;

/**
 * Created by joseramos on 6/8/18.
 */

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpHandler {

    private static final String LOG_TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    //Make an HTTP request to the given URL and return a String as the response
    public String makeHttpRequest(String reqUrl) throws IOException {

        String response = null;

        if(reqUrl == null){
            return response;
        }

        HttpURLConnection conn = null;
        InputStream inputStream = null;

        URL url = createUrl(reqUrl);

        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.connect();

            if(conn.getResponseCode() == 200){
                // read the response
                inputStream = conn.getInputStream();
                response       = convertStreamToString(inputStream);
            }else{
                Log.e(LOG_TAG, "Error response code: " + conn.getResponseCode());
            }


        } catch (ProtocolException e) {
            Log.e( LOG_TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e( LOG_TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e( LOG_TAG, "Exception: " + e.getMessage());
        }finally {
            if(conn != null){
                conn.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return response;
    }

    //Returns new URL object from the given string URL
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e( LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    //Convert the inputStream into a String which contains the JSON response from server
    private String convertStreamToString(InputStream is) throws IOException {
        StringBuilder output = new StringBuilder();
        if (is != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}