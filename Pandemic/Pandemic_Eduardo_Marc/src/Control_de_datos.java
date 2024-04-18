import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Control_de_datos {

	private static String url; 
	private static String user;
	private static String password;
	private static String ficheroTxt;
	private static String ficheroBin;
	private static String ficheroXML;
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String [] args) {
		try {
		    File inputFile = new File("parametros.xml");
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(inputFile);
		    doc.getDocumentElement().normalize();

		    Element rootElement = doc.createElement("parametros");

		    NodeList nodeCiudadesInfectadasInicio = doc.getElementsByTagName("numCiudadesInfectadasInicio");
		    NodeList nodeCiudadesInfectadasRonda = doc.getElementsByTagName("numCuidadesInfectadasRonda");
		    NodeList nodeEnfermedadesActivasDerrota = doc.getElementsByTagName("numEnfermedadesActivasDerrota");
		    NodeList nodeNumBrotesDerrota = doc.getElementsByTagName("numBrotesDerrota");

		    Node node1 = nodeCiudadesInfectadasInicio.item(0);
		    Node node2 = nodeCiudadesInfectadasRonda.item(0);
		    Node node3 = nodeEnfermedadesActivasDerrota.item(0);
		    Node node4 = nodeNumBrotesDerrota.item(0);

		    if (node1 != null && node2 != null && node3 != null && node4 != null) {
		        String CiudadesInfectadasInicio = node1.getTextContent();
		        String CiudadesInfectadasRonda = node2.getTextContent();
		        String EnfermedadesActivasDerrota = node3.getTextContent();
		        String NumBrotesDerrota = node4.getTextContent();

		        Element rootElement_rewrite = doc.createElement("parametros");

		        System.out.println("numCiudadesInfectadasInicio : " + CiudadesInfectadasInicio);
		        Element item = doc.createElement("numCiudadesInfectadasInicio");
		        System.out.print("\nnumCiudadesInfectadasInicio : ");
		        String valor1 = ModificarXML(CiudadesInfectadasInicio);
		        item.appendChild(doc.createTextNode(valor1));
		        rootElement_rewrite.appendChild(item);
		        
		        System.out.println("----------------------");
		        
		        System.out.println("numCuidadesInfectadasRonda : "+ CiudadesInfectadasRonda);
		        Element item2 = doc.createElement("numCuidadesInfectadasRonda");
		        System.out.print("\nnumCuidadesInfectadasRonda : ");
		        String valor2 = ModificarXML(CiudadesInfectadasRonda);
		        item2.appendChild(doc.createTextNode(valor2));
		        rootElement_rewrite.appendChild(item2);
		        
		        System.out.println("----------------------");
		        
		        System.out.println("numEnfermedadesActivasDerrota : "+ EnfermedadesActivasDerrota );
		        Element item3 = doc.createElement("numEnfermedadesActivasDerrota");
		        System.out.print("\nnumEnfermedadesActivasDerrota : ");
		        String valor3 = ModificarXML(EnfermedadesActivasDerrota);
		        item3.appendChild(doc.createTextNode(valor3));
		        rootElement_rewrite.appendChild(item3);
		        
		        System.out.println("----------------------");
		        
		        System.out.println("numBrotesDerrota : "+ NumBrotesDerrota );
		        Element item4 = doc.createElement("numBrotesDerrota");
		        System.out.print("\nnumBrotesDerrota : ");
		        String valor4 = ModificarXML(NumBrotesDerrota);
		        item4.appendChild(doc.createTextNode(valor4));
		        rootElement_rewrite.appendChild(item4);

		        System.out.println("----------------------");
		        
		        TransformerFactory transformerFactory = TransformerFactory.newInstance();
		        Transformer transformer = transformerFactory.newTransformer();
		        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Define la cantidad de espacios para la sangría

		        DOMSource source = new DOMSource(rootElement_rewrite);
		        StreamResult result = new StreamResult("parametros.xml");

		        transformer.transform(source, result);
		    }

		} catch (Exception e) {
		    e.printStackTrace();
		}
		
	}
	
	public static String ModificarXML(String valor) {
		while (true) {
<<<<<<< HEAD
            System.out.print("Ingrese el nuevo valor para " + valor + " o presione Enter para mantener el valor existente:");
=======
            System.out.print("Ingrese el valor nuevo o presione Enter para mantener el valor existente (" + valor + "): ");
>>>>>>> Marc
            String variable1 = scan.nextLine();

            if (variable1.isEmpty()) {
                return valor;
            }

            if (variable1.matches("\\d+")) {
                return variable1;
            } else {
                System.out.println("El valor ingresado no es un número. Inténtelo de nuevo.");
            }
        }
	}
	public static <Ciudad> ArrayList<Ciudad> cargarCiudades() {
		return null;
	}
	
	public static <Vacunas> ArrayList<Vacunas> cargarVacunas() {
		return null;
	}
	
	public static <Virus> ArrayList<Virus> cargarVirus() {
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
