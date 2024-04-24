import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
		    File inputFile = new File("src//files//parametros.xml");
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
		        String valor1 = ModificarXML(CiudadesInfectadasInicio);
		        item.appendChild(doc.createTextNode(valor1));
		        rootElement_rewrite.appendChild(item);
		        
		        System.out.println("----------------------");
		        
		        System.out.println("numCuidadesInfectadasRonda : "+ CiudadesInfectadasRonda);
		        Element item2 = doc.createElement("numCuidadesInfectadasRonda");
		        String valor2 = ModificarXML(CiudadesInfectadasRonda);
		        item2.appendChild(doc.createTextNode(valor2));
		        rootElement_rewrite.appendChild(item2);
		        
		        System.out.println("----------------------");
		        
		        System.out.println("numEnfermedadesActivasDerrota : "+ EnfermedadesActivasDerrota );
		        Element item3 = doc.createElement("numEnfermedadesActivasDerrota");
		        String valor3 = ModificarXML(EnfermedadesActivasDerrota);
		        item3.appendChild(doc.createTextNode(valor3));
		        rootElement_rewrite.appendChild(item3);
		        
		        System.out.println("----------------------");
		        
		        System.out.println("numBrotesDerrota : "+ NumBrotesDerrota );
		        Element item4 = doc.createElement("numBrotesDerrota");
		        String valor4 = ModificarXML(NumBrotesDerrota);
		        item4.appendChild(doc.createTextNode(valor4));
		        rootElement_rewrite.appendChild(item4);
		        
		        TransformerFactory transformerFactory = TransformerFactory.newInstance();
		        Transformer transformer = transformerFactory.newTransformer();
		        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Define la cantidad de espacios para la sangría

		        DOMSource source = new DOMSource(rootElement_rewrite);
		        StreamResult result = new StreamResult("src//files//parametros.xml");

		        transformer.transform(source, result);
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
	}
	
	public static String ModificarXML(String valor) {
		while (true) {
            System.out.print("Ingrese el nuevo valor para " + valor + " o presione Enter para mantener el valor existente:");
            System.out.print("Ingrese el valor nuevo o presione Enter para mantener el valor existente (" + valor + "): ");

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

    static String nombreFichero = "src//files//ciudades.txt";
    static ArrayList<Ciudad> Ciudades = new ArrayList<>();
	public static ArrayList<Ciudad> cargarCiudades() {
	    try (FileReader fileReader = new FileReader(nombreFichero);
	         BufferedReader bufferedReader = new BufferedReader(fileReader);) {

	        String valor;
	        while ((valor = bufferedReader.readLine()) != null) {
	            String[] x = valor.split(";");
	            int[] cords = new int[2];
	            cords[0] = Integer.parseInt(x[2].split(",")[0]);
	            cords[1] = Integer.parseInt(x[2].split(",")[1]);
	            
	            String[] ciudadesColindantes = x[3].split(",");
	            
	            Ciudad ciudad = new Ciudad(x[0], cords, x[1], 0, ciudadesColindantes);
	            
	            Ciudades.add(ciudad);
	        }
	    } catch (IOException e) {
	        System.out.println("Ha habido un error al intentar leer los datos de Ciudades" + e);
	    }
	    
	    return Ciudades;
	}
	
	static ArrayList<Vacunas> Vacuna = new ArrayList<>();
	public static ArrayList<Vacunas> cargarVacunas() {
		Vacunas Vacuna1 = new Vacunas("Alfa", "Azul", 0);
		Vacunas Vacuna2 = new Vacunas("Beta", "Rojo", 0);
		Vacunas Vacuna3 = new Vacunas("Gama", "Verde", 0);
		Vacunas Vacuna4 = new Vacunas("Delta", "Amarillo", 0);
		
		Vacuna.add(Vacuna1);
		Vacuna.add(Vacuna2);
		Vacuna.add(Vacuna3);
		Vacuna.add(Vacuna4);
		
		for (Vacunas vacuna : Vacuna) {
			System.out.println(vacuna.getNombre());
			System.out.println(vacuna.getColor());
			System.out.println(vacuna.getPorcentaje());
		}
		return Vacuna;
	}

//	public static void calculo(int cityIndex, BufferedWriter bufferedWriter) throws IOException {
//	x1 = cord1.get(cityIndex);
//    y1 = cord2.get(cityIndex);
//    ciudades = array.get(cityIndex);
//    bufferedWriter.write("La ciudad "+ciudad.get(cityIndex)+" esta en las cordenadas ("+cord1.get(cityIndex)+","+cord2.get(cityIndex)+") sus ciudades colindantes son:\n");
//    	for (String ciudadColindante : ciudades) {
//            x2 = cord1.get(ciudad_2.get(ciudadColindante));
//            y2 = cord2.get(ciudad_2.get(ciudadColindante));
//            distancia = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
//            bufferedWriter.write(ciudadColindante+", que esta a una distancia de "+distancia);
//            bufferedWriter.newLine();
//		}
//}
	
	static String FicheroEnfermedades = "src//files//CCP.bin";
    static String FicheroCiudadEnfermedad = "src//files//ciudades-enfermedad.bin";
    static int contador = 0;
    static ArrayList<Virus> Virus = new ArrayList<>();
	public static ArrayList<Virus> cargarVirus() throws IOException {
		try (FileInputStream fileInputStream = new FileInputStream(FicheroEnfermedades);
		         DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {

				@SuppressWarnings("unused")
				String enfermedad_NO_USAR_NO_SIRVE = dataInputStream.readUTF();
		        while (contador != 4) {
		            int codigoEnfermedad = dataInputStream.readInt();
		            String nombreEnfermedad = dataInputStream.readUTF();
		            String colorEnfermedad = dataInputStream.readUTF();

		            Virus virus = new Virus(String.valueOf(codigoEnfermedad), nombreEnfermedad, colorEnfermedad);
		            Virus.add(virus);

		            contador++;
		        }
		    } catch (EOFException e1) {
		        System.out.println();
		    } catch (FileNotFoundException e) {
		        System.out.println("Fichero no encontrado " + e);
		    } catch (IOException e) {
		        System.out.println("Ha habido un error de lectura " + e);
		    }
		    return Virus;
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
