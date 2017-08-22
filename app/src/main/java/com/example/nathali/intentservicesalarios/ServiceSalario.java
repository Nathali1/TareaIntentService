package com.example.nathali.intentservicesalarios;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ServiceSalario extends IntentService {

    public ServiceSalario() {
        super("ServiceSalario");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {

            Salarios();
        }

    }

    public void Salarios() {

        String devuelve = "";

        URL url = null;

        try {
            url = new URL("http://paseoporlagranja.com/WebServices/listasalarios.php");
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }


        HttpURLConnection connection = null;
        int respuesta;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0*(Linux;Android 1.5;es-ES)Ejemplo HTTP");

            respuesta = connection.getResponseCode();

            StringBuilder result = new StringBuilder();

            if (respuesta == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                JSONObject respuestaJSON = null;
                try {
                    respuestaJSON = new JSONObject(result.toString());
                    String resultJSON = respuestaJSON.getString("estado");

                    if (resultJSON == "1") {
                        JSONArray result2JSON = respuestaJSON.getJSONArray("salarios");

                        for (int i = 0; i < result2JSON.length(); i++) {
                            String anio = result2JSON.getJSONObject(i).getString("Anio");
                            Double salario = result2JSON.getJSONObject(i).getDouble("Uni");
                            String salario2 = String.valueOf(salario);
                            devuelve = "Anio: " + anio + " Salario: " + salario2 + "\n";

                            System.out.println(devuelve);
                        }

                    } else if (resultJSON == "2") {
                        System.out.println("No hay salarios");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}