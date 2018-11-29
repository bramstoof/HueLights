package com.example.bram.huelampen;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.Locale;

public class Settings extends AppCompatActivity {

    SettingsInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final RadioButton nl = findViewById(R.id.set_nl);
        final RadioButton en = findViewById(R.id.set_en);
        final RadioButton de = findViewById(R.id.set_de);
        final RadioButton fr = findViewById(R.id.set_fr);

        Button save = findViewById(R.id.set_save);

        info = new SettingsInfo(getBaseContext());

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
}
