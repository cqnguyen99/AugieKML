package component;

import java.util.*;

public class Line {
    private List<Point> list;
    private String label;

    public Line() {
        list = new ArrayList<Point>();
        label = "";
    }

    public Line(String label) {
        list = new ArrayList<Point>();
        this.label = label;
    }

    public void addPoint(Point lineP){
        list.add(lineP);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public Point getPoint(int i) {
        return list.get(i);
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public int getSize(){
        return list.size();
    }

    public int findPoint(Point lineP){
        return list.indexOf(lineP);
    }

    public boolean clearAll(){
        list.clear();
        return list.isEmpty();
    }

    public String toString() {
        return list.toString();
    }
}