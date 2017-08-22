package com.example.nathali.intentservicesalarios;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView resultnuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultnuevo=(TextView) findViewById(R.id.textresult);
    }

    public void Salario(View v){

        if(estaConectado()){
            Intent intent= new Intent(MainActivity.this,ServiceSalario.class);
            startService(intent);

        }else{
            Toast.makeText(MainActivity.this, "Requiere Conexión a Internet", Toast.LENGTH_LONG).show();
        }

    }

    protected void onDestroy() {
        super.onDestroy();

    }

    protected Boolean estaConectado(){

        if(conectadoWifi()){
            return true;
        }else if(conectadoRedMovil()){
           return true;
        }else{
            return false;
        }
    }


    protected Boolean conectadoWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected Boolean conectadoRedMovil(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

}
