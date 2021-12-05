package component;

import java.util.*;

public class Polygon
{
    private List<Point> outerBoundary;
    private List<Point> innerBoundary;
    private String label;

    public Polygon() {
        outerBoundary = new ArrayList<>();
        innerBoundary = new ArrayList<>();
        label = "";
    }

    public Polygon(String label) {
        outerBoundary = new ArrayList<>();
        innerBoundary = new ArrayList<>();
        this.label = label;
    }

    public void addOuterPoint(Point polyP){
        outerBoundary.add(polyP);
    }

    public void addInnerPoint(Point polyP){
        innerBoundary.add(polyP);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public Point getOuterPoint(int i) {
        return outerBoundary.get(i);
    }

    public Point getInnerPoint(int i) {
        return outerBoundary.get(i);
    }

    public boolean hasInner() {
        if (innerBoundary.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }
    
    public boolean isOuterEmpty() {
        return outerBoundary.isEmpty();
    }

    public int getOuterSize() {
        return outerBoundary.size();
    }

    public int getInnerSize() {
        return innerBoundary.size();
    }

    public int findOuterPoint(Point polyP){
        return outerBoundary.indexOf(polyP);
    }

    public int findInnerPoint(Point polyP){
        return innerBoundary.indexOf(polyP);
    }

    public void clearAll() {
        outerBoundary.clear();
        innerBoundary.clear();
    }

    public String toString() {
        String s = "Outer Boundary: " + outerBoundary.toString() + "\nInner Boundary: " + innerBoundary.toString();
        if (innerBoundary.isEmpty()) {
            return outerBoundary.toString();
        }
        else {
            return s;
        }
    }
}