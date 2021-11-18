import java.util.*;
public class Polygon
{
    private Point startP;
    private Point endP;
    private List<Point> points;

    public Polygon(List<Point> points) {
        this.startP = points.get(0);
        this.endP = points.get(points.size()-1);
        this.points = points;
    }

    public void setStartPoint(Point point) {
        this.startP = point;
    }

    public void setEndPoint(Point point) {
        this.endP = point;
    }

    public Point getStartPoint() {
        return startP;
    }

    public Point getEndPoint() {
        return endP;
    }

    public String toString() {
        String output = "";
        for(int i = 0; i < points.size(); i++) {
            output = output + points.get(i).toString() + " ";
        }
        return output;
    }
}