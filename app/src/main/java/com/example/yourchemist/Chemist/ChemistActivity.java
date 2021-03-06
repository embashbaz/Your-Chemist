package com.example.yourchemist.Chemist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.yourchemist.R;

import java.util.Objects;

public class ChemistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemist);
    }

    public void setActionBarTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }
}
