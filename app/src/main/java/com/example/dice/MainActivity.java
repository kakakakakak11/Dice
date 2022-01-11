package com.example.dice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView mLeftImageView;
    private ImageView mRightImageView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button rollDicesButton = (Button) findViewById(R.id.button_roll);
        mLeftImageView = (ImageView) findViewById(R.id.imageview_left);
        mRightImageView = (ImageView) findViewById(R.id.imageview_right);
        mTextView = (TextView) findViewById(R.id.textView);

        rollDicesButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                int value1 = randomDiceValue();
                int value2 = randomDiceValue();

                int res1 = getResources().getIdentifier("dice" + value1,
                        "drawable", "com.example.dice");
                int res2 = getResources().getIdentifier("dice" + value2,
                        "drawable", "com.example.dice");

                mLeftImageView.setImageResource(res1);
                mRightImageView.setImageResource(res2);
                mTextView.setText(String.valueOf(value1 + value2));
            }
        });
    }

    private int randomDiceValue() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
}