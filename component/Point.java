package component;

public class Point {
    private double longitude;
    private double latitude;
    private double altitude;
    private String label;

    public Point() {
        longitude = 0;
        latitude = 0;
        altitude = 0;
        label = "";
    }

    public Point(double longitude, double latitude, double altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        label = "";
    }

    public Point(String label, double longitude, double latitude, double altitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.label = label;
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

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String toString() {
        return longitude + "," + latitude + "," + altitude;
    }
}