package com.example.adminapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    List<String> bookList  = new ArrayList<>();
    //ArrayAdapter<String> arrayAdapter;
    DatabaseReference myRef;
    ArrayAdapter<String> adapter;
    private Context mContext;
    ListView listView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getActivity();
        populateBookList();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View HomeFragView = inflater.inflate(R.layout.fragment_home, container, false);
        listView = HomeFragView.findViewById(android.R.id.list);
        TextView emptyTextView = HomeFragView.findViewById(android.R.id.empty);
        listView.setEmptyView(emptyTextView);

        //bookList = new ArrayList<>();
        //bookList.add("a");
        //populateBookList();

        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, bookList);
        listView.setAdapter(adapter);

        return HomeFragView;
    }



    private void populateBookList() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("books_db");

        //bookList = new ArrayList<>();
        bookList.clear();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    bookList.add(snapshot.child("bookname").getValue().toString());
                }
                adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, bookList);
                listView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}