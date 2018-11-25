package com.example.bram.huelampen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Lamps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamps);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            final ArrayList<Hue> huelist = (ArrayList<Hue>) extras.getSerializable("lamps");
            Koppeling koppeling = (Koppeling) extras.getSerializable("koppel");
            final VolleyRequest request = new VolleyRequest(koppeling, this);

            final Button power = findViewById(R.id.all_power);
            final SeekBar hueBar = findViewById(R.id.seekBar_hue);
            final TextView hueValue = findViewById(R.id.all_hue_value);
            final SeekBar satBar = findViewById(R.id.seekBar_sat);
            final TextView satValue = findViewById(R.id.all_sat_value);
            final SeekBar briBar = findViewById(R.id.seekBar_bir);
            final TextView briValue = findViewById(R.id.all_bri_value);
            final Button change = findViewById(R.id.all_change);
            Button back = findViewById(R.id.all_back);



            change.setBackgroundColor(Color.RED);

            hueValue.setText("65535");
            briValue.setText("254");
            satValue.setText("254");



            hueBar.setMax(65535);
            hueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    hueValue.setText(Integer.toString(seekBar.getProgress()));
                    change.setBackgroundColor(huelist.get(1).getColor(Float.valueOf(hueValue.getText().toString()),
                            Float.valueOf(satValue.getText().toString()),Float.valueOf(briValue.getText().toString())));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            briBar.setMax(254);
            briBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    briValue.setText(Integer.toString(briBar.getProgress()));
                    change.setBackgroundColor(huelist.get(1).getColor(Float.valueOf(hueValue.getText().toString()),
                            Float.valueOf(satValue.getText().toString()),Float.valueOf(briValue.getText().toString())));
                    if(briBar.getProgress() < 100)
                        change.setTextColor(Color.WHITE);
                    else
                        change.setTextColor(Color.BLACK);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            satBar.setMax(254);
            satBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    satValue.setText(Integer.toString(satBar.getProgress()));
                    change.setBackgroundColor(huelist.get(1).getColor(Float.valueOf(hueValue.getText().toString()),
                            Float.valueOf(satValue.getText().toString()),Float.valueOf(briValue.getText().toString())));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            hueBar.setProgress(65535);
            briBar.setProgress(254);
            satBar.setProgress(254);

            if(huelist.get(0).isOn()){
                power.setText("ON");
                power.setBackgroundColor(Color.GREEN);
            }
            else {
                power.setText("OFF");
                power.setBackgroundColor(Color.RED);
            }

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = getBaseContext();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });

            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(Hue hue : huelist)
                    request.changeColler(hue,Integer.valueOf(briValue.getText().toString()),
                            Integer.valueOf(satValue.getText().toString()),Integer.valueOf(hueValue.getText().toString()));

                }
            });
            power.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(power.getText()== "ON"){
                        for(Hue hue : huelist){
                            request.turnLightOff(hue);
                            hue.setOn(false);

                        }
                        power.setText("OFF");
                        power.setBackgroundColor(Color.RED);
                    }else {
                        for (Hue hue : huelist) {
                            request.turnLightOn(hue);
                            hue.setOn(true);
                        }
                        power.setText("ON");
                        power.setBackgroundColor(Color.GREEN);
                    }
                }
            });
        }
    }
}

