package com.example.bram.huelampen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class AllHueLamps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_all_lamps);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            final ArrayList<Hue> huelist = (ArrayList<Hue>) extras.getSerializable("lamps");
            Koppeling koppeling = (Koppeling) extras.getSerializable("koppel");
            final VolleyRequest request = new VolleyRequest(koppeling, this);

            final Button power = findViewById(R.id.allLamps_lampStatusColor);
            final SeekBar hueBar = findViewById(R.id.allLamps_seekBar_hueLampColor);
            final TextView hueValue = findViewById(R.id.allLamps_hueColorValue_TextView);
            final SeekBar satBar = findViewById(R.id.allLamps_seekBar_saturation);
            final TextView satValue = findViewById(R.id.allLamps_saturationValue_TextView);
            final SeekBar briBar = findViewById(R.id.allLamps_seekBar_brightness);
            final TextView briValue = findViewById(R.id.allLamps_brightnessValue_TextView);
            final Button change = findViewById(R.id.allLamps_ChangeLamp_Button);
            final ImageView imageColorChanger = findViewById(R.id.allLamps_currentColor_Image);
            final Button back = findViewById(R.id.allLamp_back_button);
            final TextView lampStatusTextView = findViewById(R.id.allLamps_lampStatusOnOff_TextView);
            final Switch lampStatusSwitch = findViewById(R.id.allLamps_lampStatus_switch);



            imageColorChanger.setColorFilter(Color.RED);

            hueValue.setText("65535");
            briValue.setText("254");
            satValue.setText("254");



            hueBar.setMax(65535);
            hueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    hueValue.setText(Integer.toString(seekBar.getProgress()));
                    imageColorChanger.setColorFilter(huelist.get(1).getHueColor(Float.valueOf(hueValue.getText().toString()),
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
                    imageColorChanger.setColorFilter(huelist.get(1).getHueColor(Float.valueOf(hueValue.getText().toString()),
                            Float.valueOf(satValue.getText().toString()),Float.valueOf(briValue.getText().toString())));
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
                    imageColorChanger.setColorFilter(huelist.get(1).getHueColor(Float.valueOf(hueValue.getText().toString()),
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

            if(huelist.get(0).isHueIsOn()){
                lampStatusTextView.setText("ON");
                power.setBackgroundColor(Color.GREEN);
            }
            else {
                lampStatusTextView.setText("OFF");
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
            lampStatusSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(lampStatusTextView.getText()== "ON"){
                        for(Hue hue : huelist){
                            request.turnLightOff(hue);
                            hue.setHueIsOn(false);

                        }
                        lampStatusTextView.setText("OFF");
                        power.setBackgroundColor(Color.RED);
                    }else {
                        for (Hue hue : huelist) {
                            request.turnLightOn(hue);
                            hue.setHueIsOn(true);
                        }
                        lampStatusTextView.setText("ON");
                        power.setBackgroundColor(Color.GREEN);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed()
    {
        Context context = getBaseContext();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}

