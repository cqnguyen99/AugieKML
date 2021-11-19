package component;

import java.util.*;

public class Line {
    private List<Point> list;

    public Line() {
        list = new ArrayList<Point>();
    }

    public void addPoint(Point lineP){
        list.add(lineP);
    }

    // public Point getStartPoint(){
    //     return list.get(0);
    // }

    // public Point getEndPoint(){
    //     return list.get(-1);
    // }

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