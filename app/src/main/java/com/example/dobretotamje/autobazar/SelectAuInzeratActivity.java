package com.example.dobretotamje.autobazar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dobretotamje.autobazar.ORM.au_inzerat;
import com.example.dobretotamje.autobazar.ORM.au_inzeratTable;

import java.util.LinkedList;
import java.util.List;

public class SelectAuInzeratActivity extends Activity {

    int autoId = 0;

    ListView.OnItemClickListener myListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //au_inzerat au = (au_inzerat) adapterView.getItemAtPosition(i);
            //Intent selectAutoActivity = new Intent(getBaseContext(), SelectAutoActivity.class);
            //selectAutoActivity.putExtra("auInzeratId", String.valueOf(au.In_id));
            //startActivity(selectAutoActivity);
        }
    };

    Button.OnClickListener btnListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent selectNahradniDilActivity = new Intent(getBaseContext(), SelectNahradniDilActivity.class);
            selectNahradniDilActivity.putExtra("autoId", String.valueOf(autoId));
            startActivity(selectNahradniDilActivity);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_au_inzerat);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String auId = (String) bundle.get("autoId");
            LinkedList<au_inzerat> au_inzerats = au_inzeratTable.Select_Au_Id(Integer.parseInt(auId));

            InzeratAdapter mAdapter = new InzeratAdapter(this.getApplicationContext(), R.layout.listview_au_inzerat, au_inzerats);
            ListView lv = findViewById(R.id.lstviewAuInzeraty);
            lv.setAdapter(mAdapter);
            lv.setOnItemClickListener(myListener);

            Button btn = findViewById(R.id.btn_nahradniDily);
            btn.setOnClickListener(btnListener);
        }
    }

    private class InzeratAdapter extends ArrayAdapter<au_inzerat> {

        Context context;
        int layoutResourceId;
        List<au_inzerat> data;

        private InzeratAdapter(Context context, int layoutResourceId, List<au_inzerat> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            InzeratHolder autoHolder;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layoutResourceId, parent, false);

                autoHolder = new InzeratHolder();
                autoHolder.txtPopis = row.findViewById(R.id.auInz_txtPopis);
                autoHolder.txtCena = row.findViewById(R.id.auInz_txtCena);
                autoHolder.txtMisto = row.findViewById(R.id.auInz_txtMisto);
                autoHolder.txtRokVyroby = row.findViewById(R.id.auInz_txtRokVyroby);
                autoHolder.txtRozvody = row.findViewById(R.id.auInz_txtRozvody);
                autoHolder.txtStavKilometru = row.findViewById(R.id.auInz_txtStavKilometru);
                autoHolder.txtVzorekPneu = row.findViewById(R.id.auInz_txtVzorekPneu);
                autoHolder.txtBourane = row.findViewById(R.id.auInz_txtBourane);
                autoHolder.txtVybava = row.findViewById(R.id.auInz_txtVybava);
                autoHolder.txtVymenaSpojky = row.findViewById(R.id.auInz_txtVymenaSpojky);
                autoHolder.txtMajitel = row.findViewById(R.id.auInz_txtMajitel);
                autoHolder.txtAuId = row.findViewById(R.id.auInz_txtAuId);
                autoHolder.txtUId = row.findViewById(R.id.auInz_txtUId);

                row.setTag(autoHolder);
            } else {
                autoHolder = (InzeratHolder) row.getTag();
            }

            au_inzerat au = data.get(position);
            autoHolder.txtPopis.setText(au.Popis);
            autoHolder.txtCena.setText("Cena: " + String.valueOf(au.Cena));
            autoHolder.txtMisto.setText("Místo: " + au.Misto);
            autoHolder.txtRokVyroby.setText("Rok Výroby: " + String.valueOf(au.Rok_vyroby));
            autoHolder.txtRozvody.setText("Rozvody: " + String.valueOf(au.Rozvody));
            autoHolder.txtStavKilometru.setText("Stav kilometrů: " + String.valueOf(au.Stav_kilometru));
            autoHolder.txtVzorekPneu.setText("Vzorek pneu: " + String.valueOf(au.Vzorek_pneu));
            autoHolder.txtBourane.setText("Bourané?: " + au.Bourane);
            autoHolder.txtVybava.setText("Výbava: " + au.Vybava);
            autoHolder.txtVymenaSpojky.setText("Výměna spojky: " + String.valueOf(au.Vymena_spojky));
            autoHolder.txtMajitel.setText("Majitel: " + String.valueOf(au.Majitel));
            autoHolder.txtAuId.setText(String.valueOf(au.Au_id));
            autoHolder.txtUId.setText(String.valueOf(au.U_id));

            return row;
        }

        private class InzeratHolder {
            TextView txtPopis;
            TextView txtCena;
            TextView txtMisto;
            TextView txtRokVyroby;
            TextView txtRozvody;
            TextView txtStavKilometru;
            TextView txtVzorekPneu;
            TextView txtBourane;
            TextView txtVybava;
            TextView txtVymenaSpojky;
            TextView txtMajitel;
            TextView txtAuId;
            TextView txtUId;
        }
    }
}
