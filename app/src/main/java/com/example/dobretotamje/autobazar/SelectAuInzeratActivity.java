package com.example.dobretotamje.autobazar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.dobretotamje.autobazar.ORM.au_inzerat;
import com.example.dobretotamje.autobazar.ORM.au_inzeratTable;

import java.util.LinkedList;
import java.util.List;

public class SelectAuInzeratActivity extends Activity {

    int autoId = 0;

    Button.OnClickListener btnListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent selectNahradniDilActivity = new Intent(getBaseContext(), SelectNahradniDilActivity.class);
            selectNahradniDilActivity.putExtra("autoId", String.valueOf(autoId));
            startActivity(selectNahradniDilActivity);
        }
    };

    ListView.OnItemClickListener detailListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            au_inzerat inzerat = (au_inzerat) adapterView.getItemAtPosition(i);
            showPopupDetail(view, inzerat.Popis);
        }
    };

    public static void dimBehind(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.5f;
        wm.updateViewLayout(container, p);
    }

    private void showPopupDetail(View view, String detail) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_inzerat2, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        dimBehind(popupWindow);
        popupView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        TextView txtDetail = popupWindow.getContentView().findViewById(R.id.txtDetail);
        txtDetail.setText(detail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_au_inzerat);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ListView lv = findViewById(R.id.lstviewAuInzeraty);
            LinkedList<au_inzerat> au_inzerats = new LinkedList<>();
            Button btn = findViewById(R.id.btn_nahradniDily);
            if (bundle.get("cenaOd") == null || bundle.get("cenaDo") == null) {
                btn.setVisibility(View.VISIBLE);
                autoId = Integer.parseInt((String) bundle.get("autoId"));
                au_inzerats = au_inzeratTable.Select_Au_Id(autoId);
            } else {
                btn.setVisibility(View.GONE);

                int typFiltrOperace = (int) bundle.get("typFiltrOperace");
                String cenaOd = (String) bundle.get("cenaOd");
                String cenaDo = (String) bundle.get("cenaDo");
                if (typFiltrOperace == 0) {
                    au_inzerats = au_inzeratTable.Select_Cena(Integer.parseInt(cenaOd), Integer.parseInt(cenaDo), "cena");
                } else if (typFiltrOperace == 1) {
                    au_inzerats = au_inzeratTable.Select_Cena(Integer.parseInt(cenaOd), Integer.parseInt(cenaDo), "stav_kilometru");
                } else if (typFiltrOperace == 2) {
                    au_inzerats = au_inzeratTable.Select_Cena(Integer.parseInt(cenaOd), Integer.parseInt(cenaDo), "majitel");
                } else if (typFiltrOperace == 3) {
                    au_inzerats = au_inzeratTable.Select_Cena(Integer.parseInt(cenaOd), Integer.parseInt(cenaDo), "rok_vyroby");
                }
            }
            InzeratAdapter mAdapter = new InzeratAdapter(this.getApplicationContext(), R.layout.listview_au_inzerat, au_inzerats);
            btn.setOnClickListener(btnListener);
            lv.setAdapter(mAdapter);
            lv.setOnItemClickListener(detailListener);
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
