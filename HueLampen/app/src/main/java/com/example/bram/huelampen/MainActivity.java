package com.example.bram.huelampen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Koppeling koppeling;
    private VolleyRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sinc = findViewById(R.id.main_sinc);
        Button changeAllLamps = findViewById(R.id.main_all);

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-
        //Als je de lampen wilt aansturen:

        //eerst hier een request runnen, met het IP en Poortnummer wat je gebruikt
        //request = new VolleyRequest(this,"192.168.0.101:81", (RecyclerView) findViewById(R.id.RecyclerView_MainScreen));

        //Daarna request commenten en deze koppeling runnen met de verkregen Username
        koppeling = new Koppeling("192.168.0.101:81","137fc4339bbe389ecef320290f783e5");
        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-


        //Deze dingen hieronder allemaal commenten


       // koppeling = new Koppeling("145.49.8.37:81","nZdLAHVHpjJZDa3X4dpxFhZDncgsC-MPJf8TtJGu");
        //koppeling = new Koppeling("192.168.0.101:81","8bc335a578e0e03325016a5432817f9"); //Thuis Timo
        //koppeling = new Koppeling("192.168.0.103", "nZdLAHVHpjJZDa3X4dpxFhZDncgsC-MPJf8TtJGu" );

        //request = new VolleyRequest(this,"192.168.0.101:81", (RecyclerView) findViewById(R.id.RecyclerView_MainScreen));
        request = new VolleyRequest(koppeling, this, (RecyclerView) findViewById(R.id.RecyclerView_MainScreen));
        request.getLampsRequest();
        //request = new VolleyRequest(koppeling,this);
        sinc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.getLampsRequest();
            }
        });
        changeAllLamps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getBaseContext();
                Intent intent = new Intent(context, AllHueLamps.class);
                intent.putExtra("lamps", request.getHueList());
                intent.putExtra("koppel", koppeling);
                context.startActivity(intent);
            }
        });

        //request = new VolleyRequest(koppeling,this,(RecyclerView) findViewById(R.id.RecyclerView_MainScreen));     //local


    }
}
