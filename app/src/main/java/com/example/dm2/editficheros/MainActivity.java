package com.example.dm2.editficheros;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    TextView tvContenidoFichero;
    EditText introducir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContenidoFichero = (TextView)findViewById(R.id.lbl);
        introducir = (EditText)findViewById(R.id.txt);
    }

    public void crearInterno(View v){
        try{
            OutputStreamWriter osw=
                    new OutputStreamWriter(openFileOutput("archivo_int.txt",
                            Context.MODE_PRIVATE));
            osw.write("Texto de prueba\n");
            osw.close();
        }
        catch (Exception e) {
            Log.e ("Ficheros", "ERROR!! al crear fichero a memoria interna");
        }
    }

    public void crearExterno(View v){
        boolean sdDisponible = false;
        boolean sdAccesoEscritura= false;
        //Comprobamos el estado de la memoria exterena
        String estado= Environment.getExternalStorageState();
        Log.i("Memoria", estado);
        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
            sdAccesoEscritura = true;
        }
        else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdDisponible = true;
            sdAccesoEscritura = false;
        }
        else {
            sdDisponible = false;
            sdAccesoEscritura = false;
        }
        if(sdAccesoEscritura && sdDisponible) {
            try {
                File ruta_sd = Environment.getExternalStorageDirectory();
                File f = new File(ruta_sd.getAbsolutePath(), "archivo_ext.txt");
                OutputStreamWriter osw =
                        new OutputStreamWriter(new FileOutputStream((f)));
                osw.write("Texto de prueba.\n");
                osw.close();
                Log.i("Ficheros", "fichero escrito correctamente");
            } catch (Exception ex) {
                Log.e("Ficheros", "Error al escribir fichero en tarjeta SD");
            }
        }
    }


    public void leerInterno(View v){

        try
        {
            BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    openFileInput("archivo_int.txt")));
            String texto="";
            String linea= fin.readLine();
            while (linea!=null){
                texto=texto+linea+"\n";
                Log.i("Ficheros", linea);
                linea=fin.readLine();
            }
            fin.close();
            tvContenidoFichero.setText(texto);
            Log.i("Ficheros", "Fichero leido!");
            Log.i("Ficheros", "Texto: " + texto);
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }
    }

    public void leerExterno(View v){
        try
        {
            File ruta_sd = Environment.getExternalStorageDirectory();
            File f = new File(ruta_sd.getAbsolutePath(), "archivo_ext.txt");
            BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(f)));
            String texto="";
            String linea= fin.readLine();
            while (linea!=null){
                texto=texto+linea+"\n";
                Log.i("Ficheros", linea);
                linea=fin.readLine();
            }
            fin.close();
            tvContenidoFichero.setText(texto);
            Log.i("Ficheros", "Fichero leido!");
            Log.i("Ficheros", "Texto: " + texto);
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }
    }

    public void leerRecurso(View v){
        try {
            InputStream fraw = getResources().openRawResource(R.raw.prueba_raw);
            BufferedReader brin = new BufferedReader( new
                    InputStreamReader(fraw));
            String texto="";
            String linea= brin.readLine();
            while (linea!=null){
                texto=texto+linea+"\n";
                Log.i("Ficheros", linea);
                linea=brin.readLine();
            }
            fraw.close();
            tvContenidoFichero.setText(texto);
        }
        catch (Exception ex) {
            Log.e ("Ficheros", "Error al leer fichero desde recurso raw");
        }
    }

    public void borrarInterno(View v){
        File dir = getFilesDir();
        File file = new File(dir, "archivo_int.txt");
        file.delete();

    }

    public void borrarExterno(View v){
        File ruta_sd = Environment.getExternalStorageDirectory();
        File f = new File(ruta_sd.getAbsolutePath(), "archivo_ext.txt");
        f.delete();
    }

}



