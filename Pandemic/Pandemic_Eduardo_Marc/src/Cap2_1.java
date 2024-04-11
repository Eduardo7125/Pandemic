import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cap2_1 {
    private static Map<Integer, String> ciudad = new HashMap<>();
    private static Map<String, Integer> ciudad_2 = new HashMap<>();
    private static Map<Integer, Integer> enfermedades = new HashMap<>();
    private static Map<Integer, Integer> cord1 = new HashMap<>();
    private static Map<Integer, Integer> cord2 = new HashMap<>();
    private static ArrayList<String[]> array = new ArrayList<String[]>();
    private static String[] ciudades;
    private static int indice = 0;
    private static double x1;
    private static double y1;
    private static double x2;
    private static double y2;
    private static double distancia;
    private static String valor;
    private static String nombreFichero = "ciudades.txt";
    private static String nombreFichero_esc = "CiudadesRedactadas.txt";
    
	public static void main(String[] args) {
		try (FileReader fileReader = new FileReader(nombreFichero);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
					FileWriter fileWriter = new FileWriter(nombreFichero_esc);
        				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);){
	            while ((valor = bufferedReader.readLine()) != null) {
	                String[] x = valor.split(";");  
                	ciudad.put(indice,x[0]);
                	ciudad_2.put(x[0],indice);              	
                	enfermedades.put(indice,Integer.parseInt(x[1]));
                    String[] parts = x[2].split(",");
                    cord1.put(indice,Integer.parseInt(parts[0]));
                    cord2.put(indice,Integer.parseInt(parts[1]));        
                    ciudades = x[3].split(",");
                	array.add(ciudades);
                	indice++;
	            }

		            for (int cityIndex : ciudad.keySet()) {
		                calculo(cityIndex, bufferedWriter);
		            }
		            
				} catch (IOException e) {
					System.out.println("Ha habido un error al intentar abrir el fichero" + e);
				}
	            System.out.println("Archivo creado correctamente!!");
	}
	
	public static void calculo(int cityIndex, BufferedWriter bufferedWriter) throws IOException {
	    	x1 = cord1.get(cityIndex);
	        y1 = cord2.get(cityIndex);
	        ciudades = array.get(cityIndex);
	        bufferedWriter.write("La ciudad "+ciudad.get(cityIndex)+" esta en las cordenadas ("+cord1.get(cityIndex)+","+cord2.get(cityIndex)+") sus ciudades colindantes son:\n");
	        	for (String ciudadColindante : ciudades) {
	                x2 = cord1.get(ciudad_2.get(ciudadColindante));
	                y2 = cord2.get(ciudad_2.get(ciudadColindante));
	                distancia = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	                bufferedWriter.write(ciudadColindante+", que esta a una distancia de "+distancia);
	                bufferedWriter.newLine();
				}
	}
}