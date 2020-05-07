package com.example.adminapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class CreateNewBookQRDialogue extends AppCompatDialogFragment {

    private ImageView QRCodeImageView;
    DatabaseReference myRef;
    private EditText bookname,authorname,edition,isdn,preface,yop;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.createnewbook_dialogue,null);

        QRCodeImageView = view.findViewById(R.id.QRCodeImageView);
        final Bitmap receivedBitmap = getArguments().getParcelable("QrCodeImage");
        QRCodeImageView.setImageBitmap(receivedBitmap);

        bookname = getActivity().findViewById(R.id.booknameETQR);
        authorname = getActivity().findViewById(R.id.authornameET);
        edition = getActivity().findViewById(R.id.editionET);
        isdn = getActivity().findViewById(R.id.isdnET);
        preface = getActivity().findViewById(R.id.prefaceET);
        yop = getActivity().findViewById(R.id.yopET);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("books_db");

        builder.setView(view)
                .setTitle("QR CODE")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //write logic to close the dialogue box
                    }
                })
                .setPositiveButton("Save To Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //write logic to download the bitmap to gallery.
                        if(isStoragePermissionGranted()) {
                            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), receivedBitmap, "abc", "desc");

                            HashMap<String, String> BooksHashmap = new HashMap<>();
                            BooksHashmap = (HashMap<String, String>) getArguments().getSerializable("BooksHashmap");
                            CreateNewBookEntryFB(BooksHashmap);

                            Snackbar snackbar = Snackbar
                                    .make(getActivity().findViewById(android.R.id.content), "QR Code successfully saved in gallery. Book details have been added to database", Snackbar.LENGTH_LONG);
                            snackbar.show();


                            bookname.setText("");
                            authorname.setText("");
                            edition.setText("");
                            isdn.setText("");
                            preface.setText("");
                            yop.setText("");



                        }
                    }
                });
        return builder.create();

    }



    private  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
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




    private void CreateNewBookEntryFB(HashMap<String, String> bookDataHashMap){

        Book newbook = new Book(bookDataHashMap.get("bookname"),bookDataHashMap.get("authorname"),bookDataHashMap.get("edition"),bookDataHashMap.get("isdn"),bookDataHashMap.get("preface"),bookDataHashMap.get("yop"));

        myRef.child(newbook.getBookname().toLowerCase()).setValue(bookDataHashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                /*Snackbar snackbar = Snackbar
                        .make(getActivity().findViewById(android.R.id.content), "User Created Successfully", Snackbar.LENGTH_LONG);
                snackbar.show();*/
                System.out.println("Book data added Successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Unable to upload book details to db");
                e.printStackTrace();
            }
        });
    }
}
