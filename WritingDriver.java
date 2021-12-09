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
            System.out.print("Name of the KML output file: ");
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
        while (scan.hasNextLine()) {
            String cur = "";
            if (temp.isEmpty()) {
                cur = scan.nextLine();
            }
            else cur = temp;
            // Check if it is a point, add coordinates to the points list
            if (cur.equalsIgnoreCase("point")) {
                String label = scan.nextLine();
                String[] coord = scan.nextLine().split("\\s");
                Double[] newCoord = convertToDecimal(coord);
                if (checkValidPoint(newCoord)) {
                    Point point = new Point(label, newCoord[0], newCoord[1], newCoord[2]);
                    points.add(point);
                }
                temp = scan.nextLine();
            }
            // Check if it is a line, add coordinates to the lines list
            else if (cur.equalsIgnoreCase("line")) {
                Line l = new Line();
                // Set label of line
                l.setLabel(scan.nextLine());
                while (scan.hasNextLine()) {
                    temp = scan.nextLine();
                    String[] coord = temp.split("\\s");
                    if (coord.length == 3) {
                        Double[] newCoord = convertToDecimal(coord);
                        if (checkValidPoint(newCoord)) {
                            Point lp = new Point(newCoord[0], newCoord[1], newCoord[2]);
                            l.addPoint(lp);
                        }
                    }
                    else break;
                }
                lines.add(l);
            }
            // Check if it is a polygon, add coordinates to the polygon list
            else if (cur.equalsIgnoreCase("polygon")) {
                Polygon poly = new Polygon();
                // Set label of polygon
                poly.setLabel(scan.nextLine());
                while (scan.hasNextLine()) {
                    temp = scan.nextLine();
                    String[] coord = temp.split("\\s");
                    if (coord.length == 3) {
                        Double[] newCoord = convertToDecimal(coord);
                        if (checkValidPoint(newCoord)) {
                            Point op = new Point(newCoord[0], newCoord[1], newCoord[2]);
                            poly.addOuterPoint(op);
                        }
                    }
                    else break;
                }
                // Check if the polygon has the innerboudary
                if (temp.equalsIgnoreCase("inner boundary")) {
                    while (scan.hasNextLine()) {
                        temp = scan.nextLine();
                        String[] coord = temp.split(" ");
                        if (coord.length == 3) {
                            Double[] newCoord = convertToDecimal(coord);
                            if (checkValidPoint(newCoord)) {
                                Point ip = new Point(newCoord[0], newCoord[1], newCoord[2]);
                                poly.addInnerPoint(ip);
                            }
                        }
                        else break;
                    }
                    polygons.add(poly);
                }
                else {
                    polygons.add(poly);
                }
            }
        }
        scan.close();
    }

    // Check if -180 <= longitude <= 180 and -90 <= latitude <= 90 
    public static boolean checkValidPoint(Double[] coord) {
        if (coord[0] >= -180.0 && coord[0] <= 180.0 && coord[1] >= -90.0 && coord[1] <= 90.0) {
            return true;
        }
        return false;
    }

    public static Double[] convertToDecimal(String[] coord) {
        // If the coordinates are already in DD format, return those coordinates
        try {
            return new Double[] {Double.parseDouble(coord[0]), Double.parseDouble(coord[1]), Double.parseDouble(coord[2])};
        }
        catch (NumberFormatException ex) {
            // Split DMS and DDM types by number and '.' For example, 122°27'W = [122, 27]
            // In that case, if length of the string array >= 3, it is in the DMS format. Otherwise, it is in the DDM format
            String[] split = coord[0].split("[^0-9.]");

            // Convert DMS to DD: DD = D + M/60 + S/3600
            if (split.length >= 3) {    
                Double[] convertedCoord = new Double[3];    // double array of longitude, latitude, altitude
                convertedCoord[2] = 0.0;                    // altitude wll equals to 0 as default
                for (int i=0; i<coord.length - 1; i++) {
                    String[] s = coord[i].split("[^0-9.]");
                    double decimal = Double.parseDouble(s[0]) + Double.parseDouble(s[1])/60 + Double.parseDouble(s[2])/3600;
                    String direction = coord[i].substring(coord[i].length()-1);     // Get the direction: N, S, E, W
                    // If the direction is S or W, the sign is negative
                    // If the direction is N or E, the sign is positive
                    if (direction.equals("S") || direction.equals("W")) {
                        decimal = -decimal;
                    }
                    convertedCoord[i] = decimal;
                }
                return convertedCoord;
            }

            // Convert DDM to DD: DD = D + M/60
            else {
                Double[] convertedCoord = new Double[3];
                convertedCoord[2] = 0.0;
                for (int i=0; i<coord.length - 1; i++) {
                    String[] s = coord[i].split("[^0-9.]");
                    double decimal = Double.parseDouble(s[0]) + Double.parseDouble(s[1])/60;
                    String direction = coord[i].substring(coord[i].length()-1);
                    if (direction.equals("S") || direction.equals("W")) {
                        decimal = -decimal;
                    }
                    convertedCoord[i] = decimal;
                }
                return convertedCoord;
            }
        }
    }
}