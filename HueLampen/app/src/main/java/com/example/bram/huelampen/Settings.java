package com.example.bram.huelampen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final RadioButton nl = findViewById(R.id.set_nl);
        final RadioButton en = findViewById(R.id.set_en);
        final RadioButton de = findViewById(R.id.set_de);
        final RadioButton fr = findViewById(R.id.set_fr);

        Button save = findViewById(R.id.set_save);

        final SettingsInfo info = new SettingsInfo(getBaseContext());

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
                    info.saveLangwitch("en");
                }
                else if(nl.isChecked()) {
                    info.saveLangwitch("nl");
                }
                else if(de.isChecked()) {
                    info.saveLangwitch("de");
                }
                else if(fr.isChecked()) {
                    info.saveLangwitch("fr");
                }

                Context context = getBaseContext();
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
            }
        });
    }
}
