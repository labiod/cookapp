package com.kgb.cooapp;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends Activity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    private static final String CHECK_AUTHORIZE_URL = "https://login.vorwerk.com/account/oauth/authorize?response_type=token" +
            "&redirect_uri=https%3A%2F%2Fcookidoo.pl%2FvorwerkWebapp%2F" +
            "&client_id=netzkern-client-v1&market=pl";
    private Disposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CookieManager.getInstance().removeAllCookies( c -> {});
        setContentView(R.layout.activity_login);
        WebView webView = findViewById(R.id.login_site);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                Log.d(TAG, "shouldInterceptRequest: url = " + request.getUrl());
//                if (request.getUrl().toString().contains("check")) {// condition to intercept webview's request
//                    return handleIntercept(request);
//                } else
                    return super.shouldInterceptRequest(view, request);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            private WebResourceResponse handleIntercept(WebResourceRequest request){
                OkHttpClient okHttpClient = new OkHttpClient();
                String data = "j_username=gabriela.betlej%40gmail.com&j_password=G%21H%40o3s4t5&_spring_security_remember_me=true";
                final Call call = okHttpClient.newCall(new Request.Builder()
                        .url(request.getUrl().toString())
                        .method(request.getMethod(), RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), data))
                        .headers(Headers.of(request.getRequestHeaders()))
                        .build()
                );
                try {
                    final Response response = call.execute();
                    response.headers();// get response header here
                    for (int i = 0; i < response.headers().size(); ++i) {
                        String name = response.headers().name(i);
                        Log.d(TAG, name + " : " + response.headers().values(name));
                    }
                    return null;
//                    return new WebResourceResponse(
//                            response.header("content-type", "text/plain"), // You can set something other as default content-type
//                            response.header("content-encoding", "utf-8"),  //you can set another encoding as default
//                            response.body().byteStream()
//                    );
                } catch (IOException e) {
                    Log.e(TAG, "handleIntercept: ", e);
                    return null;
                }
            }
        });
        webView.loadUrl(CHECK_AUTHORIZE_URL);
        Observable<String> observable = Observable.create(emitter -> {
            String authKey = null;
            while(authKey == null) {
                SystemClock.sleep(1000);
                CookieManager cookieManager = CookieManager.getInstance();
                Log.d(TAG, "onCreate: cookies: " + cookieManager.toString());
            }
            emitter.onNext(authKey);
            emitter.onComplete();
        });

//        mDisposable = observable
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe( authKey -> {
//                    Intent intent = new Intent();
//                    intent.putExtra(MainActivity.AUTH_KEY, authKey);
//                    setResult(MainActivity.RESULT_CODE, intent);
//                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
