import component.*;

import java.io.*;
import java.util.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        System.out.print("Enter the KML file: ");
        Scanner input = new Scanner(System.in);
        File file = new File(input.nextLine());

        // Instance of the class which helps on reading tags
        XMLInputFactory factory = XMLInputFactory.newInstance();
      
        // Initializing the handler to access the tags in the XML file
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));
        
        List<Point> points = new ArrayList<>();
        Point point = new Point();
        Point startP = new Point();
        Point endP = new Point();
        Line line = null;
        Polygon poly = null;
        StringBuilder current = new StringBuilder();

        // Checking the availability of the next tag
        while(eventReader.hasNext()) {
            XMLEvent xmlEvent = eventReader.nextEvent();
            if(xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                //Now everytime content tags are found; 
                //Move the iterator and read data
                if("coordinates".equalsIgnoreCase(startElement.getName().getLocalPart())) {
                    Characters nameDataEvent = (Characters)eventReader.nextEvent();
                    current.append(nameDataEvent.getData());
                    String[] coords = current.toString().split(" ");
                    List<String> coordsCopy = new ArrayList<>();
                    for(int i = 0; i < coords.length; i++) {
                        if(coords[i].isBlank()) {
                            coordsCopy.add(coords[++i]);
                        }
                        else
                            coordsCopy.add(coords[i]);
                    }
                    if(coordsCopy.size() == 1) {
                        String[] coord = coordsCopy.get(0).split(",");
                        point.setLongitude(Double.parseDouble(coord[0]));
                        point.setLatitude(Double.parseDouble(coord[1]));
                        point.setAltitude(Double.parseDouble(coord[2]));
                        System.out.println(point.toString() + " point");
                    }
                    else if(coordsCopy.size() == 2) {
                        String[] coord = coordsCopy.get(0).split(",");
                        startP.setLongitude(Double.parseDouble(coord[0]));
                        startP.setLatitude(Double.parseDouble(coord[1]));
                        startP.setAltitude(Double.parseDouble(coord[2]));
                        coord = coordsCopy.get(1).split(",");
                        endP.setLongitude(Double.parseDouble(coord[0]));
                        endP.setLatitude(Double.parseDouble(coord[1]));
                        endP.setAltitude(Double.parseDouble(coord[2]));
                        line = new Line(startP, endP);
                        System.out.println(line.toString() + " line");
                    }
                    else if(coordsCopy.size() > 2) {
                        for(int i = 0; i < coordsCopy.size(); i++) {
                            String[] coord = coordsCopy.get(i).split(",");
                            point.setLongitude(Double.parseDouble(coord[0]));
                            point.setLatitude(Double.parseDouble(coord[1]));
                            point.setAltitude(Double.parseDouble(coord[2]));
                            points.add(point);
                        }
                        poly = new Polygon(points);
                        System.out.println(poly.toString() + " poly");
                    }
                    current.delete(0, current.length()-1);
                }
            }

            if(xmlEvent.isEndElement()) {
                EndElement endElement = xmlEvent.asEndElement();
            }
        }
        input.close();
    }
}