package com.example.bram.huelampen;

import android.graphics.Color;

import java.io.Serializable;

public class Hue implements Serializable {
    private int hueID;
    private String name;
    private String type;
    private boolean hueIsOn;
    private int hueBrightness;
    private int hueColorNumber;
    private int hueSaturation;

    public Hue(int id, String name,String type, boolean hueIsOn, int hueBrightness, int hueColorNumber, int hueSaturation) {
        this.hueID = id;
        this.name = name;
        this.type = type;
        this.hueIsOn = hueIsOn;
        this.hueBrightness = hueBrightness;
        this.hueColorNumber = hueColorNumber;
        this.hueSaturation = hueSaturation;
    }

    public int getHueColor(){
        float[] hsv = new float[3];
        hsv[0] = (float) getHueColorNumber()/182;
        hsv[1] = (float) getHueSaturation()/254;
        hsv[2] = (float) getHueBrightness()/200;
        return Color.HSVToColor(hsv);
    }
    public int getHueColor(float hue, float sat, float bri){
        float[] hsv = new float[3];
        hsv[0] = hue/182;
        hsv[1] = sat/254;
        hsv[2] = bri/200;
        return Color.HSVToColor(hsv);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return hueID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.hueID = id;
    }

    public boolean isHueIsOn() {
        return hueIsOn;
    }

    public void setHueIsOn(boolean hueIsOn) {
        this.hueIsOn = hueIsOn;
    }

    public int getHueBrightness() {
        return hueBrightness;
    }

    public void setHueBrightness(int hueBrightness) {
        this.hueBrightness = hueBrightness;
    }

    public int getHueColorNumber() {
        return hueColorNumber;
    }

    public void setHueColorNumber(int hueColorNumber) {
        this.hueColorNumber = hueColorNumber;
    }

    public int getHueSaturation() {
        return hueSaturation;
    }

    public void setHueSaturation(int hueSaturation) {
        this.hueSaturation = hueSaturation;
    }
}
