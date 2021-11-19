package component;

import java.util.*;

public class Polygon
{
    // private Point startP;
    // private Point endP;
    private List<Point> list;

    public Polygon() {
        list = new ArrayList<>();
    }

    public void addPoint(Point polyP){
        list.add(polyP);
    }
    // public Polygon(List<Point> points) {
    //     this.startP = points.get(0);
    //     this.endP = points.get(points.size()-1);
    //     this.points = points;
    // }

    // public void setStartPoint(Point point) {
    //     this.startP = point;
    // }

    // public void setEndPoint(Point point) {
    //     this.endP = point;
    // }

    // public Point getStartPoint() {
    //     return startP;
    // }

    // public Point getEndPoint() {
    //     return endP;
    // }

    public String toString() {
        return list.toString();
    }
}