package com.example.dobretotamje.autobazar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dobretotamje.autobazar.ORM.nd_inzerat;
import com.example.dobretotamje.autobazar.ORM.nd_inzeratTable;

import java.util.LinkedList;
import java.util.List;

public class SelectNdInzeratActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_nd_inzerat);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String ndId = (String) bundle.get("nahradniDilId");
            LinkedList<nd_inzerat> nd_inzerats = nd_inzeratTable.Select_Nd_Id(Integer.parseInt(ndId));

            InzeratAdapter mAdapter = new InzeratAdapter(this.getApplicationContext(), R.layout.listview_nd_inzerat, nd_inzerats);
            ListView lv = findViewById(R.id.lstviewNdInzeraty);
            lv.setAdapter(mAdapter);
        }
    }

    private class InzeratAdapter extends ArrayAdapter<nd_inzerat> {

        Context context;
        int layoutResourceId;
        List<nd_inzerat> data;

        private InzeratAdapter(Context context, int layoutResourceId, List<nd_inzerat> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            InzeratHolder inzerat;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layoutResourceId, parent, false);

                inzerat = new InzeratHolder();
                inzerat.txtPopis = row.findViewById(R.id.ndInz_txtPopis);
                inzerat.txtCena = row.findViewById(R.id.ndInz_txtCena);
                inzerat.txtMisto = row.findViewById(R.id.ndInz_txtMisto);
                inzerat.txtUId = row.findViewById(R.id.ndInz_txtUId);
                inzerat.txtOpotrebeni = row.findViewById(R.id.ndInz_txtOpotrebeni);

                row.setTag(inzerat);
            } else {
                inzerat = (InzeratHolder) row.getTag();
            }

            nd_inzerat inz = data.get(position);
            inzerat.txtPopis.setText(inz.Popis);
            inzerat.txtCena.setText("Cena: " + String.valueOf(inz.Cena));
            inzerat.txtMisto.setText("Místo: " + inz.Misto);
            inzerat.txtOpotrebeni.setText("Opotřebení: " + String.valueOf(inz.Opotrebeni));
            inzerat.txtUId.setText(String.valueOf(inz.U_id));

            return row;
        }

        private class InzeratHolder {
            TextView txtPopis;
            TextView txtCena;
            TextView txtMisto;
            TextView txtOpotrebeni;
            TextView txtUId;
        }
    }
}
