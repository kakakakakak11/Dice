package com.example.dice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class SplashScreen extends AppCompatActivity {
    private FirebaseRemoteConfig remoteConfig;
    private FirebaseRemoteConfigSettings settings;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
        int time = sp.getInt("key_int", 0);

        Log.i("MyLog", "Time: " + time);

        remoteConfig = FirebaseRemoteConfig.getInstance();
        settings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(time)
                .build();
        remoteConfig.setConfigSettingsAsync(settings);
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default);
        getData();

    }

    private void connectMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void connectWebActivity() {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
        finish();
    }

    private void getData() {

        remoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean value;
                            remoteConfig.fetchAndActivate();
                            value = remoteConfig.getBoolean("key");
                            Log.i("MyLog", "Value: " + value);

                            if (value) {
                                time = 3600;
                                SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt("key_int", time);
                                editor.commit();

                                Toast.makeText(SplashScreen.this, "True", Toast.LENGTH_SHORT).show();
                                connectWebActivity();
                            } else {
                                Toast.makeText(SplashScreen.this, "False", Toast.LENGTH_SHORT).show();
                                connectMainActivity();
                            }
                        } else
                            Toast.makeText(SplashScreen.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}