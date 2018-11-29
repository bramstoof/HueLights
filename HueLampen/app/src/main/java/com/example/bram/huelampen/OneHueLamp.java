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

public class OneHueLamp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_one_lamp);Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            final Hue hue = (Hue) extras.getSerializable("hue");
            final Koppeling koppeling = (Koppeling) extras.getSerializable("koppel");
            final VolleyRequest volleyRequest = new VolleyRequest(koppeling, this);
            final TextView lampIdValue = findViewById(R.id.lamp_ID_Value_TextView);
            final Button lampStatusColor = findViewById(R.id.lamp_lampStatusColor);
            final SeekBar hueColorSeekBar = findViewById(R.id.lamp_HueColor_seekBar);
            final TextView hueColorValue = findViewById(R.id.lamp_hueColorValue_TextView);
            final SeekBar saturationSeekBar = findViewById(R.id.lamp_saturation_seekBar);
            final TextView saturationValue = findViewById(R.id.lamp_saturationValue_TextView);
            final SeekBar brightnessSeekBar = findViewById(R.id.lamp_brightness_seekBar);
            final TextView brightnessValue = findViewById(R.id.lamp_brightnessValue_TextView);
            final Button changeColorButton = findViewById(R.id.lamp_changeColor_button);
            final ImageView currentColorImage = findViewById(R.id.lamp_currentColor_Image);
            final Button backButton = findViewById(R.id.lamp_back_button);
            final TextView statusOnOff = findViewById(R.id.lamp_Status_ON_OF);
            final Switch lampSwitch = findViewById(R.id.lamp_switch);


            currentColorImage.setColorFilter(hue.getHueColor());

            hueColorValue.setText(Integer.toString(hue.getHueColorNumber()));
            brightnessValue.setText(Integer.toString(hue.getHueBrightness()));
            saturationValue.setText(Integer.toString(hue.getHueSaturation()));



            hueColorSeekBar.setMax(65535);
            hueColorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    hueColorValue.setText(Integer.toString(seekBar.getProgress()));
                    currentColorImage.setColorFilter(hue.getHueColor(Float.valueOf(hueColorValue.getText().toString()),
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
                    currentColorImage.setColorFilter(hue.getHueColor(Float.valueOf(hueColorValue.getText().toString()),
                            Float.valueOf(saturationValue.getText().toString()), Float.valueOf(brightnessValue.getText().toString())));
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
                    currentColorImage.setColorFilter(hue.getHueColor(Float.valueOf(hueColorValue.getText().toString()),
                            Float.valueOf(saturationValue.getText().toString()),Float.valueOf(brightnessValue.getText().toString())));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            hueColorSeekBar.setProgress(hue.getHueColorNumber());
            brightnessSeekBar.setProgress(hue.getHueBrightness());
            saturationSeekBar.setProgress(hue.getHueSaturation());

            if(hue.isHueIsOn()){
                statusOnOff.setText("ON");
                lampSwitch.setChecked(true);
                lampStatusColor.setBackgroundColor(Color.GREEN);
            }
            else {
                statusOnOff.setText("OFF");
                lampSwitch.setChecked(false);
                lampStatusColor.setBackgroundColor(Color.RED);
            }
            lampIdValue.setText(Integer.toString(hue.getId()));

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = getBaseContext();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });

            changeColorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    volleyRequest.changeColler(hue,Integer.valueOf(brightnessValue.getText().toString()),
                            Integer.valueOf(saturationValue.getText().toString()),Integer.valueOf(hueColorValue.getText().toString()));

                }
            });


            lampSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(hue.isHueIsOn()){
                        volleyRequest.turnLightOff(hue);
                        hue.setHueIsOn(false);
                        statusOnOff.setText("OFF");
                        lampStatusColor.setBackgroundColor(Color.RED);

                    }else{
                        volleyRequest.turnLightOn(hue);
                        hue.setHueIsOn(true);
                        statusOnOff.setText("ON");
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
