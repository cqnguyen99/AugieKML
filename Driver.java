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
        Point point = null;
        StringBuilder current = new StringBuilder();

        // Checking the availability of the next tag
        while (eventReader.hasNext()) {
            XMLEvent xmlEvent = eventReader.nextEvent();
            if (xmlEvent.isStartElement()) {
                StartElement startElement = xmlEvent.asStartElement();
                if ("Point".equalsIgnoreCase(startElement.getName().getLocalPart())) {
                    point = new Point();
                }

                //Now everytime content tags are found; 
                //Move the iterator and read data
                if ("coordinates".equalsIgnoreCase(startElement.getName().getLocalPart())) {
                    Characters nameDataEvent = (Characters) eventReader.nextEvent();
                    current.append(nameDataEvent.getData());
                    String[] coor = current.toString().split(",");
                    Double longitude = Double.parseDouble(coor[0]);
                    Double latitude = Double.parseDouble(coor[1]);
                    Double altitude = Double.parseDouble(coor[2]);
                    point.setLongitude(longitude);
                    point.setLatitude(latitude);
                    point.setAltitude(altitude);
                }
            }

            if (xmlEvent.isEndElement())
            {
                EndElement endElement = xmlEvent.asEndElement();
                 
                //If employee tag is closed then add the employee object to list; 
                //and be ready to read next employee data
                if("Point".equalsIgnoreCase(endElement.getName().getLocalPart())) {
                    points.add(point);
                }
            }
        }
        System.out.println(points);
        input.close();
    }
}