// KML Writer

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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KMLWriterDriver {

    public static void main(String[] args) {
    
      String file="C:\\users\\benja\\KMLProject\\KMLfile.xml";
        
        
      List<KMLWriter> kmlList = new ArrayList<>();
      kmlList.add(new KMLWriter("iPhone","6s"));
      kmlList.add(new KMLWriter("iPhone","7"));
      kmlList.add(new KMLWriter("iPad","2"));
        
      XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        
      try(FileOutputStream fos = new FileOutputStream(file)){
        XMLStreamWriter writer = outputFactory.createXMLStreamWriter(fos);
          
        writer.writeStartDocument("UTF-8", "1.0");
          writer.writeCharacters("\n");
          writer.writeStartElement("devices");
          writer.writeCharacters("\n");
          
          for (KMLWriter k : kmlList){
            writer.writeCharacters("\t");
            writer.writeStartElement("device");
            writer.writeCharacters("\n\t\t");
            writer.writeStartElement("name");
            writer.writeCharacters(k.getName());
            writer.writeEndElement();
            writer.writeCharacters("\n\t\t");
            writer.writeStartElement("model");
            writer.writeCharacters(k.getModel());
            writer.writeEndElement();
            writer.writeCharacters("\n\t");
            writer.writeEndElement();
            writer.writeCharacters("\n");
          }
          writer.writeEndElement();
          writer.writeEndDocument();
          writer.close();
      }
        catch (FileNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        catch (XMLStreamException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    }
}