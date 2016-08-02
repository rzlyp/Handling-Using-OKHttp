package com.rizal.perpusonline;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rizal.perpusonline.adapter.BukuAdapter;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.root_layout,ListBukuFragment.newInstance(),"Buku")
            .commit();
        }
    }




}
