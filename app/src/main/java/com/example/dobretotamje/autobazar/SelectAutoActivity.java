package com.example.dobretotamje.autobazar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.dobretotamje.autobazar.ORM.autoTable;

public class SelectAutoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_auto);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            String znId = (String)bundle.get("znackaId");
            autoTable.Select_Zn_Id(Integer.parseInt(znId));
        }
    }
}
