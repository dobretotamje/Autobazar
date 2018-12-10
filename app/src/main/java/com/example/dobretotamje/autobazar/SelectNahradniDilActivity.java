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

import com.example.dobretotamje.autobazar.ORM.nahradni_dil;
import com.example.dobretotamje.autobazar.ORM.nahradni_dilTable;

import java.util.LinkedList;
import java.util.List;

public class SelectNahradniDilActivity extends Activity {

    ListView.OnItemClickListener myListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            nahradni_dil nd = (nahradni_dil) adapterView.getItemAtPosition(i);
            Intent selectNdInzeratActivity = new Intent(getBaseContext(), SelectNdInzeratActivity.class);
            selectNdInzeratActivity.putExtra("nahradniDilId", String.valueOf(nd.Nd_id));
            startActivity(selectNdInzeratActivity);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_nahradni_dil);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String auId = (String) bundle.get("autoId");
            LinkedList<nahradni_dil> nahradni_dils = DataSupplier.getAutoFilteredNahradniDily(Integer.parseInt(auId));

            NahradniDilAdapter mAdapter = new NahradniDilAdapter(this.getApplicationContext(), R.layout.listview_nahradni_dil, nahradni_dils);
            ListView lv = findViewById(R.id.lstviewNahradniDily);
            lv.setAdapter(mAdapter);
            lv.setOnItemClickListener(myListener);
        }
    }

    private class NahradniDilAdapter extends ArrayAdapter<nahradni_dil> {

        Context context;
        int layoutResourceId;
        List<nahradni_dil> data;

        private NahradniDilAdapter(Context context, int layoutResourceId, List<nahradni_dil> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            NahradniDilHolder nahrdniDilHolder;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layoutResourceId, parent, false);

                nahrdniDilHolder = new NahradniDilHolder();
                nahrdniDilHolder.txtZnacka = row.findViewById(R.id.nd_txtZnacka);
                nahrdniDilHolder.txtOrig = row.findViewById(R.id.nd_txtOrig);
                nahrdniDilHolder.txtNazev = row.findViewById(R.id.nd_txtNazev);
                nahrdniDilHolder.txtNdId = row.findViewById(R.id.nd_txtNdId);

                row.setTag(nahrdniDilHolder);
            } else {
                nahrdniDilHolder = (NahradniDilHolder) row.getTag();
            }

            nahradni_dil nd = data.get(position);
            nahrdniDilHolder.txtZnacka.setText("Znacka?: " + nd.Znacka);
            nahrdniDilHolder.txtOrig.setText("Originál?: " + nd.Orig);
            nahrdniDilHolder.txtNazev.setText("Název: " + nd.Nazev);
            nahrdniDilHolder.txtNdId.setText(String.valueOf(nd.Nd_id));

            return row;
        }

        private class NahradniDilHolder {
            TextView txtZnacka;
            TextView txtOrig;
            TextView txtNazev;
            TextView txtNdId;
        }
    }
}
