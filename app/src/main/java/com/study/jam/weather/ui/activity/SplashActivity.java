package com.study.jam.weather.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.study.jam.weather.R;
/**
 * @author cikeron 2016
 */
public class SplashActivity extends AppCompatActivity {
    // Temporizador para la pantalla de bienvenida
    private static int SPLASH_TIEMPO = 3000;

public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(SPLASH_TIEMPO);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

/* En caso de ser necesario usar la tecla back
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
*/
    @Override
    protected void onStop() {
        //unregisterReceiver(receiver);
        super.onStop();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
            overridePendingTransition(0,0);
        }
    };
}
