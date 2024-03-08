package com.example.application.data.entity;

public class Kart {
    private String number;
    private String name;
    private String status;
    
    public Kart() {
    	this.number = "";
        this.name = "";
        this.status = "no_info";
    }
    
    public Kart(String number) {
        this.number = number;
        this.name = "";
        this.status = "no_info";
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}