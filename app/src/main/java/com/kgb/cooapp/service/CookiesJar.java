package com.kgb.cooapp.service;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @author Krzysztof Betlej <k.betlej@samsung.com>.
 * @date 3/14/18
 * @copyright Copyright (c) 2016 by Samsung Electronics Polska Sp. z o. o.
 */

public class CookiesJar implements CookieJar {
    public static final String TAG = CookiesJar.class.getSimpleName();
    private List<Cookie> mCookies = new ArrayList<>();
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        Log.d(TAG, "saveFromResponse: before add new cookies = " + mCookies);
        List<Cookie> cookieToRemove = new ArrayList<>();
        for (Cookie cookie : cookies) {
             for (Cookie oldCookie : mCookies) {
                 if (cookie.name().equals(oldCookie.name())) {
                     cookieToRemove.add(oldCookie);
                 }
             }
        }
        mCookies.removeAll(cookieToRemove);
        Log.d(TAG, "saveFromResponse: cookies to add = " + cookies);
        mCookies.addAll(cookies);
        Log.d(TAG, "saveFromResponse: cookies = " + mCookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> actualCookie = new ArrayList<>();
        for (Iterator<Cookie> it = mCookies.iterator(); it.hasNext(); ) {
            Cookie cookie = it.next();
            if (cookie.expiresAt() < System.currentTimeMillis()) {
                continue;
            }
            actualCookie.add(cookie);
        }
        mCookies.clear();
        mCookies.addAll(actualCookie);
        return actualCookie;
    }

    public String cookiesAsString() {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Cookie cookie : mCookies) {
            if (!first) {
                builder.append("; ");
            }
            builder.append(cookie.name()).append("=").append(cookie.value());
            first = false;
        }
        Log.d(TAG, "cookiesAsString: string " + builder.toString());
        return builder.toString();
    }
}
