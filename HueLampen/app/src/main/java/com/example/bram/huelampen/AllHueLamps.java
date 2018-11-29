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

        if(extras != null)
        {
            final ArrayList<Hue> hueLampList = (ArrayList<Hue>) extras.getSerializable("lamps");
            final Koppeling koppeling = (Koppeling) extras.getSerializable("koppel");
            final VolleyRequest volleyRequest = new VolleyRequest(koppeling, this);
            final Button lampStatusColor = findViewById(R.id.allLamps_lampStatusColor);
            final SeekBar hueLampColorSeekBar = findViewById(R.id.allLamps_seekBar_hueLampColor);
            final TextView hueColorValue = findViewById(R.id.allLamps_hueColorValue_TextView);
            final SeekBar saturationSeekBar = findViewById(R.id.allLamps_seekBar_saturation);
            final TextView saturationValue = findViewById(R.id.allLamps_saturationValue_TextView);
            final SeekBar brightnessSeekBar = findViewById(R.id.allLamps_seekBar_brightness);
            final TextView brightnessValue = findViewById(R.id.allLamps_brightnessValue_TextView);
            final Button changeLampButton = findViewById(R.id.allLamps_ChangeLamp_Button);
            final ImageView currentColorImage = findViewById(R.id.allLamps_currentColor_Image);
            final Button backButton = findViewById(R.id.allLamp_back_button);
            final TextView lampStatusOnOff = findViewById(R.id.allLamps_lampStatusOnOff_TextView);
            final Switch lampStatusSwitch = findViewById(R.id.allLamps_lampStatus_switch);

            currentColorImage.setColorFilter(Color.RED);
            hueColorValue.setText("65535");
            brightnessValue.setText("254");
            saturationValue.setText("254");

            hueLampColorSeekBar.setMax(65535);
            hueLampColorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    hueColorValue.setText(Integer.toString(seekBar.getProgress()));
                    currentColorImage.setColorFilter(hueLampList.get(1).getHueColor(Float.valueOf(hueColorValue.getText().toString()),
                            Float.valueOf(saturationValue.getText().toString()),Float.valueOf(brightnessValue.getText().toString())));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            brightnessSeekBar.setMax(254);
            brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    brightnessValue.setText(Integer.toString(brightnessSeekBar.getProgress()));
                    currentColorImage.setColorFilter(hueLampList.get(1).getHueColor(Float.valueOf(hueColorValue.getText().toString()),
                            Float.valueOf(saturationValue.getText().toString()),Float.valueOf(brightnessValue.getText().toString())));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            saturationSeekBar.setMax(254);
            saturationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    saturationValue.setText(Integer.toString(saturationSeekBar.getProgress()));
                    currentColorImage.setColorFilter(hueLampList.get(1).getHueColor(Float.valueOf(hueColorValue.getText().toString()),
                            Float.valueOf(saturationValue.getText().toString()),Float.valueOf(brightnessValue.getText().toString())));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            hueLampColorSeekBar.setProgress(65535);
            brightnessSeekBar.setProgress(254);
            saturationSeekBar.setProgress(254);

            if(hueLampList.get(0).isHueIsOn())
                {
                    lampStatusOnOff.setText("ON");
                    lampStatusColor.setBackgroundColor(Color.GREEN);
                }
            else
                {
                    lampStatusOnOff.setText("OFF");
                    lampStatusColor.setBackgroundColor(Color.RED);
                }

            backButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Context context = getBaseContext();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });

            changeLampButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    for(Hue hue : hueLampList)
                    volleyRequest.changeColler(hue,Integer.valueOf(brightnessValue.getText().toString()),
                            Integer.valueOf(saturationValue.getText().toString()),Integer.valueOf(hueColorValue.getText().toString()));

                }
            });
            lampStatusSwitch.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(lampStatusOnOff.getText()== "ON")
                    {
                        for(Hue hue : hueLampList)
                        {
                            volleyRequest.turnLightOff(hue);
                            hue.setHueIsOn(false);
                        }
                        lampStatusOnOff.setText("OFF");
                        lampStatusColor.setBackgroundColor(Color.RED);
                    }
                    else
                        {
                        for (Hue hue : hueLampList)
                        {
                            volleyRequest.turnLightOn(hue);
                            hue.setHueIsOn(true);
                        }
                        lampStatusOnOff.setText("ON");
                        lampStatusColor.setBackgroundColor(Color.GREEN);
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

