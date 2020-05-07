package com.example.adminapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.Manifest.permission_group.CAMERA;
import static android.content.ContentValues.TAG;

public class SearchFragment extends Fragment {

    private Button createNewBookBtn,scanBookBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        createNewBookBtn = getView().findViewById(R.id.createNewBookBtn);
        scanBookBtn = getView().findViewById(R.id.scanBookBtn);

        createNewBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateNewBookActivity.class);
                startActivity(intent);
            }
        });

        scanBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add logic to open QR Code Scanner to scan barcode
                System.out.println("scan button clicked");

                Intent intent2 = new Intent(getActivity(),ScanQRCodeActivity.class);
                startActivity(intent2);


            }
        });

        super.onViewCreated(view, savedInstanceState);
    }








}
