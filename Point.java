import java.util.*;
import java.io.*;

public class Point {
    private double longitude;
    private double latitude;
    private double altitude;

    public Point() {
        longitude = 0;
        latitude = 0;
        altitude = 0;
    }

    public Point(double longitude, double latitude, double altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String toString() {
        return longitude + " " + latitude + " " + altitude;
    }
}
