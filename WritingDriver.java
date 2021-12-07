import component.*;

import java.io.*;
import java.util.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
// import javax.xml.transform.OutputKeys;
// import javax.xml.transform.Transformer;
// import javax.xml.transform.TransformerFactory;
// import javax.xml.transform.stream.StreamResult;
// import javax.xml.transform.stream.StreamSource;

public class WritingDriver {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Lists of points, lines, and polygons 
        List<Point> points = new ArrayList<>();
        List<Line> lines = new ArrayList<>();
        List<Polygon> polygons = new ArrayList<>();

        try {
            readTxtTest(args[0], points, lines, polygons);

            // Output file path
            System.out.print("Name of the output file: ");
            String fileName = input.nextLine();
            String p = System.getProperty("user.dir");
            FileWriter fw = new FileWriter(p + File.separator + fileName + ".kml");
            String path = p + File.separator + fileName + ".kml";

            // Output stream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Write.writeKml(out, points, lines, polygons);

            // Standard way to convert byte array to String
            String kml = new String(out.toByteArray(), StandardCharsets.UTF_8);

            // Write to the output kml file
            Files.writeString(Paths.get(path), Write.formatKML(kml), StandardCharsets.UTF_8);
            fw.close();
        } 
        catch (TransformerException | XMLStreamException | IOException e) {
            e.printStackTrace();
        }
        input.close();
    }

    // Read from a text file and add all the coordinates to points, lines, or polygons accordingly 
    public static void readTxtTest(String file, List<Point> points, List<Line> lines, List<Polygon> polygons) throws FileNotFoundException {
        File txtFile = new File(file);
        Scanner scan = new Scanner(txtFile);

        String temp = "";
        while (scan.hasNext()) {
            String cur = "";
            if (temp.isEmpty()) {
                cur = scan.next();
            }
            else cur = temp;
            // Check if it is a point, add coordinates to the points list
            if (cur.equalsIgnoreCase("point")) {
                Point p = new Point(scan.next(), scan.nextDouble(), scan.nextDouble(), scan.nextDouble());
                points.add(p);
            }
            // Check if it is a line, add coordinates to the lines list
            else if (cur.equalsIgnoreCase("line")) {
                scan.next();
                Line l = new Line();
                while (scan.hasNextDouble()) {
                    Point lp = new Point(scan.nextDouble(), scan.nextDouble(), scan.nextDouble());
                    l.addPoint(lp);
                }
                lines.add(l);
            }
            // Check if it is a polygon, add coordinates to the polygon list
            else if (cur.equalsIgnoreCase("polygon")) {
                Polygon poly = new Polygon();
                scan.next();
                while (scan.hasNextDouble()) {
                    Point op = new Point(scan.nextDouble(), scan.nextDouble(), scan.nextDouble());
                    poly.addOuterPoint(op);
                }
                temp = scan.next();
                // check if the polygon has the innerboudary
                if (temp.equalsIgnoreCase("innerboundary")) {
                    while (scan.hasNextDouble()) {
                        Point ip = new Point(scan.nextDouble(), scan.nextDouble(), scan.nextDouble());
                        poly.addInnerPoint(ip);
                    }
                    polygons.add(poly);
                    temp = "";
                }
                else {
                    polygons.add(poly);
                }
            }
        }
        scan.close();
    }
}