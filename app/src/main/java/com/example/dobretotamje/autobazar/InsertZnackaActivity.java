package com.example.dobretotamje.autobazar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dobretotamje.autobazar.ORM.znacka;

public class InsertZnackaActivity extends Activity {

    ListView.OnClickListener myListener = new ListView.OnClickListener() {
        @Override
        public void onClick(View v) {

            TextView nazev = findViewById(R.id.zn_InsertNazev);
            TextView zemeVyroby = findViewById(R.id.zn_InsertZemeVyroby);
            TextView koncern = findViewById(R.id.zn_InsertKoncern);

            if (nazev.getText().length() == 0 || zemeVyroby.getText().length() == 0 || koncern.getText().length() == 0 ) {
                Toast.makeText(getApplicationContext(), "Byly zadány špatné údaje", Toast.LENGTH_SHORT).show();
                return;
            }

            znacka zn = new znacka();
            zn.Nazev = nazev.getText().toString();
            zn.Zeme_vyroby = zemeVyroby.getText().toString();
            zn.Koncern = koncern.getText().toString();
            DataSupplier.insertZnacka(zn);

            Toast.makeText(getApplicationContext(), "Značka byla vložena", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_znacka);

        Button button = findViewById(R.id.zn_PridatZnacku);
        button.setOnClickListener(myListener);
    }
}
