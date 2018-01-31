package com.example.cdsm.tp011;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameActivity extends AppCompatActivity{

    HashMap<String, String> mmap;
    private List<String> values;
    private List<String> names;
    private String winName;
    private String winValue;
    private ListView liste;
    private Button confirm;
    private Button restart;
    private String selName;
    private TextView definition;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initGameActivity();

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // find a way to decolor old selected value
                liste.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.colorSelection));
                //liste.setSelection(i);
                selName = names.get(i);
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.shuffle(names);
                trimAndShuffle();
                restart.setEnabled(false);
                liste.setEnabled(true);
                confirm.setEnabled(true);
                ((TextView)findViewById(R.id.tvGameDefinition)).setText(winValue);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selName != null) {
                    handleGameResult();
                }
            }
        });
    }

    private void handleGameResult() {
        if (selName.equals(winName)) {
            names.remove(selName);
            listAdapter.notifyDataSetChanged();
            liste.setEnabled(false);
            confirm.setEnabled(false);
            restart.setEnabled(true);
            Toast.makeText(GameActivity.this, " You Win ! Congrats ! ", Toast.LENGTH_LONG).show();

        } else {
            names.remove(selName);
            listAdapter.notifyDataSetChanged();
            Toast.makeText(GameActivity.this, "Wrong answer ! Try again ! ", Toast.LENGTH_LONG).show();
        }
    }

    /*private void updateListView() {
        liste.setAdapter(null);

        ArrayAdapter<String> aa = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, names);
        liste.setAdapter(aa);
    }*/

    private void initGameActivity() {
        values = new ArrayList<>();
        names = new ArrayList<>();
        liste = ((ListView)findViewById(R.id.lvGameNames));
        confirm = ((Button)findViewById(R.id.btnConfirm));
        restart = ((Button)findViewById(R.id.btnRestart));

        Intent intent =  this.getIntent();
        mmap = (HashMap<String, String>)intent.getSerializableExtra("map");

        trimAndShuffle();


        restart.setEnabled(false);
        ((TextView)findViewById(R.id.tvGameDefinition)).setText(winValue);
    }

    private void trimAndShuffle() {
       values.clear();
       names.clear();
        ArrayList<Map.Entry<String, String>> list = new ArrayList<>(mmap.entrySet());

        Collections.shuffle(list);
        System.out.println("FIRST SHUFFLE");
        for (int i = 0; i <= 5; i++) {
            list.remove(i);
        }

        for (Map.Entry<String, String> e : list) {
            values.add(e.getValue());
            names.add(e.getKey());
        }

        Map.Entry<String, String> winEntry = list.get(0);
        winName = winEntry.getKey();
        winValue = winEntry.getValue();


        Collections.shuffle(names);
        System.out.println("SECOND SHUFFLE");

        listAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, names);
        liste.setAdapter(listAdapter);


    }
}
