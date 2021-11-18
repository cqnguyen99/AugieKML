//package component;

import java.util.*;
import java.io.*;

public class Line {
    private Point startP;
    private Point endP;

    public Line(Point startP, Point endP) {
        this.startP = startP;
        this.endP = endP;
    }

    public String toString() {
        return startP.toString()+ " to " + endP.toString();
    }

    public void setStartPoint(Point point) {
        this.startP = point;
    }

    public void setEndPoint(Point point) {
        this.endP = point;
    }

    public Point getStartPoint(){
        return startP;
    }
    public Point getEndPoint(){
        return endP;
    }
}