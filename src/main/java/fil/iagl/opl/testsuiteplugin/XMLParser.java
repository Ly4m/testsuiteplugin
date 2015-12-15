package fil.iagl.opl.testsuiteplugin;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by William on 12/12/2015.
 */
public class XMLParser {

    File xmlFile;
    File surefireReport;
    Document doc;
    Document surefireReportDoc;

    Map tests;


    public XMLParser(File file) {
        try {

            this.xmlFile = file;
//            this.surefireReport = surefireReport;
            this.tests = new HashMap<String, Integer>();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.parse(xmlFile);
//            this.surefireReportDoc = dBuilder.parse(surefireReport);
//            surefireReportDoc.getDocumentElement().normalize();
            doc.getDocumentElement().normalize();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

//    public void initTestList() {
//        NodeList nList = this.surefireReportDoc.getElementsByTagName("testcase");
//        for (int temp = 0; temp < nList.getLength(); temp++) {
//            Node nNode = nList.item(temp);
//
//            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element eElement = (Element) nNode;
//
//                System.out.println(eElement);
//                final String className = eElement.getAttribute("classname");
////                final String methodName = eElement.getAttribute("name");
//                tests.put(className, 0);
//
//
//            }
//
//        }
//    }

    public void initDoc() {
        NodeList nList = this.doc.getElementsByTagName("mutation");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if (eElement.getAttribute("status").equals("KILLED")) {
//                    System.out.println(eElement.getElementsByTagName("killingTest").item(0).getTextContent());
//                    final String killingTest = eElement.getElementsByTagName("killingTest").item(0).getTextContent().split("\\(")[1].split("\\)")[0];
//                    System.out.println(eElement.getElementsByTagName("killingTest").item(0).getTextContent());


                    try {
                        String killingTest = eElement.getElementsByTagName("killingTest").item(0).getTextContent().split("\\(")[0];
                        System.out.println("COUCOU " + killingTest);
                        String[] testSplit = killingTest.split("\\.");
                        killingTest = testSplit[testSplit.length-1];
                        tests.put(killingTest, tests.containsKey(killingTest) ? ((Integer) tests.get(killingTest) + 1) : 1);

                        List<Class> testCases = new ArrayList<Class>();

                        try {
                            testCases.add(Class.forName(killingTest));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }


                }
            }

        }
    }

    public Map getTests() {
        return tests;
    }


}
