package com.example.bram.huelampen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private Koppeling koppeling;
    private VolleyRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sinc = findViewById(R.id.main_sinc);
        Button all = findViewById(R.id.main_all);

       // koppeling = new Koppeling("145.49.8.37:81","nZdLAHVHpjJZDa3X4dpxFhZDncgsC-MPJf8TtJGu");
        koppeling = new Koppeling("192.168.0.103", "nZdLAHVHpjJZDa3X4dpxFhZDncgsC-MPJf8TtJGu" );
        request = new VolleyRequest(koppeling, this, (RecyclerView) findViewById(R.id.RecyclerView_MainScreen));
        request.getLampsRequest();
        //request = new VolleyRequest(koppeling,this);
        sinc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.getLampsRequest();
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getBaseContext();
                Intent intent = new Intent(context, Lamps.class);
                intent.putExtra("lamps", request.getHueList());
                intent.putExtra("koppel", koppeling);
                context.startActivity(intent);
            }
        });

        //request = new VolleyRequest(koppeling,this,(RecyclerView) findViewById(R.id.RecyclerView_MainScreen));     //local


    }
}
