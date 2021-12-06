import component.*;

import java.io.*;
import java.util.*;

import javax.xml.stream.XMLStreamException;
// import javax.xml.transform.OutputKeys;
// import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
// import javax.xml.transform.TransformerFactory;
// import javax.xml.transform.stream.StreamResult;
// import javax.xml.transform.stream.StreamSource;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WritingDriver {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // lists
        List<Point> points = new ArrayList<>();
        List<Line> lines = new ArrayList<>();
        List<Polygon> polygons = new ArrayList<>();

        try {
            readTxtTest(args[0], points, lines, polygons);

            // file path
            System.out.print("Name File: ");
            String fileName = input.nextLine();
            String p = System.getProperty("user.dir");
            FileWriter fw = new FileWriter(p + File.separator + fileName + ".kml");
            String path = p + File.separator + fileName + ".kml";

            // output stream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Write.writeKml(out, points, lines, polygons);

            // standard way to convert byte array to String
            String kml = new String(out.toByteArray(), StandardCharsets.UTF_8);

            // write to file
            Files.writeString(Paths.get(path), Write.formatKML(kml), StandardCharsets.UTF_8);
            fw.close();
        } 
        catch (TransformerException | XMLStreamException | IOException e) {
            e.printStackTrace();
        }
        input.close();
    }
    // read txt file for testing and demo
    public static void readTxtTest(String file, List<Point> points, List<Line> lines, List<Polygon> polygons) throws FileNotFoundException {
        File txtFile = new File(file);
        Scanner scan = new Scanner(txtFile);

        while (scan.hasNext()) {
            if (scan.next().equalsIgnoreCase("point")) {
                Point p = new Point(scan.next(), scan.nextDouble(), 
                scan.nextDouble(), scan.nextDouble());
                points.add(p);
            }

            if (scan.next().equalsIgnoreCase("line")) {
                Line l = new Line(scan.next());
                while (scan.hasNextDouble()) {
                    Point lp = new Point(scan.nextDouble(), scan.nextDouble(), scan.nextDouble());
                    l.addPoint(lp);
                }
                lines.add(l);
            }

            if (scan.next().equalsIgnoreCase("polygon")) {
                Polygon poly = new Polygon(scan.next());
                while (scan.hasNextDouble()) {
                    Point op = new Point(scan.nextDouble(), scan.nextDouble(), scan.nextDouble());
                    poly.addOuterPoint(op);
                }
                if (scan.next().equalsIgnoreCase("innerboundary")) {
                    while (scan.hasNextDouble()) {
                        Point ip = new Point(scan.nextDouble(), scan.nextDouble(), scan.nextDouble());
                        poly.addInnerPoint(ip);
                    }
                    polygons.add(poly);
                }
                else {polygons.add(poly);}
            }
        }
        scan.close();
    }
}