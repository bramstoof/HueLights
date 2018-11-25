package com.example.bram.huelampen;

import android.graphics.Color;

import java.io.Serializable;

public class Hue implements Serializable {
    private int id;
    private boolean on;
    private int bri;
    private int hue;
    private int sat;

    public Hue(int id, boolean on, int bri, int hue, int sat) {
        this.id = id;
        this.on = on;
        this.bri = bri;
        this.hue = hue;
        this.sat = sat;
    }

    public int getColor(){
        float[] hsv = new float[3];
        hsv[0] = (float)getHue()/182;
        hsv[1] = (float)getSat()/254;
        hsv[2] = (float)getBri()/200;
        return Color.HSVToColor(hsv);
    }
    public int getColor(float hue, float sat, float bri){
        float[] hsv = new float[3];
        hsv[0] = hue/182;
        hsv[1] = sat/254;
        hsv[2] = bri/200;
        return Color.HSVToColor(hsv);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getBri() {
        return bri;
    }

    public void setBri(int bri) {
        this.bri = bri;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }
}
