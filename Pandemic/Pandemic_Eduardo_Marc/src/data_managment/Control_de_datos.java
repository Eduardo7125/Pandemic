package data_managment;

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
import java.util.Arrays;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import objects.Ciudad;
import objects.Vacunas;
import objects.Virus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Control_de_datos {

	private static final String url = "jdbc:oracle:thin:@192.168.3.26:1521:xe";
	private static final String user = "DAM1_2324_PET_EDU";
	private static final String password = "X7565598R";
	public static Connection con;
	private static final String ficheroTxt = "src//files//ciudades.txt";
	private static final String ficheroBin = "src//files//CCP.bin";
	public static String ficheroXML = "src//files//parametrosFacil.xml";
	
    static int contador = 0;
	public static String CiudadesInfectadasInicio;
	public static String CiudadesInfectadasRonda;
	public static String EnfermedadesActivasDerrota;
	public static String NumBrotesDerrota;
	
    public static ArrayList<Ciudad> Ciudades = new ArrayList<>();
	static ArrayList<Vacunas> Vacuna = new ArrayList<>();
    static ArrayList<Virus> Virus = new ArrayList<>();
    
	private static Connection conectarBaseDatos() {
		Connection con = null;
	    try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        con = DriverManager.getConnection(url, user, password);
	        if (con != null) {
	            System.out.println("Conexión establecida correctamente.");
	        } else {
	            System.out.println("No se pudo establecer la conexión.");
	        }
	    } catch (ClassNotFoundException e) {
	        System.out.println("No se ha encontrado el driver " + e);
	    } catch (SQLException e) {
	        System.out.println("Error en las credenciales o en la URL " + e);
	    }
	    return con;
	}
	

	private static ArrayList<Ranking> select(Connection con) {
		String sql = "SELECT p.* FROM RANKING_PANDEMIC p";
		ArrayList<Ranking> p = new ArrayList<>();

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);

			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					int rondas = rs.getInt("NºRondas");
					String nombre = rs.getString("Nombre");
					Date fecha = rs.getDate("Fecha");
					int resultado = rs.getInt("Resultado");
					int puntuacion = rs.getInt("Puntuacion");
					Ranking rank = new Ranking(rondas, nombre, fecha, resultado, puntuacion);

					p.add(rank);				}
			} else {
				System.out.println("No he encontrado nada");
			}	

		} catch (SQLException e) {
			System.out.println("Ha habido un error al intentar leer los datos" + e);
		}
		
		return p;
	}
	
	public static ArrayList<Ciudad> cargarCiudades() {
	    try (FileReader fileReader = new FileReader(ficheroTxt);
	         BufferedReader bufferedReader = new BufferedReader(fileReader)) {

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
	
	public static ArrayList<Vacunas> cargarVacunas() {
		Vacunas Vacuna1 = new Vacunas("Alfa", "Azul", 0);
		Vacunas Vacuna2 = new Vacunas("Beta", "Rojo", 0);
		Vacunas Vacuna3 = new Vacunas("Gama", "Verde", 0);
		Vacunas Vacuna4 = new Vacunas("Delta", "Amarillo", 0);
		
		Vacuna.add(Vacuna1);
		Vacuna.add(Vacuna2);
		Vacuna.add(Vacuna3);
		Vacuna.add(Vacuna4);
		
		return Vacuna;
	}
	
	public static ArrayList<Virus> cargarVirus() {
		try (FileInputStream fileInputStream = new FileInputStream(ficheroBin);
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
	
	public static int[] cargarXML() {
//		if (e.getsource = dificil) {
//			ficheroXML = "src//files//parametrosDificil.xml";
//		}else if(e.getsource = medio){
//			ficheroXML = "src//files//parametrosMedio.xml";
//		}else if(e.getsource = facil){
//			ficheroXML = "src//files//parametrosFacil.xml";
//		}
		try {
		    File inputFile = new File(ficheroXML);
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(inputFile);
		    doc.getDocumentElement().normalize();

		    NodeList nodeCiudadesInfectadasInicio = doc.getElementsByTagName("numCiudadesInfectadasInicio");
		    NodeList nodeCiudadesInfectadasRonda = doc.getElementsByTagName("numCuidadesInfectadasRonda");
		    NodeList nodeEnfermedadesActivasDerrota = doc.getElementsByTagName("numEnfermedadesActivasDerrota");
		    NodeList nodeNumBrotesDerrota = doc.getElementsByTagName("numBrotesDerrota");

		    Node node1 = nodeCiudadesInfectadasInicio.item(0);
		    Node node2 = nodeCiudadesInfectadasRonda.item(0);
		    Node node3 = nodeEnfermedadesActivasDerrota.item(0);
		    Node node4 = nodeNumBrotesDerrota.item(0);

		    if (node1 != null && node2 != null && node3 != null && node4 != null) {
		    	CiudadesInfectadasInicio = node1.getTextContent();
		        CiudadesInfectadasRonda = node2.getTextContent();
		        EnfermedadesActivasDerrota = node3.getTextContent();
		        NumBrotesDerrota = node4.getTextContent();
		    }
		    return new int[]{Integer.parseInt(CiudadesInfectadasInicio),Integer.parseInt(CiudadesInfectadasInicio),
		    		Integer.parseInt(EnfermedadesActivasDerrota),Integer.parseInt(NumBrotesDerrota)};
		} catch (Exception e) {
			System.out.println("Ha habido un error al intentar leer los datos" + e);
		}
		return null;
		
	}
	
	public static void cargarPartida() {
		cargarRecord();
		cargarCiudades();
		cargarVacunas();
		cargarVirus();
		cargarXML();
	}

	public static void main(String []args) {
		cargarPartida();
		try (FileWriter fileWriter = new FileWriter("partida");
        		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){

		            for (Ciudad ciudades : Ciudades) {
		            	bufferedWriter.write(ciudades.getNombre()+"\n");
		            	bufferedWriter.write(Arrays.toString(ciudades.getCoordenadas())+"\n");
		            	bufferedWriter.write(ciudades.getEnfermedad()+"\n");
		            	bufferedWriter.write(ciudades.getInfeccion()+"\n");
		            	bufferedWriter.write(Arrays.toString(ciudades.getCiudadesColindantes()) +"\n");
		            }
		            bufferedWriter.newLine();
		            for (Vacunas vacuna : Vacuna) {
		            	bufferedWriter.write(vacuna.getNombre()+"\n");
		            	bufferedWriter.write(vacuna.getColor()+"\n");
		            	bufferedWriter.write(vacuna.getPorcentaje()+"\n");
		            }
		            bufferedWriter.newLine();
		            for (Virus virus : Virus) {
		            	bufferedWriter.write(virus.getIdentificador()+"\n");
		            	bufferedWriter.write(virus.getNombre()+"\n");
		            	bufferedWriter.write(virus.getColor()+"\n");
		            }	
				} catch (IOException e) {
					System.out.println("Ha habido un error al intentar abrir el fichero" + e);
				}
	}
	
	public static void cargarRecord() {
	    try {
	        con = conectarBaseDatos();
	    } finally {
	        if (con != null) {
	            try {
	                con.close();
	                System.out.println("Conexión cerrada correctamente.");
	            } catch (SQLException e) {
	                System.out.println("Error al cerrar la conexión " + e);
	            }
	        }
	    }
	}
	
	public static void guardarRecord() {

	}
}


class Ranking{
	int rondas;
	String nombre;
	Date fecha;
	int resultado;
	int puntuacion;
	public Ranking(int rondas, String nombre, Date fecha, int resultado, int puntuacion) {
		this.rondas = rondas;
		this.nombre = nombre;
		this.fecha = fecha;
		this.resultado = resultado;
		this.puntuacion = puntuacion;
	}
	
	public int getRondas() {
		return rondas;
	}
	
	public void setRondas(int rondas) {
		this.rondas = rondas;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public int getResultado() {
		return resultado;
	}
	
	public void setResultado(int resultado) {
		this.resultado = resultado;
	}
	
	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}
}