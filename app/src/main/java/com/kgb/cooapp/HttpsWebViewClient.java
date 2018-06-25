package com.kgb.cooapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Krzysztof Betlej <k.betlej@samsung.com>.
 * @date 3/14/18
 * @copyright Copyright (c) 2016 by Samsung Electronics Polska Sp. z o. o.
 */

public class HttpsWebViewClient extends WebViewClient {

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        Log.d("CHECK", "onReceivedSslError");
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        AlertDialog alertDialog = builder.create();
        String message = "Certificate error.";
        switch (error.getPrimaryError()) {
            case SslError.SSL_UNTRUSTED:
                message = "The certificate authority is not trusted.";
                break;
            case SslError.SSL_EXPIRED:
                message = "The certificate has expired.";
                break;
            case SslError.SSL_IDMISMATCH:
                message = "The certificate Hostname mismatch.";
                break;
            case SslError.SSL_NOTYETVALID:
                message = "The certificate is not yet valid.";
                break;
        }
        message += " Do you want to continue anyway?";
        alertDialog.setTitle("SSL Certificate Error");
        alertDialog.setMessage(message);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", (dialog, which) -> {
            Log.d("CHECK", "Button ok pressed");
            // Ignore SSL certificate errors
            handler.proceed();
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> {
            Log.d("CHECK", "Button cancel pressed");
            handler.cancel();
        });
        alertDialog.show();
    }
}
