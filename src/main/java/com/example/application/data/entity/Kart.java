package com.example.application.data.entity;

public class Kart {
	private Integer id;
    private String number;
    private String name;
    private SpeedType speedType;
    
	public Kart() {
    	//this.id = id;
    	this.number = "";
        this.name = "";
        this.speedType = SpeedType.NO_INFO;
    }
    
    public Kart(String number) {
    	//this.id = id;
    	this.number = number;
        this.name = "";
        this.speedType = SpeedType.NO_INFO;
    }
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

    public SpeedType getSpeedType() {
        return speedType;
    }

    public void setSpeedType(SpeedType speedType) {
        this.speedType = speedType;
    }
    
    public enum SpeedType {
        FAST,
        SLOW,
        NO_INFO;
        
        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}