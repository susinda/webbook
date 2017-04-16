package com.example.susinda.mapproject;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by susinda on 8/12/16.
 */
public class OKHttpClientWrapper {

    OkHttpClient client = null;
    public OKHttpClientWrapper() {
        client = new OkHttpClient();
    }

    public void doGet(String url, Callback cb) throws IOException {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.addHeader("Accept", "application/json");
        Request request = builder.build();

        try {
            //Response response = client.newCall(request).execute();
            client.newCall(request).enqueue(cb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void doPost(String url, Callback cb1, String phone, float latitude, float longitude) throws IOException {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String template = "{\n" +
                "   \t\"Phone\": %s,\n" +
                "   \t\"Lat\": %.6f,\n" +
                "   \t\"Lon\": %.6f\n" +
                "}\n";
        String jsonBody = String.format(template, phone, latitude, longitude);

        RequestBody body = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

         client.newCall(request).enqueue(cb1);
    }


}
