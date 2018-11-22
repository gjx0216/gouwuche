package com.baway.guo.rikao20.Model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.baway.guo.rikao20.UrlUtools.App;
import com.baway.guo.rikao20.UrlUtools.OkHttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UrlModel {

    public void getUrl(String url, final HttpData httpData) {
        OkHttp.enqueueGET(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(App.context, "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String s = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        httpData.getUrl(s);
                    }
                });

            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public interface HttpData {
        void getUrl(String s);
    }
}
