import java.io.*;
import java.util.*;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
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
        Point cur = null;

        while (eventReader.hasNext()) {
            XMLEvent = evenRead.nextEvent();
        }
    }
}