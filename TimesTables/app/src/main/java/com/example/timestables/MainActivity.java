package com.example.timestables;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> table;
    ArrayAdapter<String> arrayAdapter;
    SeekBar slider;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        table = new ArrayList<String>();
        list = (ListView)findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,table);
        slider =(SeekBar) findViewById(R.id.seekBar3);
        list.setAdapter(arrayAdapter);
        slider.setProgress(10);
        slider.setMax(20);
        slider.setMin(1);
        if(slider.getProgress()<1){
            slider.setProgress(1);
            listFiller(slider.getProgress());
        }else {
            listFiller(slider.getProgress());
        }
        listFiller(slider.getProgress());
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(progress<1){
                    slider.setProgress(1);
                    listFiller(progress);
                }else {
                    listFiller(progress);
                }
                arrayAdapter.notifyDataSetChanged();


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void listFiller(int number) {
        table.removeAll(table);

        for(int i=0;i<20;i++){
            int z = (i+1) * number;
            String y = String.valueOf(z);
            table.add(y);

        }


    }
}
