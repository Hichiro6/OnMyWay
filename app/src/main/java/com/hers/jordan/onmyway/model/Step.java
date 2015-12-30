package com.hers.jordan.onmyway.model;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jordan on 23/12/2015.
 */
public class Step {

    private int _id;
    private String name;
    private double longitude;
    private double latitude;
    private long time; // number of secondes
    private String transport;
    private Step nextStep;

    public Step(int _id, String name, double longitude, double latitude, long time, String transport, Step nextStep) {
        this._id = _id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
        this.transport = transport;
        this.nextStep = nextStep;
    }

    public Step(String name, double longitude, double latitude, long time, String transport, Step nextStep) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
        this.transport = transport;
        this.nextStep = nextStep;
    }

    public Step(String name, double longitude, double latitude, long time, String transport) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
        this.transport = transport;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public Step getNextStep() {
        return nextStep;
    }

    public void setNextStep(Step nextStep) {
        this.nextStep = nextStep;
    }

    @Override
    public String toString() {
        return "Step{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", time=" + time +
                ", transport='" + transport + '\'' +
                ", nextStep=" + nextStep +
                '}';
    }

    public String getPosition() {
        return "Position: (Lat: "+latitude+", Long: "+longitude+")";
    }

    public String getFormatTime() {

        int day = (int) TimeUnit.SECONDS.toDays(time);
        long hours = TimeUnit.SECONDS.toHours(time) - (day *24);
        long minute = TimeUnit.SECONDS.toMinutes(time) - (TimeUnit.SECONDS.toHours(time)* 60);
        long second = TimeUnit.SECONDS.toSeconds(time) - (TimeUnit.SECONDS.toMinutes(time) *60);

        return day+"time: day(s) "+hours+ "hour(s) "+minute+" minute(s) "+second+" seconde(s)";
    }
}
