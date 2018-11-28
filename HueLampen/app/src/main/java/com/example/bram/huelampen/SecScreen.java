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
import android.widget.TextView;

public class SecScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec_screen);Bundle extras = getIntent().getExtras();
        if(extras != null) {
            final Hue hue = (Hue) extras.getSerializable("hue");
            Koppeling koppeling = (Koppeling) extras.getSerializable("koppel");
            final VolleyRequest request = new VolleyRequest(koppeling, this);




            TextView id = findViewById(R.id.sec_id);
            final Button power = findViewById(R.id.sec_bPower);
            final SeekBar hueBar = findViewById(R.id.sec_bar_hue);
            final TextView hueValue = findViewById(R.id.sec_hue_value);
            final SeekBar satBar = findViewById(R.id.sec_bar_sat);
            final TextView satValue = findViewById(R.id.sec_sat_value);
            final SeekBar briBar = findViewById(R.id.sec_bar_bri);
            final TextView briValue = findViewById(R.id.sec_bri_value);
            final Button change = findViewById(R.id.sec_button_change);
            final ImageView ColorImageButton = findViewById(R.id.ColorImage);
            Button back = findViewById(R.id.all_back);



            change.setBackgroundColor(hue.getColor());

            hueValue.setText(Integer.toString(hue.getHue()));
            briValue.setText(Integer.toString(hue.getBri()));
            satValue.setText(Integer.toString(hue.getSat()));



            hueBar.setMax(65535);
            hueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    hueValue.setText(Integer.toString(seekBar.getProgress()));
                    change.setBackgroundColor(hue.getColor(Float.valueOf(hueValue.getText().toString()),
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
                    change.setBackgroundColor(hue.getColor(Float.valueOf(hueValue.getText().toString()),
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
                    change.setBackgroundColor(hue.getColor(Float.valueOf(hueValue.getText().toString()),
                            Float.valueOf(satValue.getText().toString()),Float.valueOf(briValue.getText().toString())));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            hueBar.setProgress(hue.getHue());
            briBar.setProgress(hue.getBri());
            satBar.setProgress(hue.getSat());

            if(hue.isOn()){
                power.setText("ON");
                power.setBackgroundColor(Color.GREEN);
            }
            else {
                power.setText("OFF");
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
            power.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(hue.isOn()){
                        request.turnLightOff(hue);
                        hue.setOn(false);
                        power.setText("OFF");
                        power.setBackgroundColor(Color.RED);

                    }else{
                        request.turnLightOn(hue);
                        hue.setOn(true);
                        power.setText("ON");
                        power.setBackgroundColor(Color.GREEN);
                    }
                }
            });

        }

    }
}
