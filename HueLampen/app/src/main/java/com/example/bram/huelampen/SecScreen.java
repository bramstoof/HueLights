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

public class SecScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_one_lamp);Bundle extras = getIntent().getExtras();
        if(extras != null) {
            final Hue hue = (Hue) extras.getSerializable("hue");
            Koppeling koppeling = (Koppeling) extras.getSerializable("koppel");
            final VolleyRequest request = new VolleyRequest(koppeling, this);




            TextView id = findViewById(R.id.lamp_ID_Value_TextView);
            final Button power = findViewById(R.id.lamp_lampStatusColor);
            final SeekBar hueBar = findViewById(R.id.lamp_HueColor_seekBar);
            final TextView hueValue = findViewById(R.id.lamp_hueColorValue_TextView);
            final SeekBar satBar = findViewById(R.id.lamp_saturation_seekBar);
            final TextView satValue = findViewById(R.id.lamp_saturationValue_TextView);
            final SeekBar briBar = findViewById(R.id.lamp_brightness_seekBar);
            final TextView briValue = findViewById(R.id.lamp_brightnessValue_TextView);
            final Button change = findViewById(R.id.lamp_changeColor_button);
            final ImageView ColorImageButton = findViewById(R.id.lamp_currentColor_Image);
            final Button back = findViewById(R.id.lamp_back_button);
            final TextView lampstatus = findViewById(R.id.lamp_Status_ON_OF);
            final Switch lampStatusSwitch = findViewById(R.id.lamp_switch);



            ColorImageButton.setColorFilter(hue.getHueColor());

            hueValue.setText(Integer.toString(hue.getHueColorNumber()));
            briValue.setText(Integer.toString(hue.getHueBrightness()));
            satValue.setText(Integer.toString(hue.getHueSaturation()));



            hueBar.setMax(65535);
            hueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    hueValue.setText(Integer.toString(seekBar.getProgress()));
                    ColorImageButton.setColorFilter(hue.getHueColor(Float.valueOf(hueValue.getText().toString()),
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
                    ColorImageButton.setColorFilter(hue.getHueColor(Float.valueOf(hueValue.getText().toString()),
                            Float.valueOf(satValue.getText().toString()), Float.valueOf(briValue.getText().toString())));
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
                    ColorImageButton.setColorFilter(hue.getHueColor(Float.valueOf(hueValue.getText().toString()),
                            Float.valueOf(satValue.getText().toString()),Float.valueOf(briValue.getText().toString())));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            hueBar.setProgress(hue.getHueColorNumber());
            briBar.setProgress(hue.getHueBrightness());
            satBar.setProgress(hue.getHueSaturation());

            if(hue.isHueIsOn()){
                lampstatus.setText("ON");
                power.setBackgroundColor(Color.GREEN);
            }
            else {
                lampstatus.setText("OFF");
                power.setBackgroundColor(Color.RED);
            }
            id.setText(Integer.toString(hue.getId()));

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
                    request.changeColler(hue,Integer.valueOf(briValue.getText().toString()),
                            Integer.valueOf(satValue.getText().toString()),Integer.valueOf(hueValue.getText().toString()));

                }
            });
            lampStatusSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(hue.isHueIsOn()){
                        request.turnLightOff(hue);
                        hue.setHueIsOn(false);
                        lampstatus.setText("OFF");
                        power.setBackgroundColor(Color.RED);

                    }else{
                        request.turnLightOn(hue);
                        hue.setHueIsOn(true);
                        lampstatus.setText("ON");
                        power.setBackgroundColor(Color.GREEN);
                    }
                }
            });

        }

    }
}
