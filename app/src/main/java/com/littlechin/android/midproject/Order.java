package com.littlechin.android.midproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Order extends AppCompatActivity {

    Boolean whileFlag = true;
    Boolean initFlag = false;
    Boolean httpFlag = false;

    // 建立OkHttpClient
    OkHttpClient client = new OkHttpClient().newBuilder().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        TextView orderName = (TextView) findViewById(R.id.orderName);
        TextView orderPhone = (TextView) findViewById(R.id.orderPhone);
        TextView orderFood1 = (TextView) findViewById(R.id.orderFood1);
        TextView orderFood2 = (TextView) findViewById(R.id.orderFood2);
        TextView orderTableware = (TextView) findViewById(R.id.orderTableware);
        TextView orderBag = (TextView) findViewById(R.id.orderBag);

        Bundle bundle = this.getIntent().getExtras();

        String name = bundle.getString("name");
        orderName.setText("姓名: " + name);

        String phone = bundle.getString("phone");
        orderPhone.setText("電話: " + phone);

        String food1 = bundle.getString("food1");
        orderFood1.setText("原味意麵 " + food1 + " 碗");

        String food2 = bundle.getString("food2");
        orderFood2.setText("辣味意麵 " + food2 + " 碗");

        String tableware = bundle.getString("tableware");
        orderTableware.setText("餐具: " + tableware);

        String bag = bundle.getString("bag");
        orderBag.setText("袋子: " + bag);

        TextView apiStatus = (TextView) findViewById(R.id.apiStatus);
        apiStatus.setText("訂單發送中");
        httpFlag = false;
        Log.d("httpFlagInit", String.valueOf(httpFlag));

        String urlOptions = "name=" + name + "&phone=%27" + phone + "&food1=" + food1 + "&food2=" + food2 + "&tableware=" + tableware + "&bag=" + bag;
        // 建立Request，設置連線資訊
        Request request = new Request.Builder()
                .url("https://script.google.com/macros/s/AKfycbzOGAmcLibHM-mHi8-hVmKV4gwGLV71b50bU2HLvfKCZ-aDKcK-FSoxFhsoyIGNRgosLg/exec?" + urlOptions)
                .build();

        // 建立Call
        Call call = client.newCall(request);

        // 執行Call連線到網址
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 連線成功，自response取得連線結果
                String result = response.body().string();
                Log.d("OkHttp result", result);
                initFlag = true;
                httpFlag = true;
                Log.d("httpFlagProcess", String.valueOf(httpFlag));
            }
            @Override
            public void onFailure(Call call, IOException e) {
                initFlag = true;
                // 連線失敗
            }
        });

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    Thread.sleep( 500 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        while(whileFlag){
                            try {Thread.sleep(500);} catch (Exception e) {};
                            if (initFlag){
                                whileFlag = false;
                                if (httpFlag)
                                    apiStatus.setText("訂單發送完成");
                                else
                                    apiStatus.setText("訂單發送失敗，請確認網路連線");
                            }
                        }
                    }
                });

            }
        }).start();
    }
}

