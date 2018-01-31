package com.example.cdsm.tp011;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, String> dictMap;
    private File file;

    private TextView tvDef;
    private AutoCompleteTextView etDefName;
    private Button btnShowDef;
    private Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMainActivity();
        makeDictFile();
        makeDictMapFromFile();

        btnShowDef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etDefName.getText().toString().trim().isEmpty()) {
                    searchDefinition(etDefName.getText().toString().trim());
                } else {
                    Toast.makeText(getApplicationContext(), "Un nom de propriete est necessaire !", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  /*               ArrayList<String> namesList = new ArrayList<>();
               ArrayList<String> valuesList = new ArrayList<>();
                ArrayList<Map.Entry<String, String>> list = new ArrayList<>(dictMap.entrySet());

                Collections.shuffle(list);
                for (int i = 0; i <= 5; i++) {
                    list.remove(i);
                }
                Collections.shuffle(list);

                for (Map.Entry<String, String> entry : list) {
                    namesList.add(entry.getKey());
                    valuesList.add(entry.getValue());
                }

                Map.Entry<String, String> winEntry = list.get(0);
*/

                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            //    intent.putExtra("nList", namesList);
              //  intent.putExtra("vList", valuesList);
                intent.putExtra("map", dictMap);
                //intent.putExtra("winName", winEntry.getKey());
                //intent.putExtra("winValue", winEntry.getValue());
                startActivity(intent);
            }
        });

    }


    private void searchDefinition(String inval) {
        boolean found = false;
        for (Map.Entry entry : dictMap.entrySet()) {
            if (inval.equalsIgnoreCase(entry.getKey().toString())) {
                tvDef.setText(dictMap.get(entry.getKey()));
                found = true;
            }
        }

        if (!found) {
            Toast.makeText(getApplicationContext(), "cette propriete n'existe pas !", Toast.LENGTH_LONG).show();
        }
    }

    private void makeDictMapFromFile() {
        try  {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] lineValues = line.split(" ", 2);
                dictMap.put(lineValues[0].trim(), lineValues[1].trim());
            }

            ArrayAdapter<String> aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, new ArrayList(dictMap.keySet()));
            etDefName.setAdapter(aa);

        } catch (FileNotFoundException fnfe) {
            System.err.println("MainActivity -- onCreate() : " + fnfe.getMessage());
        }
    }

    private void initMainActivity() {
        dictMap = new HashMap<>();
        String path = getApplicationContext().getFilesDir().getAbsolutePath() + "/dict.txt";
        file = new File(path);

        etDefName = ((AutoCompleteTextView)findViewById(R.id.etDefinitionName));
        btnShowDef = ((Button)findViewById(R.id.btnShowDefinition));
        tvDef = ((TextView)findViewById(R.id.tvDefinition));
        btnPlay = ((Button)findViewById(R.id.btnPlaygame));
    }


    private void makeDictFile() {
        if (file != null) {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                String entry0 = "Java Langage de programmation pour Android et autres plateformes \n";
                String entry1 = "Android Système d'operation développé par Google \n";
                String entry2 = "Windows Système d'operation développé par Microsoft \n";
                String entry3 = "OSX Système d'operation développé par Apple pour les ordinateurs \n";
                String entry4 = "IOS Système d'operation développé par Apple pour mobiles et tablettes \n";
                String entry5 = "Swift Langage de programmation des systèmes Apple \n";
                String entry6 = "DotNet Langage de programmation utilisé par les systèmes Microsoft \n";
                String entry7 = "Xamarin IDE de Microsoft pour applications multi-plateformes \n";
                String entry8 = "JavaOne Outil de développement d'Oracle pour le développement multi-plateformes \n";
                String entry9 = "IDE 'Integrated Developpement Environnement' en anglais, outils pour simplifier le développement d'applications \n";
                String entry10 = "CMD Interpreteur de commande sur les systèmes Microsoft, equivalent d'un terminal \n";
                fos.write(entry0.getBytes());
                fos.write(entry1.getBytes());
                fos.write(entry2.getBytes());
                fos.write(entry3.getBytes());
                fos.write(entry4.getBytes());
                fos.write(entry5.getBytes());
                fos.write(entry6.getBytes());
                fos.write(entry7.getBytes());
                fos.write(entry8.getBytes());
                fos.write(entry9.getBytes());
                fos.write(entry10.getBytes());

            } catch (FileNotFoundException fnfe) {
                System.err.println("MainActivity -- makeDictFile() : " + fnfe.getMessage());
            } catch (IOException ie) {
                System.err.println("MainActivity -- makeDictFile() : " + ie.getMessage());
            }
        }

    }
}
