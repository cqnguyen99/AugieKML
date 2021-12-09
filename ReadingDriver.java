import component.*;

import java.io.*;
import java.util.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class ReadingDriver {
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException, IOException {
        // Read the input kml file
        System.out.print("Enter the KML file: ");
        Scanner input = new Scanner(System.in);
        File file = new File(input.nextLine());

        // Instance of the class which helps on reading tags
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty("javax.xml.stream.isCoalescing", true);
      
        // Initializing the handler to access the tags in the XML file
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));
        
        // Lists of points, lines, and polygons 
        List<Point> points = new ArrayList<>();
        List<Line> lines = new ArrayList<>();
        List<Polygon> polygons = new ArrayList<>();
        String current = "";

        // Checking the availability of the next tag
        while(eventReader.hasNext()) {
            XMLEvent xmlEvent = eventReader.nextEvent();
            if(xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();

                // Now everytime content tags are found. Move the iterator and read data
                if(startElement.getName().getLocalPart().equals("coordinates")) {
                    Characters nameDataEvent = (Characters)eventReader.nextEvent();
                    current = nameDataEvent.getData();
                    current = current.trim();
                    // Splt the string current that read from a stream by space and blank line
                    String[] coords = current.split("[\\n|\\s]");

                    // List of all points after cleaning (processing)
                    List<String> newCoords = new ArrayList<>(); 
                    String curCoord = "";

                    // Remove any space or blank line between coordinates
                    // Make sure every coordinate is in the same format: longitude, latitude, altitude
                    for (String s: coords) {
                        if (s.isBlank()) continue;
                        if (!s.substring(s.length()-1).equals(",")) {
                            if (curCoord.length() == 0) {
                                newCoords.add(s);
                            }
                            else {
                                curCoord += s;
                                newCoords.add(curCoord);
                                curCoord = "";
                            }
                        }
                        else {
                            curCoord += s;
                        }
                    }
                    readCoordinates(newCoords, points, lines, polygons);
                    current = "";
                }
            }
        }

        // Write points, lines, polygons to a text file
        outputToText(points, lines, polygons);

        input.close();
    }

    // Add coordinates from List<String> to Lists of points, lines, and polygons
    public static void readCoordinates(List<String> newCoords, List<Point> points, List<Line> lines, List<Polygon> polygons) {
        // If the list of coordinates has length of 1, it is a point.
        if(newCoords.size() == 1) {         // Point
            String[] coord = newCoords.get(0).split(",");
            Double[] newCoord = convertToDecimal(coord);    // Convert different coordinates' types (DMS, DDM, DD) to DD type
            
            // If the longitude and latitude are valid , add coordinates to list of points 
            if (checkValidPoint(newCoord)) {
                Point point = new Point(newCoord[0], newCoord[1], newCoord[2]);
                points.add(point);
            }
        }

        else {
            // If the first coordinate is the same as the last coordinate in the list, it is a polygon. Otherwise, it is a line.
            if (!newCoords.get(0).trim().equalsIgnoreCase(newCoords.get(newCoords.size()-1).trim())) {      // Line
                Line line = new Line();
                for (String s: newCoords) {
                    if (s.isBlank()) continue;
                    String[] coord = s.split(",");
                    Double[] newCoord = convertToDecimal(coord);    
                    if (checkValidPoint(newCoord)) {
                        Point point = new Point(newCoord[0], newCoord[1], newCoord[2]);
                        line.addPoint(point);
                    }
                }
                lines.add(line);
            }
            else {      // Polygon
                Polygon polygon = new Polygon();
                for (String s: newCoords) {
                    if (s.isBlank()) continue;
                    String[] coord = s.split(",");
                    Double[] newCoord = convertToDecimal(coord);
                    if (checkValidPoint(newCoord)) {
                        Point point = new Point(newCoord[0], newCoord[1], newCoord[2]);
                        polygon.addOuterPoint(point);
                    }
                }
                polygons.add(polygon);
            }
        }
    } 

    // Check if -180 <= longitude <= 180 and -90 <= latitude <= 90 
    public static boolean checkValidPoint(Double[] coord) {
        if (coord[0] >= -180.0 && coord[0] <= 180.0 && coord[1] >= -90.0 && coord[1] <= 90.0) {
            return true;
        }
        return false;
    }

    // Convert different coordinates' types (DMS, DDM, DD) to DD type
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

    // Write points, lines, polygons to a text file 
    public static void outputToText(List<Point> points, List<Line> lines, List<Polygon> polygons) throws FileNotFoundException, IOException {
        System.out.print("Name of the text output file: ");
        Scanner input = new Scanner(System.in);
        String file = input.next();

        FileWriter writer = new FileWriter(file);

        // Write list of points
        writer.write("Point" + "\n");
        for (Point p: points) {
            writer.write(p + System.lineSeparator());
        }

        // Write list of lines
        writer.write("Line" + "\n");
        for (Line l: lines) {
            writer.write(l + System.lineSeparator());
        }

        // Write list of polygons
        writer.write("Polygon" + "\n");
        for (Polygon po: polygons) {
            writer.write(po + System.lineSeparator());
        }

        writer.close();
        input.close();
    }
}