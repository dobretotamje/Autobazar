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

import com.example.dobretotamje.autobazar.ORM.auto;
import com.example.dobretotamje.autobazar.ORM.autoTable;

import java.util.LinkedList;
import java.util.List;

public class SelectAutoActivity extends Activity {

    int znackaId;

    ListView.OnItemClickListener myListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            auto au = (auto) adapterView.getItemAtPosition(i);
            Intent selectAuInzeratActivity = new Intent(getBaseContext(), SelectAuInzeratActivity.class);
            selectAuInzeratActivity.putExtra("autoId", String.valueOf(au.Au_id));
            startActivity(selectAuInzeratActivity);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_auto);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String znId = (String) bundle.get("znackaId");
            znackaId = Integer.parseInt(znId);
            LinkedList<auto> autos = DataSupplier.getFilteredAuta(znackaId);

            AutoAdapter mAdapter = new AutoAdapter(this.getApplicationContext(), R.layout.listview_auto, autos);
            ListView lv = findViewById(R.id.lstviewAuta);
            lv.setAdapter(mAdapter);
            lv.setOnItemClickListener(myListener);
        }
    }

    private class AutoAdapter extends ArrayAdapter<auto> {

        Context context;
        int layoutResourceId;
        List<auto> data;

        private AutoAdapter(Context context, int layoutResourceId, List<auto> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            AutoHolder autoHolder;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layoutResourceId, parent, false);

                autoHolder = new AutoHolder();
                autoHolder.txtNazev = row.findViewById(R.id.txtNazev);
                autoHolder.txtVykon = row.findViewById(R.id.txtVykon);
                autoHolder.txtSpotreba = row.findViewById(R.id.txtSpotreba);
                autoHolder.txtId = row.findViewById(R.id.au_txtId);

                row.setTag(autoHolder);
            } else {
                autoHolder = (AutoHolder) row.getTag();
            }

            auto aut = data.get(position);
            autoHolder.txtNazev.setText(aut.Nazev);
            autoHolder.txtVykon.setText("Výkon: " + String.valueOf(aut.Vykon));
            autoHolder.txtSpotreba.setText("Spotřeba: " + String.valueOf(aut.Spotreba));
            autoHolder.txtId.setText(String.valueOf(aut.Au_id));

            return row;
        }

        private class AutoHolder {
            TextView txtNazev;
            TextView txtVykon;
            TextView txtSpotreba;
            TextView txtId;
        }
    }
}
