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
import android.widget.Toast;

import com.example.dobretotamje.autobazar.ORM.znacka;
import com.example.dobretotamje.autobazar.ORM.znackaTable;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity {

    ListView.OnClickListener searchListener = new ListView.OnClickListener() {
        @Override
        public void onClick(View v) {
            //validace udaju
            TextView cenaOd = findViewById(R.id.txtCenaOd);
            TextView cenaDo = findViewById(R.id.txtCenaDo);
            //TODO check if cenaOdNum neni nikdy null při špatnych vstupech? je to možne vubec?
            int cenaOdNum = Integer.parseInt(cenaOd.getText().toString());
            int cenaDoNum = Integer.parseInt(cenaDo.getText().toString());
            if (cenaOd.getText() != "") {
                cenaOdNum = 0;
            }
            if (cenaDo.getText() != "") {
                cenaDoNum = 999999999;
            }
            if (cenaOdNum > cenaDoNum) {
                Toast.makeText(getApplicationContext(), "Cena od nemůže být větší než cena do!", Toast.LENGTH_SHORT).show();
            } else {
                Intent selectAuInteratActivity = new Intent(getBaseContext(), SelectAuInzeratActivity.class);
                selectAuInteratActivity.putExtra("cenaOd", String.valueOf(cenaOd));
                selectAuInteratActivity.putExtra("cenaDo", String.valueOf(cenaDo));
                startActivity(selectAuInteratActivity);
            }
        }
    };
    ListView.OnItemClickListener myListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            znacka zn = (znacka) adapterView.getItemAtPosition(i);
            Intent selectAutoActivity = new Intent(getBaseContext(), SelectAutoActivity.class);
            selectAutoActivity.putExtra("znackaId", String.valueOf(zn.Zn_id));
            startActivity(selectAutoActivity);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinkedList<znacka> select = znackaTable.Select();
        ZnackaAdapter mAdapter = new ZnackaAdapter(this.getApplicationContext(), R.layout.listview_znacka, select);
        ListView lv = findViewById(R.id.lstviewZnacky);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(myListener);

        Button search = findViewById(R.id.btnSearch);
        search.setOnClickListener(searchListener);
    }

    private class ZnackaAdapter extends ArrayAdapter<znacka> {

        Context context;
        int layoutResourceId;
        List<znacka> data;

        private ZnackaAdapter(Context context, int layoutResourceId, List<znacka> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ZnackaHolder znackaHolder;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layoutResourceId, parent, false);

                znackaHolder = new ZnackaHolder();
                znackaHolder.txtNazev = row.findViewById(R.id.txtNazev);
                znackaHolder.txtZemeVyroby = row.findViewById(R.id.txtZemeVyroby);
                znackaHolder.txtKoncern = row.findViewById(R.id.txtKoncern);
                znackaHolder.txtId = row.findViewById(R.id.txtId);

                row.setTag(znackaHolder);
            } else {
                znackaHolder = (ZnackaHolder) row.getTag();
            }

            znacka zna = data.get(position);
            znackaHolder.txtNazev.setText(zna.Nazev);
            znackaHolder.txtZemeVyroby.setText(zna.Zeme_vyroby);
            znackaHolder.txtKoncern.setText(zna.Koncern);
            znackaHolder.txtId.setText(String.valueOf(zna.Zn_id));

            return row;
        }

        private class ZnackaHolder {
            TextView txtNazev;
            TextView txtZemeVyroby;
            TextView txtKoncern;
            TextView txtId;
        }
    }
}
