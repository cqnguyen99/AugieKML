package component;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.*;
import java.lang.StringBuilder;

public class Write {
    private List<Point> points;
    private List<Line> lines;
    private List<Polygon> polygons;

    private static final String NS_KML = "http://www.opengis.net/kml/2.2";

    public Write(List<Point> points, List<Line> lines, List<Polygon> polygons) {
        this.points = points;
        this.lines = lines;
        this.polygons = polygons;
    }

    public static void writeKml(OutputStream out, List<Point> points, List<Line> lines, List<Polygon> polygons) throws XMLStreamException {

        XMLOutputFactory output = XMLOutputFactory.newInstance();

        XMLStreamWriter writer = output.createXMLStreamWriter(out);

        writer.writeStartDocument();

        writer.writeStartElement("kml"); // <kml xmlns="http://www.opengis.net/kml/2.2">
        writer.writeDefaultNamespace(NS_KML);

        writer.writeStartElement("Document"); // <Document>

        // no data to write
        if (points.isEmpty() && lines.isEmpty() && polygons.isEmpty()) {
            System.out.println("There are no points, lines, or polygons.");
            System.exit(0);
        }

        // have data
        else {
            if (points.isEmpty() == false) {
                writePoints(writer, points);
            }

            if (lines.isEmpty() == false) {
                writeLines(writer, lines);
            }
                
            if (polygons.isEmpty() == false) {
                writePolygons(writer, polygons);
            }
        }

        writer.writeEndElement(); // </Document>

        writer.writeEndDocument(); // </kml>

        // to output stream
        writer.flush();

        writer.close();

    }

    private static void writePoints(XMLStreamWriter writer, List<Point> points) throws XMLStreamException {
        for (Point point : points) {

            writer.writeStartElement("Placemark"); // <Placemark>

            writer.writeStartElement("name"); // <name>
            writer.writeCharacters(point.getLabel());
            writer.writeEndElement(); // </name>

            writer.writeStartElement("Point"); // <Point>
    
            writer.writeStartElement("coordinates"); // <coordinates>
            writer.writeCharacters(point.toString());
            writer.writeEndElement(); // </coordinates>
    
            writer.writeEndElement(); // </Point>
            
            writer.writeEndElement(); // </Placemark>
        }
    }

    private static void writeLines(XMLStreamWriter writer, List<Line> lines) throws XMLStreamException {
        for (Line line : lines) {

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < line.getSize(); i++) {
                sb.append(line.getPoint(i).toString() + " ");
            }

            writer.writeStartElement("Placemark"); // <Placemark>

            writer.writeStartElement("name"); // <name>
            writer.writeCharacters(line.getLabel());
            writer.writeEndElement(); // </name>

            writer.writeStartElement("LineString"); // <LineString>

            writer.writeStartElement("coordinates"); // <coordinates>

            writer.writeCharacters(sb.toString());

            writer.writeEndElement(); // </coordinates>

            writer.writeEndElement(); // </LineString>

            writer.writeEndElement(); // </Placemark>
        }
    }

    private static void writePolygons(XMLStreamWriter writer, List<Polygon> polygons) throws XMLStreamException {
        for (Polygon polygon : polygons) {

            StringBuilder sbOuter = new StringBuilder();
            StringBuilder sbInner = new StringBuilder();

            for (int i = 0; i < polygon.getOuterSize(); i++) {
                sbOuter.append(polygon.getOuterPoint(i).toString() + " ");
            }
            writer.writeStartElement("Placemark");

            writer.writeStartElement("name"); // <name>
            writer.writeCharacters(polygon.getLabel());
            writer.writeEndElement(); // </name>
            
            writer.writeStartElement("Polygon"); // <Polygon>

            writer.writeStartElement("outerBoundaryIs"); // <outerBoundary>

            writer.writeStartElement("LinearRing"); // <LinearRing>

            writer.writeStartElement("coordinates"); // <coordinates>

            writer.writeCharacters(sbOuter.toString());

            writer.writeEndElement(); // </coordinates>

            writer.writeEndElement(); // </LinearRing>

            writer.writeEndElement(); // </outerBoundaryIs>

            if (polygon.hasInner()) {

                for (int i = 0; i < polygon.getInnerSize(); i++) {
                    sbInner.append(polygon.getInnerPoint(i).toString() + " ");
                }
                writer.writeStartElement("innerBoundaryIs"); // <innerBoundary>

                writer.writeStartElement("LinearRing"); // <LinearRing>

                writer.writeStartElement("coordinates"); // <coordinates>

                writer.writeCharacters(sbInner.toString());

                writer.writeEndElement(); // </coordinates>

                writer.writeEndElement(); // </LinearRing>

                writer.writeEndElement(); // </innerBoundaryIs>
            }

            writer.writeEndElement(); // </Polygon>

            writer.writeEndElement(); // </Placemark>
        }
    }

    // makes the kml file more human-readable (Source: https://mkyong.com/java/how-to-write-xml-file-in-java-stax-writer/)
    public static String formatKML(String kml) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print by indention
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
    
        StreamSource source = new StreamSource(new StringReader(kml));
        StringWriter output = new StringWriter();
        transformer.transform(source, new StreamResult(output));

        return output.toString();

    }
}
