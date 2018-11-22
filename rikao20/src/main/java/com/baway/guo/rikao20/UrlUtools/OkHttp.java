package com.baway.guo.rikao20.UrlUtools;

import java.util.concurrent.TimeUnit;

import javax.xml.transform.sax.SAXTransformerFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttp {

    private static final String METHOD_GET = "GET";
    private static OkHttpClient okHttpClient;

    private OkHttp() {

    }

    public static void init() {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(3000, TimeUnit.MILLISECONDS)
                .writeTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
    }

    public static Request createRequest(String url, String method) {
        Request.Builder builder = new Request.Builder().url(url);
        Request request = null;
        switch (method) {
            case METHOD_GET:
                request = builder.build();
                break;
        }
        return request;
    }

    public static void enqueueGET(String url, Callback Callback) {
        Request request = createRequest(url, METHOD_GET);
        Call call = okHttpClient.newCall(request);
        call.enqueue(Callback);
    }
}
