package com.example.samsung.p0971_servicebindclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "myLogs";
    private String message = "";

    boolean bound = false;
    ServiceConnection serviceConnection;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(getString(R.string.intent_filter_name));

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                message = "MainActivity onServiceConnected()";
                Log.d(LOG_TAG, message);
                Toast.makeText(MainActivity.this, message, LENGTH_SHORT).show();
                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                message = "MainActivity onServiceDisconnected()";
                Log.d(LOG_TAG, message);
                Toast.makeText(MainActivity.this, message, LENGTH_SHORT).show();
                bound = false;
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onClickBtn((Button)findViewById(R.id.btnUnBind));
    }

    public void onClickBtn(View view) {

        switch (view.getId()) {

            case R.id.btnStart:
                startService(intent);
                break;
            case R.id.btnStop:
                stopService(intent);
                break;
            case R.id.btnBind:
                //Автоматический запуск сервиса при попытке подключения к нему, если он не был запущен ранее.
//                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                //Попытка подключения к сервису без флага автоматического запуска, если он не был запущен ранее.
                bindService(intent, serviceConnection, 0);
                break;
            case R.id.btnUnBind:
                if (!bound) return;
                unbindService(serviceConnection);
                bound = false;
                break;

        }

    }
}
