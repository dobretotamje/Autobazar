package com.example.dobretotamje.autobazar;

import com.example.dobretotamje.autobazar.ORM.au_inzerat;
import com.example.dobretotamje.autobazar.ORM.au_inzeratTable;
import com.example.dobretotamje.autobazar.ORM.auto;
import com.example.dobretotamje.autobazar.ORM.autoTable;
import com.example.dobretotamje.autobazar.ORM.nahradni_dil;
import com.example.dobretotamje.autobazar.ORM.nahradni_dilTable;
import com.example.dobretotamje.autobazar.ORM.nd_inzerat;
import com.example.dobretotamje.autobazar.ORM.nd_inzeratTable;
import com.example.dobretotamje.autobazar.ORM.znacka;
import com.example.dobretotamje.autobazar.ORM.znackaTable;

import java.util.LinkedList;

public class DataSupplier {
    public static LinkedList<znacka> getAllZnacka(){
        return znackaTable.Select();
    }

    public static LinkedList<auto> getFilteredAuta(int znackaId){
        return autoTable.Select_Zn_Id(znackaId);
    }


    public static LinkedList<au_inzerat> getAutoFilteredInzeraty(int autoId){
        return au_inzeratTable.Select_Au_Id(autoId);
    }

    public static LinkedList<nahradni_dil> getAutoFilteredNahradniDily(int autoId){
        return nahradni_dilTable.Select_Au_Id(autoId);
    }

    public static LinkedList<nd_inzerat> getDilFilteredInzeraty(int ndId){
        return nd_inzeratTable.Select_Nd_Id(ndId);
    }

    public static void insertAutoInzerat(au_inzerat inzerat){
        au_inzeratTable.Insert(inzerat);
    }
}
