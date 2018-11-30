package com.example.bram.huelampen;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.Locale;

public class Settings extends AppCompatActivity implements FragmentIP.FragmentListener {

    SettingsInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final RadioButton nl = findViewById(R.id.set_nl);
        final RadioButton en = findViewById(R.id.set_en);
        final RadioButton de = findViewById(R.id.set_de);
        final RadioButton fr = findViewById(R.id.set_fr);
        RadioButton bramHome = findViewById(R.id.set_BramHome);
        RadioButton school = findViewById(R.id.set_school);
        final RadioButton simulation = findViewById(R.id.set_simulation);



        Button save = findViewById(R.id.set_save);
        final Button setIP = findViewById(R.id.set_New_Location);

        info = new SettingsInfo(getBaseContext());

        String ip = info.loadLocationIP();

        if(ip == "192.168.0.103")
            bramHome.setChecked(true);
        else if(ip == "145.48.205.33")
            school.setChecked(true);
        else
            simulation.setChecked(true);


        simulation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    setIP.setClickable(true);
                }
            }
        });

        bramHome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    info.saveLocationIP("192.168.0.103");
                    info.saveLocationName("nZdLAHVHpjJZDa3X4dpxFhZDncgsC-MPJf8TtJGu");
                    setIP.setClickable(false);
                }
            }
        });

        school.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    info.saveLocationIP("145.48.205.33");
                    info.saveLocationName("iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB");
                    setIP.setClickable(false);
                }
            }
        });

        String langwitch = info.loadLangwitch();

        switch (langwitch) {
            case "en":
                en.setChecked(true);
                break;
            case "nl":
                nl.setChecked(true);
                break;
            case "de":
                de.setChecked(true);
                break;
            case "fr":
                fr.setChecked(true);
                break;
        }

        setIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentIP fragmentIP = new FragmentIP();
                fragmentIP.show(getSupportFragmentManager(),"getIp");
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(en.isChecked()) {
                    chageLanguage("en");
                }
                else if(nl.isChecked()) {
                    chageLanguage("nl");
                }
                else if(de.isChecked()) {
                    chageLanguage("de");
                }
                else if(fr.isChecked()) {
                    chageLanguage("fr");
                }
                recreate();
                Context context = getBaseContext();
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    private void chageLanguage(String lang){
        info.saveLangwitch(lang);
        Locale loc = new Locale(lang);
        Locale.setDefault(new Locale(lang));
        Configuration config = new Configuration();
        config.locale = loc;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void applyIP(String ip, String Port) {
        final VolleyRequest volleyRequest = new VolleyRequest(getBaseContext(),ip +":"+Port);
    }
}
