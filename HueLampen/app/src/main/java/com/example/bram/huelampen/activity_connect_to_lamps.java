package com.example.bram.huelampen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class activity_connect_to_lamps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_lamps);

        final EditText ip = findViewById(R.id.connect_IP_textField);
        final EditText port = findViewById(R.id.connect_poortNummer_TextField);
        Button request = findViewById(R.id.connect_button);
        Button back = findViewById(R.id.conection_back);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipaddress = ip.getText().toString() +":"+ port.getText().toString();

                final VolleyRequest volleyRequest = new VolleyRequest(getBaseContext(),ipaddress);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getBaseContext();
                Intent intent = new Intent(context,Settings.class);
                context.startActivity(intent);
            }
        });
    }
}
