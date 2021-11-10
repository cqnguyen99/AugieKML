import java.util.*;
import java.io.*;

public class Point {
    private double longitude = 0;
    private double latitude = 0;
    private double altitude = 0;

    public Point(double longitude, double latitude, double altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
    }

    public String toString() {
        return longitude + " " + latitude + " " + altitude;
    }
}
