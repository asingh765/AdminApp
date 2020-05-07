package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.content.ContentValues.TAG;

public class ScanQRCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView zXingScannerView;
    EditText bookname, authorname,edition, isdn, preface, yop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_scan_qrcode);


        if(isCameraPermissionGranted()){
            System.out.println("Camera permission granted");
            zXingScannerView =  new ZXingScannerView(this);
            setContentView(zXingScannerView);
            zXingScannerView.setResultHandler(this);
            zXingScannerView.startCamera();

        }
        else{
            onBackPressed();
        }

    }

    private  boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(ScanQRCodeActivity.this, android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Camera Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Camera Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Camera Permission is granted");
            return true;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch(requestCode){
            case REQUEST_CAMERA:
                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted){
                        Toast.makeText(this, "PErmission granted", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(this, "Permission Denqwweied", Toast.LENGTH_SHORT).show();
                        //ActivityCompat.requestPermissions(Homepage.this, new String[]{CAMERA}, REQUEST_CAMERA);
                    }
                }
        }

        }
        */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void handleResult(Result result) {
        //Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();

        if(result.getText().equals("")){
            onResume();
        }
        else{
            //fetched the data from QR code. Now do the processing.
            try {
                zXingScannerView.stopCamera();
                setContentView(R.layout.activity_scan_qrcode);
                HashMap<String, String> BookDetailsHashmap = new HashMap<>();
                BookDetailsHashmap = StringToHashmap(result.getText());

                if(BookDetailsHashmap.get("authorname").equals("") || BookDetailsHashmap.get("bookname").equals("") || BookDetailsHashmap.get("edition").equals("") || BookDetailsHashmap.get("isdn").equals("") || BookDetailsHashmap.get("preface").equals("") || BookDetailsHashmap.get("yop").equals("")){
                    onBackPressed();
                    Toast.makeText(this, "Some of book data fields are empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    //populate all the fields with qr code result data.
                    System.out.println(BookDetailsHashmap.toString());
                    bookname = findViewById(R.id.booknameETQR);
                    bookname = findViewById(R.id.booknameETQR);
                    authorname = findViewById(R.id.authornameET);
                    edition = findViewById(R.id.editionET);
                    isdn = findViewById(R.id.isdnET);
                    preface = findViewById(R.id.prefaceET);
                    yop = findViewById(R.id.yopET);


                    bookname.setText(BookDetailsHashmap.get("bookname"));
                    authorname.setText(BookDetailsHashmap.get("authorname"));
                    edition.setText(BookDetailsHashmap.get("edition"));
                    isdn.setText(BookDetailsHashmap.get("isdn"));
                    preface.setText(BookDetailsHashmap.get("preface"));
                    yop.setText(BookDetailsHashmap.get("yop"));
                }
            }catch (Exception e){
                e.printStackTrace();
                onBackPressed();
                Toast.makeText(this, "No Book Data Found", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zXingScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private HashMap<String, String> StringToHashmap(String mainstring){
        String[] arr = mainstring.split(", ");
        HashMap<String, String> map = new HashMap<String, String>();
        for (String str : arr) {
            str = str.replace("{", "").replace("}", "");
            String[] splited = str.split("=");

            map.put(splited[0].trim(), splited[1].trim());

        }
        return map;
    }

}
