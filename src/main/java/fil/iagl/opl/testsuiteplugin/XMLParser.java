package fil.iagl.opl.testsuiteplugin;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by William on 12/12/2015.
 */
public class XMLParser {

    File xmlFile;
    Document doc;

    Map tests;

    public XMLParser(String path){
        try {

            URL url = getClass().getResource("/config");
            BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));


            this.xmlFile = new File(path);
            this.tests = new HashMap();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        NodeList nList = this.doc.getElementsByTagName("mutation");

        for(int temp = 0; temp < nList.getLength(); temp++){
            Node nNode = nList.item(temp);

            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                Element eElement = (Element) nNode;

                if(eElement.getAttribute("status").equals("KILLED")){

                    final String killingTest = eElement.getElementsByTagName("killingTest").item(0).getTextContent();
                    tests.put(killingTest, tests.containsKey(killingTest) ? ((Integer) tests.get(killingTest) + 1 ) : 1);

                }
            }

        }

        tests.forEach((key, value) -> System.out.println(key + " " +  value));

    }



}
