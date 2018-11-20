package com.example.dobretotamje.autobazar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        CustomAdapterOptimized mAdapter = new CustomAdapterOptimized(this.getContext)

    }

    private class CustomAdapterOptimized extends ArrayAdapter<znacka> {

        public CustomAdapterOptimized(Context context, int textViewResourceId, List<znacka> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getViewOptimize(position, convertView, parent);
        }

        public View getViewOptimize(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)
                        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.example_item, null);
                viewHolder = new ViewHolder();
                viewHolder.exampleID = convertView.findViewById(R.id.exampleID);
                viewHolder.exampleName = convertView.findViewById(R.id.exampleName);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            znacka example = getItem(position);
            viewHolder.exampleID.setText(example.ID);
            viewHolder.exampleName.setText(example.name);
            return convertView;
        }

        private class ViewHolder {
            public TextView exampleID;
            public TextView exampleName;
        }
    }
}
