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
import android.widget.ListView;
import android.widget.TextView;

import com.example.dobretotamje.autobazar.ORM.znacka;
import com.example.dobretotamje.autobazar.ORM.znackaTable;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinkedList<znacka> select = znackaTable.Select();
        ZnackaAdapter mAdapter = new ZnackaAdapter(this.getApplicationContext(), R.layout.listview_znacka, select);
        ListView lv = findViewById(R.id.lstviewZnacky);
        lv.setAdapter(mAdapter);
    }

    ListView.OnItemClickListener myListener = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ZnackaAdapter.ZnackaHolder entry = (ZnackaAdapter.ZnackaHolder) adapterView.getItemAtPosition(i);
            Intent selectAutoActivity = new Intent(getBaseContext(), SelectAutoActivity.class);
            selectAutoActivity.putExtra("znackaId", entry.txtId.getText());
            startActivity(selectAutoActivity);
        }
    };

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
                znackaHolder.textKoncern = row.findViewById(R.id.txtKoncern);
                znackaHolder.txtId = row.findViewById(R.id.txtId);

                row.setTag(znackaHolder);
            } else {
                znackaHolder = (ZnackaHolder) row.getTag();
            }

            znacka zna = data.get(position);
            znackaHolder.txtNazev.setText(zna.Nazev);
            znackaHolder.txtZemeVyroby.setText(zna.Zeme_vyroby);
            znackaHolder.textKoncern.setText(zna.Koncern);
            znackaHolder.textKoncern.setText(zna.Zn_id);

            return row;
        }

        private class ZnackaHolder {
            TextView txtNazev;
            TextView txtZemeVyroby;
            TextView textKoncern;
            TextView txtId;
        }
    }
}
