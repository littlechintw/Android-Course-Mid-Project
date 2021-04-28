package com.littlechin.android.midproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class History extends AppCompatActivity {

    Boolean whileFlag = true;
    Boolean initFlag = false;
    String resString = "";

    // 建立OkHttpClient
    OkHttpClient client = new OkHttpClient().newBuilder().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView orderName = (TextView) findViewById(R.id.orderName);
        TextView orderPhone = (TextView) findViewById(R.id.orderPhone);

        Bundle bundle = this.getIntent().getExtras();

        String name = bundle.getString("name");
        orderName.setText("姓名: " + name);

        String phone = bundle.getString("phone");
        orderPhone.setText("電話: " + phone);

        TextView resText = (TextView) findViewById(R.id.resText);
        resText.setText("訂單查詢中");

        String urlOptions = "name=" + name + "&phone=" + phone;
        String url = "https://script.google.com/macros/s/AKfycbznqpYM0piGdRxvjnqcqCJ6bEtF4v6zYiljOzKvs5HPkygH7crcNoNm8otJGG4HAO1pMA/exec?";
        // 建立Request，設置連線資訊
        Request request = new Request.Builder()
                .url(url + urlOptions)
                .build();
        Log.d("url", url + urlOptions);

        // 建立Call
        Call call = client.newCall(request);

        // 執行Call連線到網址
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 連線成功，自response取得連線結果
                String result = response.body().string();
                resString = result;
                Log.d("OkHttp result", result);
                initFlag = true;
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
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        while (whileFlag) {
                            try {
                                Thread.sleep(500);
                            } catch (Exception e) {
                            }
                            ;
                            if (initFlag) {
                                whileFlag = false;
                                JSONObject j = null;
                                String time_stamp = null;
                                String food1 = null;
                                String food2 = null;
                                String tableware = null;
                                String bag = null;

                                try {
                                    j = new JSONObject(resString);
                                    j = j.getJSONObject("message");
                                    System.out.println(j);
                                    time_stamp = String.valueOf(j.get("time_stamp"));
                                    food1 = String.valueOf(j.get("food1"));
                                    food2 = String.valueOf(j.get("food2"));
                                    tableware = String.valueOf(j.get("tableware"));
                                    bag = String.valueOf(j.get("bag"));
                                }catch (JSONException err){
                                    Log.d("Error", err.toString());
                                }
                                String layoutText = "找不到歷史訂單，請確認姓名與電話是否正確";
                                Log.d("why", time_stamp);
                                if (time_stamp != "Not Found") {
                                    layoutText = "最近一次訂購時間: " + time_stamp + "\n原味意麵 " + food1 + " 碗" + "\n辣味意麵 " + food2 + " 碗" + "\n餐具: " + tableware + "\n袋子: " + bag;
                                }
                                resText.setText(layoutText);
                            }
                        }
                    }
                });

            }
        }).start();
    }
}