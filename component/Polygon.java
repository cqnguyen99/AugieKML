package component;

import java.util.*;

public class Polygon
{
    private List<Point> list;

    public Polygon() {
        list = new ArrayList<>();
    }

    public void addPoint(Point polyP){
        list.add(polyP);
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