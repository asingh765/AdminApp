package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;

public class CreateNewBookActivity extends AppCompatActivity {

    EditText bookname, authorname, edition, isdn, preface, yop;
    Spinner bookcat;
    Button submitBtn;
    ImageView imageviewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_book);

        bookname = findViewById(R.id.booknameETQR);
        authorname = findViewById(R.id.authornameET);
        edition = findViewById(R.id.editionET);
        isdn = findViewById(R.id.isdnET);
        preface = findViewById(R.id.prefaceET);
        yop = findViewById(R.id.yopET);
        submitBtn = findViewById(R.id.submitBtn);
        bookcat = findViewById(R.id.bookcatdrpdwn);

    }

    public void generateQrCode(View view){

        QRCodeWriter qrCodeWriter = new QRCodeWriter();


        if(isEmpty(bookname) || isEmpty(authorname) || isEmpty(edition) || isEmpty(isdn) || isEmpty(preface) || isEmpty(yop)){
            Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
        }
        else if(bookcat.getSelectedItem().toString().equals("Select Book Category")){
            Toast.makeText(this, "Please select book category", Toast.LENGTH_SHORT).show();
        }
        else {
            HashMap<String, String> booksinfo = new HashMap<>();
            booksinfo.put("bookname", bookname.getText().toString());
            booksinfo.put("authorname", authorname.getText().toString());
            booksinfo.put("edition", edition.getText().toString());
            booksinfo.put("isdn", isdn.getText().toString());
            booksinfo.put("preface", preface.getText().toString());
            booksinfo.put("yop", yop.getText().toString());
            booksinfo.put("category", bookcat.getSelectedItem().toString());

            try {
                BitMatrix bitMatrix = qrCodeWriter.encode(booksinfo.toString(), BarcodeFormat.QR_CODE,200,200);
                Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);

                for(int x=0; x<200; x++){
                    for(int y =0;y<200;y++){
                        bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.WHITE);
                    }
                }

                //open custom dialogue to display qr code
                CreateNewBookQRDialogue dialogue = new CreateNewBookQRDialogue();
                dialogue.show(getSupportFragmentManager(),"Qr Code Dialogue");

                Bundle args = new Bundle();
                args.putParcelable("QrCodeImage", bitmap);
                args.putSerializable("BooksHashmap",booksinfo);

                dialogue.setArguments(args);
                dialogue.setCancelable(false);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void ScanQRCode(View view){

    }
}
