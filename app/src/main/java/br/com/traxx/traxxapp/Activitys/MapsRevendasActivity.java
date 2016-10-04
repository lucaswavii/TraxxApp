package br.com.traxx.traxxapp.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.traxx.traxxapp.R;

public class MapsRevendasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_revendas);
        getSupportActionBar().hide();
    }
}
