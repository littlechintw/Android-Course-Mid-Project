package com.littlechin.android.midproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button historyBtn = findViewById(R.id.historyBtn);
        EditText nameInput = findViewById(R.id.nameInput);
        EditText phoneInput = findViewById(R.id.phoneInput);

        TextView food1Num = findViewById(R.id.food1Num);
        TextView food2Num = findViewById(R.id.food2Num);

        historyBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (nameInput.getText().toString().matches("") || phoneInput.getText().toString().matches("")) {
                            Toast.makeText(MainActivity.this, "You must write your name and phone", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, History.class);
                            bundle.putString("name", nameInput.getText().toString());
                            bundle.putString("phone", phoneInput.getText().toString());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                }
        );

        Button orderBtn = findViewById(R.id.orderBtn);
        CheckBox tablewareCheck = findViewById(R.id.tablewareCheck);
        CheckBox bagCheck = findViewById(R.id.bagCheck);

        orderBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (nameInput.getText().toString().matches("") || phoneInput.getText().toString().matches("")) {
                            Toast.makeText(MainActivity.this, "You must write your name and phone", Toast.LENGTH_SHORT).show();
                        }
                        else if (Integer.parseInt(food1Num.getText().toString()) == 0 && Integer.parseInt(food2Num.getText().toString()) == 0) {
                            Toast.makeText(MainActivity.this, "You must order something", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, Order.class);
                            bundle.putString("name", nameInput.getText().toString());
                            bundle.putString("phone", phoneInput.getText().toString());
                            bundle.putString("food1", food1Num.getText().toString());
                            bundle.putString("food2", food2Num.getText().toString());
                            bundle.putString("tableware", String.valueOf(tablewareCheck.isChecked()));
                            bundle.putString("bag", String.valueOf(bagCheck.isChecked()));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                }
        );

        Button food1SubBtn = findViewById(R.id.food1SubBtn);
        Button food1AddBtn = findViewById(R.id.food1AddBtn);

        food1SubBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = Integer.parseInt(food1Num.getText().toString());
                        if (i > 0)
                            food1Num.setText(String.valueOf(i - 1));
                        else if (i == 0)
                            Toast.makeText(MainActivity.this, "0 is lowest", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        food1AddBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = Integer.parseInt(food1Num.getText().toString());
                        if (i < 10)
                            food1Num.setText(String.valueOf(i + 1));
                        else if (i == 10)
                            Toast.makeText(MainActivity.this, "10 is highest", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Button food2SubBtn = findViewById(R.id.food2SubBtn);
        Button food2AddBtn = findViewById(R.id.food2AddBtn);

        food2SubBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = Integer.parseInt(food2Num.getText().toString());
                        if (i > 0)
                            food2Num.setText(String.valueOf(i - 1));
                        else if (i == 0)
                            Toast.makeText(MainActivity.this, "0 is lowest", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        food2AddBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = Integer.parseInt(food2Num.getText().toString());
                        if (i < 10)
                            food2Num.setText(String.valueOf(i + 1));
                        else if (i == 10)
                            Toast.makeText(MainActivity.this, "10 is highest", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}