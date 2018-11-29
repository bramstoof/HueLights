package com.example.bram.huelampen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class VolleyRequest implements  HueAdapter.onItemClickListener{

    private Koppeling koppeling;
    private Context context;
    private RecyclerView recyclerView;
    String username;
    ArrayList<Hue> hueList;

    public VolleyRequest(Koppeling koppeling, Context context, RecyclerView recyclerView) {
        this.koppeling = koppeling;
        this.context = context;
        this.recyclerView = recyclerView;


    }

    public VolleyRequest(Koppeling koppeling, Context context) {
        this.koppeling = koppeling;
        this.context = context;
    }

    public VolleyRequest(Context context, String ip, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        getUsername(ip);
    }

    private void doRequest(final String requestBody, String url)
    {
        //final String requestBody  = "{\"on\":true}";
        final RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("My response:", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error: ", error.toString());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        queue.add(stringRequest);
    }
    //private String stringURL = "http://145.49.8.37:81/api/f11c60ae4609c576b8b17fefb16903e/lights/1/state";
    public void turnLightOn(Hue hue)
    {
        doRequest("{\"on\":true}","http://"+ koppeling.getIp() +"/api/"+ koppeling.getUsername() +
                "/lights/" + hue.getId()+ "/state");
        hue.setHueIsOn(true);
    }

    public void turnLightOff(Hue hue)
    {
        doRequest("{\"on\":false}","http://"+ koppeling.getIp() +"/api/"+ koppeling.getUsername() +
                "/lights/" + hue.getId()+ "/state");
        hue.setHueIsOn(false);
    }

    public void changeColler(Hue hueLight, int bri, int sat, int hue)
    {
        String requestbody = "{\"bri\":"+ bri + ",\"sat\":"+ sat +",\"hue\":"+hue+"}";
        doRequest(requestbody,"http://"+ koppeling.getIp() +"/api/"+ koppeling.getUsername() +
                "/lights/" + hueLight.getId()+ "/state");

        hueLight.setHueBrightness(bri);
        hueLight.setHueColorNumber(hue);
        hueLight.setHueSaturation(sat);
    }










    private void getUsername(final String ip)
    {
        final String requestBody ="{\"devicetype\":\"MijnApp#BramTimo\"}";
        String url = "http://"+ip+"/api";

        final RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("My response:", response.toString());
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject object = (JSONObject) array.get(0);
                    username = object.getJSONObject("success").getString("username");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                koppeling = new Koppeling(ip,username);
                getLampsRequest();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error: ", error.toString());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        queue.add(stringRequest);
    }



    public void getLampsRequest()
    {
        hueList = new ArrayList<>();
        String url = "http://"+ koppeling.getIp()+"/api/"+koppeling.getUsername();
        final RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject list = new JSONObject(response).getJSONObject("lights");
                    for (int i = 0; i< list.length()+1;i++)
                    {
                        String id = Integer.toString(i);
                        if(list.has(id)){
                            JSONObject hueLampObject = list.getJSONObject(id).getJSONObject("state");
                            int bri = hueLampObject.getInt("bri");
                            boolean on = hueLampObject.getBoolean("on");
                            int hue = hueLampObject.getInt("hue");
                            int sat = hueLampObject.getInt("sat");
                            Hue hueLamp = new Hue(i,on,bri,hue,sat);
                            System.out.println(hueLamp);
                            hueList.add(hueLamp);
                        }


                    }
                    HueAdapter adapter = new HueAdapter(hueList,context);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));

                    adapter.setOnItemClickListener(VolleyRequest.this);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error: ", error.toString());
            }
        });
        queue.add(stringRequest);
    }

    public ArrayList<Hue> getHueList(){
        return hueList;
    }
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(context, OneHueLamp.class);
        intent.putExtra("hue", hueList.get(position));
        intent.putExtra("koppel", koppeling);
        context.startActivity(intent);
    }
}
