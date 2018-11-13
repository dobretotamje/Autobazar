package com.example.dobretotamje.autobazar;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.example.dobretotamje.autobazar.ORM.au_inzerat;
import com.example.dobretotamje.autobazar.ORM.au_inzeratTable;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        au_inzerat au_inzerat = au_inzeratTable.Select_ID(10);
        int i = 0;
    }
}
