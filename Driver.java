import component.*;

import java.io.*;
import java.util.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        System.out.print("Enter the KML file: ");
        Scanner input = new Scanner(System.in);
        File file = new File(input.nextLine());

        // Instance of the class which helps on reading tags
        //XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty("javax.xml.stream.isCoalescing", true);
      
        // Initializing the handler to access the tags in the XML file
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));
        
        List<Point> points = new ArrayList<>();
        List<Line> lines = new ArrayList<>();
        List<Polygon> polygons = new ArrayList<>();
        String current = "";

        // Checking the availability of the next tag
        while(eventReader.hasNext()) {
            XMLEvent xmlEvent = eventReader.nextEvent();
            if(xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                //Now everytime content tags are found; 
                //Move the iterator and read data
                if(startElement.getName().getLocalPart().equals("coordinates")) {
                    Characters nameDataEvent = (Characters)eventReader.nextEvent();
                    current = nameDataEvent.getData();
                    current = current.trim();
                    String[] coords = current.split("[\\n|\\s]");
                    List<String> newCoords = new ArrayList<>(); 
                    String curCoord = "";
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
        System.out.println(points);
        System.out.println(lines);
        System.out.println(polygons);
        input.close();
    }

    public static void readCoordinates(List<String> newCoords, List<Point> points, List<Line> lines, List<Polygon> polygons) {
        if(newCoords.size() == 1) {
            String[] coord = newCoords.get(0).split(",");
            //boolean check = true;
            if (checkValidPoint(coord)) {
                Point point = new Point(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]), Double.parseDouble(coord[2]));
                points.add(point);
            }
        }

        else {
            if (!newCoords.get(0).trim().equalsIgnoreCase(newCoords.get(newCoords.size()-1).trim())) {
                Line line = new Line();
                System.out.println(newCoords);
                for (String s: newCoords) {
                    if (s.isBlank()) continue;
                    String[] coord = s.split(",");
                    if (checkValidPoint(coord)) {
                        Point point = new Point(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]), Double.parseDouble(coord[2]));
                        line.addPoint(point);
                    }
                }
                lines.add(line);
            }
            else {
                Polygon polygon = new Polygon();
                for (String s: newCoords) {
                    if (s.isBlank()) continue;
                    String[] coord = s.split(",");
                    if (checkValidPoint(coord)) {
                        Point point = new Point(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]), Double.parseDouble(coord[2]));
                        polygon.addOuterPoint(point);
                    }
                }
                polygons.add(polygon);
            }
        }
    } 

    public static boolean checkValidPoint(String[] coord) {
        try {
            if (Double.parseDouble(coord[0]) >= -180.0 && Double.parseDouble(coord[0]) <= 180.0 && 
            Double.parseDouble(coord[1]) >= -90.0 && Double.parseDouble(coord[1]) <= 90.0) {
                return true;
            }
        }
        catch (NumberFormatException ex) {
            return true;
        }
        return false;
    }
}