import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Control_de_datos {

	private static String url; 
	private static String user;
	private static String password;
	private static String ficheroTxt;
	private static String ficheroBin;
	private static String ficheroXML;
	
	public static void main(String [] args) {
		try {
            File inputFile = new File("parametros.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nodeCiudadesInfectadasInicio = doc.getElementsByTagName("numCiudadesInfectadasInicio");
            NodeList nodeCiudadesInfectadasRonda = doc.getElementsByTagName("numCuidadesInfectadasRonda");
            NodeList nodeEnfermedadesActivasDerrota = doc.getElementsByTagName("numEnfermedadesActivasDerrota");
            NodeList nodeNumBrotesDerrota = doc.getElementsByTagName("numBrotesDerrota");

            Node node1 = nodeCiudadesInfectadasInicio.item(0);
            Node node2 = nodeCiudadesInfectadasRonda.item(0);
            Node node3 = nodeEnfermedadesActivasDerrota.item(0);
            Node node4 = nodeNumBrotesDerrota.item(0);
                
            if (node1.getNodeType() == Node.ELEMENT_NODE || node2.getNodeType() == Node.ELEMENT_NODE || node3.getNodeType() == Node.ELEMENT_NODE || node4.getNodeType() == Node.ELEMENT_NODE) {
                String CiudadesInfectadasInicio = nodeCiudadesInfectadasInicio.item(0).getTextContent();
                String CiudadesInfectadasRonda = nodeCiudadesInfectadasRonda.item(0).getTextContent();
                String EnfermedadesActivasDerrota = nodeEnfermedadesActivasDerrota.item(0).getTextContent();
                String NumBrotesDerrota = nodeNumBrotesDerrota.item(0).getTextContent();
                
                System.out.println("numCiudadesInfectadasInicio : " + CiudadesInfectadasInicio);
                System.out.println("numCuidadesInfectadasRonda : "+ CiudadesInfectadasRonda);
                System.out.println("numEnfermedadesActivasDerrota : "+ EnfermedadesActivasDerrota );
                System.out.println("numBrotesDerrota : "+ NumBrotesDerrota );
                System.out.println("----------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static ArrayList<Ciudad> cargarCiudades() {
		return null;
	}
	
	public static ArrayList<Vacunas> cargarVacunas() {
		return null;
	}
	
	public static ArrayList<Virus> cargarVirus() {
		return null;
	}
	
	public static void cargarPartida() {

	}
	
	public static void guardarPartida() {

	}
	
	public static void cargarRecord() {

	}
	
	public static void guardarRecord() {

	}
}
