package com.kgb.cooapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kgb.cooapp.service.endpoint.Link;
import com.kgb.cooapp.service.endpoint.EndPointList;
import com.kgb.cooapp.service.VApiService;
import com.kgb.cooapp.service.endpoint.UserContent;
import com.kgb.cooapp.service.endpoint.UserEndPoint;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int RESULT_CODE = 100456;
    private static final String BASE_API_URL = "https://cookidoo.pl/vorwerkApiV2/";

    private String secureKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        secureKey = getResources().getString(R.string.auth_key);
        if (secureKey == null || secureKey.isEmpty()) {
            Intent intent = new Intent(this, LoginActivity.class);

            startActivityForResult(intent, RESULT_CODE);
        }
        Button showEndpointList = findViewById(R.id.endpoint_list);
        showEndpointList.setOnClickListener(v -> {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retro = new Retrofit.Builder()
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            VApiService service = retro.create(VApiService.class);

            Call<EndPointList> endPoints = service.listProvider(secureKey);
            endPoints.enqueue(new Callback<EndPointList>() {
                @Override
                public void onResponse(@NonNull Call<EndPointList> call, @NonNull Response<EndPointList> response) {
                    Log.d(TAG, "onResponse: ok");
                    if (response.body() != null) {
                        Log.d(TAG, "onResponse: body ok : " + response.body());
                        for (Link linkList : response.body().getLinks()) {
                            Log.d(TAG, "onResponse: rel = " + linkList.getRel());
                            Log.d(TAG, "onResponse: href = " + linkList.getHref());
                        }
                    } else {
                        try {
                            Log.d(TAG, "onResponse: error body = " + response.errorBody().string());
                        } catch (IOException e) {
                            Log.e(TAG, "onResponse: ", e);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<EndPointList> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        });

        Button showUserInfo = findViewById(R.id.user_info);
        showUserInfo.setOnClickListener(v -> {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retro = new Retrofit.Builder()
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            VApiService service = retro.create(VApiService.class);

            Call<UserEndPoint> endPoints = service.getUserInfo(secureKey);
            endPoints.enqueue(new Callback<UserEndPoint>() {
                @Override
                public void onResponse(@NonNull Call<UserEndPoint> call, @NonNull Response<UserEndPoint> response) {
                    Log.d(TAG, "onResponse: ok");
                    if (response.body() != null) {
                        Log.d(TAG, "onResponse: body ok : " + response.body());
                        UserContent userContent = response.body().getContent().get(0);
                        Log.d(TAG, "onResponse: firstName: " + userContent.getFirstName());
                        Log.d(TAG, "onResponse: name: " + userContent.getName());
                        Log.d(TAG, "onResponse: email:" + userContent.getEmail());
                        Log.d(TAG, "onResponse: birthday: " + userContent.getBirthday());

                    } else {
                        try {
                            Log.d(TAG, "onResponse: error body = " + response.errorBody().string());
                        } catch (IOException e) {
                            Log.e(TAG, "onResponse: ", e);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserEndPoint> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        });

    }
}
