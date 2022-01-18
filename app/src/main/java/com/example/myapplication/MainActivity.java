package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private String folder_path;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private int request_code;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Wait for result of latest activity
        String returnedResult = data.getData().toString();
        System.out.println("--> returnedResult: " + returnedResult);

        //Change textView value
        TextView path_textview = (TextView) findViewById(R.id.pathfield);
        path_textview.setText(returnedResult);

        //Call the callreturn
        endcall1(returnedResult);
    }


    private void showFile(Uri uriToLoad) throws URISyntaxException {
        // Choose a directory using the system's file picker.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when it loads.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uriToLoad);

        startActivityForResult(Intent.createChooser(intent, "Select picture"), request_code);

    }

    public void makecall1(View view) throws URISyntaxException {
        showFile(Uri.EMPTY);
        System.out.println("(makecall1)request_code: " + request_code);
        TextView path_textview = (TextView) findViewById(R.id.pathfield);
        path_textview.setText("Seleccionada");

    }

    private void endcall1(String returnedResult) {

    }
    public static String ACCESS_NETWORK_STATE;
    public void makecall2(View view) {

        try {
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);

            }
            // code block
            //Obtener valores de los campos
            EditText pathfieldtext = (EditText) findViewById(R.id.pathfield);
            EditText hosttext = (EditText) findViewById(R.id.txthost);
            EditText porttext = (EditText) findViewById(R.id.txtport);

            String msgstr = pathfieldtext.getText().toString();
            String host = hosttext.getText().toString();
            String port = porttext.getText().toString();


            //Intentando crear la conexión con el servidor
            System.out.println("Attempting connection at host " + host + " port " + port);

            //Creando la conexión con el servidor
            //final String HOST = "127.0.0.1";
            //Puerto del servidor
            //final int PUERTO = 5000;
            DataInputStream in;
            DataOutputStream out;


            //Creo el socket para conectarme con el cliente
            Socket sc = new Socket(host, Integer.parseInt(port));

            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());

            byte[] msg = msgstr.getBytes(StandardCharsets.UTF_8);
            out.write(msg);

            System.out.println("Mensaje enviado");
            //TextView errors = (TextView) findViewById(R.id.error);
            //errors.setText("El mensaje se ha enviado con éxito");

            Snackbar mySnackbar = Snackbar.make(view, "Mensaje enviado", 3000);
            mySnackbar.show();

        }catch(Exception e){
            System.out.println("Error!!!");
            e.printStackTrace();

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            TextView errors = (TextView) findViewById(R.id.error);
            errors.setText(exceptionAsString);
            //Error snack bar
            Snackbar mySnackbar = Snackbar.make(view, "ERROR mensaje NO enviado", 3000);
            mySnackbar.show();
        }

    }
}

    /*
    private void onActivityResult(){
        System.out.println("(ActivityResult)request_code: " + request_code);
        TextView path_textview = (TextView) findViewById(R.id.path_textview);
        path_textview.setText("Seleccionada");
        
        Uri uri_from_intent = intent.getData();
        String path = uri_from_intent.getPath(); // "file:///mnt/sdcard/FileName.mp3"
        try {
            File file = new File(new URI(path));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("(ActivityResult)uri_from_intent: " + uri_from_intent);
    }
    */
    /*
    private void finishcall1(){
        Uri uri_from_intent = intent.getData();
        String path = uri_from_intent.getPath(); // "file:///mnt/sdcard/FileName.mp3"
        try {
            File file = new File(new URI(path));
        } catch (URISyntaxException e) {
            //e.printStackTrace();
        }
        System.out.println("(ActivityResult)uri_from_intent: " + uri_from_intent);
    }
*/


