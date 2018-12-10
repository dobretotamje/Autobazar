package com.example.dobretotamje.autobazar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dobretotamje.autobazar.ORM.au_inzerat;
import com.example.dobretotamje.autobazar.ORM.au_inzeratTable;
import com.example.dobretotamje.autobazar.ORM.auto;
import com.example.dobretotamje.autobazar.ORM.autoTable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class InsertAuInzeratActivity extends Activity {

    Map<String, Integer> nazevToId = new HashMap<>();
    Spinner autoSpinner;

    ListView.OnClickListener pridatInzeratListener = new ListView.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView cena = findViewById(R.id.insAuInz_Cena);
            TextView popis = findViewById(R.id.insAuInz_Popis);
            TextView misto = findViewById(R.id.insAuInz_Misto);
            TextView rokVyroby = findViewById(R.id.insAuInz_RokVyroby);
            TextView rozvody = findViewById(R.id.insAuInz_Rozvody);
            TextView stavKm = findViewById(R.id.insAuInz_StavKm);
            TextView vzorekPneu = findViewById(R.id.insAuInz_VzorekPneu);
            Spinner bourane = findViewById(R.id.insAuInz_Bourane);
            TextView vybava = findViewById(R.id.insAuInz_Vybava);
            TextView vymenaSpojky = findViewById(R.id.insAuInz_VymenaSpojky);
            TextView majitel = findViewById(R.id.insAuInz_Majitel);

            if (cena.getText().length() == 0 || popis.getText().length() == 0 || popis.getText().length() == 0 || misto.getText().length() == 0 || rokVyroby.getText().length() == 0 || rozvody.getText().length() == 0 || stavKm.getText().length() == 0
                    || vzorekPneu.getText().length() == 0 || vybava.getText().length() == 0 || vymenaSpojky.getText().length() == 0 || majitel.getText().length() == 0) {
                Toast.makeText(getApplicationContext(), "Byly zadány špatné údaje", Toast.LENGTH_SHORT).show();
                return;
            }
            String auNazev = (String) autoSpinner.getSelectedItem();

            au_inzerat au_inzerat = new au_inzerat();
            au_inzerat.Au_id = nazevToId.get(auNazev);
            au_inzerat.U_id = 1;
            au_inzerat.Popis = popis.getText().toString();
            au_inzerat.Cena = Integer.parseInt(cena.getText().toString());
            au_inzerat.Misto = misto.getText().toString();
            au_inzerat.Rok_vyroby = Integer.parseInt(rokVyroby.getText().toString());
            au_inzerat.Rozvody = Integer.parseInt(rozvody.getText().toString());
            au_inzerat.Stav_kilometru = Integer.parseInt(stavKm.getText().toString());
            au_inzerat.Vzorek_pneu = Integer.parseInt(vzorekPneu.getText().toString());
            au_inzerat.Bourane = bourane.getSelectedItem().toString();
            au_inzerat.Vybava = vybava.getText().toString();
            au_inzerat.Vymena_spojky = Integer.parseInt(vymenaSpojky.getText().toString());
            au_inzerat.Majitel = Integer.parseInt(majitel.getText().toString());

            au_inzeratTable.Insert(au_inzerat);

            Toast.makeText(getApplicationContext(), "Inzerát byl vložen", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_au_inzerat);

        autoSpinner = findViewById(R.id.insAuInz_AuNazev);

        Button btnPridatInzerat = findViewById(R.id.insAuInz_PridatInzerat);
        btnPridatInzerat.setOnClickListener(pridatInzeratListener);

        LinkedList<auto> select = autoTable.Select();
        String[] autoNameToSpinner = new String[select.size()];
        for (int i = 0; i < select.size(); i++) {
            auto auto = select.get(i);
            nazevToId.put(auto.Nazev, auto.Au_id);
            autoNameToSpinner[i] = auto.Nazev;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, autoNameToSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoSpinner.setAdapter(adapter);
    }
}
