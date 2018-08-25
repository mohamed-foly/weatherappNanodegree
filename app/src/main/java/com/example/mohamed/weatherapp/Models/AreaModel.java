package com.example.mohamed.weatherapp.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.mohamed.weatherapp.DateConverter;

import java.io.Serializable;
import java.util.Date;
@Entity
public class AreaModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private double lat,lng;
    private String name;



    private String shortDesc;
    private String longDesc ;
    private int temperature,humidity,pressure,speed,degrees,cloud ;
    private boolean rainy ;


    @TypeConverters(DateConverter.class)
    private Date lastUpdate;

    public AreaModel(String name,double lat ,double lng, Date lastUpdate) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.lastUpdate = lastUpdate;

    }

    @Ignore
    public AreaModel(String name,double lat ,double lng, String shortDesc ,String longDesc ,int temperature ,int humidity ,int pressure ,int speed ,int degrees ,int cloud ,boolean rainy,Date lastUpdate) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.speed = speed;
        this.degrees = degrees;
        this.cloud = cloud;
        this.rainy = rainy;
        this.lastUpdate = lastUpdate;
    }

    public String getName() {
        return name;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDegrees() {
        return degrees;
    }

    public int getCloud() {
        return cloud;
    }

    public boolean isRainy() {
        return rainy;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }

    public void setCloud(int cloud) {
        this.cloud = cloud;
    }

    public void setRainy(boolean rainy) {
        this.rainy = rainy;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}