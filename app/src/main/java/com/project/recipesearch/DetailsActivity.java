package com.project.recipesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    TextView txtTitle, txtlink, txting;
    ImageView imgThum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        txting = findViewById(R.id.txtDecIng);
        txtlink = findViewById(R.id.txtDecLink);
        txtTitle = findViewById(R.id.txtDetTitle);
        imgThum = findViewById(R.id.imgThum);

        Intent intent = getIntent();
        txtTitle.setText(intent.getStringExtra("Title"));
        txtlink.setText(intent.getStringExtra("href"));
        txting.setText(intent.getStringExtra("ingredients"));

        Picasso.with(getApplicationContext()).load(intent.getStringExtra("thumbnail")).into(imgThum);
    }
}