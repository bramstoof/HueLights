package com.example.bram.huelampen;

import java.io.Serializable;

public class Koppeling implements Serializable {
    private String ip;
    private String username;

    public Koppeling(String ip, String username) {
        this.ip = ip;
        this.username = username;
        System.out.println(username);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
