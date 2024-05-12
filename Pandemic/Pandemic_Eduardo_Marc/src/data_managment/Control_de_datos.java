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
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import objects.Ciudad;
import objects.Vacunas;
import objects.Virus;
import oracle.jdbc.OracleConnection;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;
import java.math.BigDecimal;

public class Control_de_datos {

	private static final String url = "jdbc:oracle:thin:@oracle.ilerna.com:1521:xe";
	private static final String user = "DAM1_2324_PET_EDU";
	private static final String password = "edu";
	public static Connection con = conectarBaseDatos();
	private static final String ficheroTxt = "src//files//ciudades.txt";
	private static final String ficheroBin = "src//files//CCP.bin";
	public static String ficheroXML = "src//files//parametrosMedio.xml";
	
    static int contador = 0;
	public static String CiudadesInfectadasInicio;
	public static String CiudadesInfectadasRonda;
	public static String EnfermedadesActivasDerrota;
	public static String NumBrotesDerrota;
	
    public static ArrayList<Ciudad> Ciudades = new ArrayList<>();
    public static ArrayList<Vacunas> Vacuna = new ArrayList<>();
    public static ArrayList<Virus> Virus = new ArrayList<>();
    
    
	public static String[] RankingNames = new String[10];
	public static int[] RankingRounds = new int[10];
	public static Date[] RankingDates = new Date[10];
	public static String[] RankingResult = new String[10];
    
	public static Connection conectarBaseDatos() {
		con = null;
	    try {
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        con = DriverManager.getConnection(url, user, password);
	        if (con != null) {
	            System.out.println("Connection established successfully.");
	        } else {
	            System.out.println("The connection could not be established.");
	        }
	    } catch (ClassNotFoundException e) {
	        System.out.println("The driver not found " + e);
	    } catch (SQLException e) {
	        System.out.println("Error in credentials or URL " + e);
	    }
	    return con;
	}
	
    public static boolean isConnected() {
        return con != null;
    }
    
    public static void disconnect() {
        if (con != null) {
            try {
            	con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            con = null;
        }
    }
    
	private static void insertarPartida() {
		try {
			OracleConnection oracleConn = (OracleConnection) con;
            Struct[] ciudadStructs = new Struct[Ciudades.size()];
            for (int i = 0; i < Ciudades.size(); i++) {
                Ciudad ciudad = Ciudades.get(i);
                Object[] ciudadAttributes = new Object[] {
                    ciudad.getNombre(),
                    new Object[] { ciudad.getCoordenadas()[0], ciudad.getCoordenadas()[1] },
                    ciudad.getEnfermedad(),
                    ciudad.getInfeccion(),
                    Arrays.toString(ciudad.getCiudadesColindantes())
                };
                ciudadStructs[i] = oracleConn.createStruct("CIUDAD", ciudadAttributes);
            }

            Array ciudadArray = oracleConn.createOracleArray("ARRAY_CIUDADES_OBJ", ciudadStructs);

            Struct[] virusStructs = new Struct[Virus.size()];
            for (int i = 0; i < Virus.size(); i++) {
                Virus virus = Virus.get(i);
                Object[] virusAttributes = new Object[] {
                    virus.getIdentificador(),
                    virus.getNombre(),
                    virus.getColor()
                };
                virusStructs[i] = oracleConn.createStruct("VIRUS", virusAttributes);
            }

            Array virusArray = oracleConn.createOracleArray("ARRAY_VIRUS_OBJ", virusStructs);

            Struct[] vacunaStructs = new Struct[ Vacuna.size()];
            for (int i = 0; i < Vacuna.size(); i++) {
                Vacunas vacuna = Vacuna.get(i);
                Object[] vacunaAttributes = new Object[] {
                    vacuna.getNombre(),
                    vacuna.getColor(),
                    vacuna.getPorcentaje()
                };
                vacunaStructs[i] = oracleConn.createStruct("VACUNAS", vacunaAttributes);
            }

            Array vacunasArray = oracleConn.createOracleArray("ARRAY_VACUNAS_OBJ", vacunaStructs);

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO PANDEMIC_SAVEFILES (identificador, ciudades, virus, vacunas, brotes, rondas, p_desarrollo, acciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setObject(1, null);
            pstmt.setArray(2, ciudadArray);
            pstmt.setArray(3, virusArray);
            pstmt.setArray(4, vacunasArray);
            pstmt.setInt(5, Control_de_partida.outbreak);
            pstmt.setInt(6, Control_de_partida.turno);
            pstmt.setInt(7, 25);
            pstmt.setInt(8, Control_de_partida.acciones);

            pstmt.executeUpdate();
            oracleConn.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private static void selectDatos() {
		try {
	        PreparedStatement pstmt = con.prepareStatement("SELECT ciudades, virus, vacunas, brotes, rondas, p_desarrollo, acciones FROM PANDEMIC_SAVEFILES WHERE identificador =?");
	        pstmt.setObject(1, 1);
	        ResultSet rs = pstmt.executeQuery();
	        
	        
	        if (rs.next()) {
	            Array ciudadArray = (Array) rs.getArray(1);
	            Object[] ciudadObjects = (Object[]) ciudadArray.getArray();
	            Ciudades.clear();
	            for (Object ciudadObject : ciudadObjects) {
	                Struct ciudadStruct = (Struct) ciudadObject;
	                Object[] ciudadAttributes = ciudadStruct.getAttributes();
	                Struct ciudadAttributesOracleObject = (Struct) ciudadAttributes[1];
	                Object[] coordenadasAttributes = ciudadAttributesOracleObject.getAttributes();
	                int[] ciudadAttributesIntArray = new int[2];
	                ciudadAttributesIntArray[0] = ((BigDecimal) coordenadasAttributes[0]).intValue();
	                ciudadAttributesIntArray[1] = ((BigDecimal) coordenadasAttributes[1]).intValue();
	                int infeccionAttribute = ((BigDecimal) ciudadAttributes[3]).intValue();
	                String[] colindantesAttribute = ((String) ciudadAttributes[4]).split(", ");
	                Ciudad ciudad = new Ciudad(
	                    (String) ciudadAttributes[0],
	                    ciudadAttributesIntArray,
	                    (String) ciudadAttributes[2],
	                    infeccionAttribute,
	                    colindantesAttribute,
	                	false	
	                );
	                Ciudades.add(ciudad);
	            }
	            
	            
	            Array virusArray = (Array) rs.getArray(2);
	            Object[] virusObjects = (Object[]) virusArray.getArray();
	            Virus.clear();
	            for (Object virusObject : virusObjects) {
	            	Struct virusStruct = (Struct) virusObject;
	                Object[] virusAttributes = virusStruct.getAttributes();
	                Virus virus = new Virus(
	                    (String) virusAttributes[0],
	                    (String) virusAttributes[1],
	                    (String) virusAttributes[2]
	                );
	                Virus.add(virus);
	            }
	            
	            
	            Array vacunaArray = (Array) rs.getArray(3);
	            Object[] vacunaObjects = (Object[]) vacunaArray.getArray();
	            Vacuna.clear();
	            for (Object vacunaObject : vacunaObjects) {
	            	Struct vacunaStruct = (Struct) vacunaObject;
	                Object[] vacunaAttributes = vacunaStruct.getAttributes();
	                int porcentajeAttribute = ((BigDecimal) vacunaAttributes[2]).intValue();
	                Vacunas vacuna = new Vacunas(
	                    (String) vacunaAttributes[0],
	                    (String) vacunaAttributes[1],
	                    porcentajeAttribute
	                );
	                Vacuna.add(vacuna);
	            }
	            
	            Control_de_partida.outbreak = rs.getInt(4);
	            Control_de_partida.turno = rs.getInt(5);
	            Control_de_partida.acciones = rs.getInt(7);

	        }
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private static void insertarRanking(){
        try{
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO PANDEMIC_RANKING (identificador, rondas, nombre, fecha, resultado) VALUES (?, ?, ?, SYSDATE, ?)");
            pstmt.setObject(1, null);
            pstmt.setInt(2, Control_de_partida.turno);
            pstmt.setString(3, Control_de_partida.playername);
            pstmt.setString(4, Control_de_partida.resultado);

            pstmt.executeUpdate();
            con.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void selectRanking(){
		try {
	        PreparedStatement pstmt = con.prepareStatement("SELECT rondas, nombre, fecha, resultado FROM PANDEMIC_RANKING WHERE ROWNUM <= 10 AND resultado LIKE 'Victory' ORDER BY rondas ASC , fecha ASC");
	        ResultSet rs = pstmt.executeQuery();
	        
	        int i = 0;
	        while (rs.next()) {
	            RankingRounds[i] = rs.getInt("rondas");
	            RankingNames[i] = rs.getString("nombre");
	            RankingDates[i] = rs.getDate("fecha");
	            RankingResult[i] = rs.getString("resultado");
	            i++;
	        }
		}catch (Exception e) {
			// TODO: handle exception
		}
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
	            
	            boolean OutbreakHappened = false;
	            
	            Ciudad ciudad = new Ciudad(x[0], cords, x[1], 0, ciudadesColindantes, OutbreakHappened);
	            
	            Ciudades.add(ciudad);
	        }
	    } catch (IOException e) {
	        System.out.println("There was an error while trying to read the data for Cities " + e);
	    }
	    
	    return Ciudades;
	}
	public static Vacunas Vacuna1;
	public static Vacunas Vacuna2;
	public static Vacunas Vacuna3;
	public static Vacunas Vacuna4;
	public static ArrayList<Vacunas> cargarVacunas() {
		Vacuna1 = new Vacunas("Alfa", "Azul", 0);
		Vacuna2 = new Vacunas("Beta", "Rojo", 0);
		Vacuna3 = new Vacunas("Gama", "Verde", 0);
		Vacuna4 = new Vacunas("Delta", "Amarillo", 0);
		
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
		        System.out.println("File not found " + e);
		    } catch (IOException e) {
		        System.out.println("There was a reading error " + e);
		    }
		    return Virus;
	}
	
	public static int[] cargarXML() {
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
			System.out.println("There was an error while trying to read the data " + e);
		}
		return null;
		
	}
	
	public static void cargarPartida() {
		Ciudades.clear();
        Vacuna.clear();
//		cargarRecord();
		cargarCiudades();
		cargarVacunas();
		cargarXML();
	}

	public static void main(String []args) {
		cargarPartida();

//		insertarPartida(con);
		
//		selectDatos(con);
		
//		insertarRanking(con);
		
		selectRanking();
		
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