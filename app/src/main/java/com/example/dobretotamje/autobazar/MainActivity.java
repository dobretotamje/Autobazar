package com.example.dobretotamje.autobazar;

import android.app.Activity;
import android.os.Bundle;

import com.example.dobretotamje.autobazar.ORM.au_inzerat;
import com.example.dobretotamje.autobazar.ORM.au_inzeratTable;

import java.util.LinkedList;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //au_inzerat au_inzerat = au_inzeratTable.Select_ID(10);
        ////LinkedList<au_inzerat> select_podle_au_id = au_inzeratTable.Select_Au_Id(7);
        /*au_inzerat au_inzerat = new au_inzerat();
        au_inzerat.U_id = 1;
        au_inzerat.Au_id = 2;
        au_inzerat.Popis = "test";
        au_inzerat.Majitel = 1;
        au_inzerat.Vymena_spojky = 20000;
        au_inzerat.Bourane = "A";
        au_inzeratTable.Insert(au_inzerat);*/
        //au_inzeratTable.Delete(17);
        LinkedList<au_inzerat> vsechny = au_inzeratTable.Select();
        int i = 0;
    }
}
