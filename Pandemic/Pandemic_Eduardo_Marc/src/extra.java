import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class extra {
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
    private static String nombreFichero_esc = "CiudadesRedactadas_v2.txt";
    private static ciudad Ciudad;
    
	public static void main(String[] args) {
		try (FileReader fileReader = new FileReader(nombreFichero);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
					FileWriter fileWriter = new FileWriter(nombreFichero_esc);
        				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);){
	            while ((valor = bufferedReader.readLine()) != null) {
	                String[] x = valor.split(";");               
                	
                    String[] parts = x[2].split(",");
                                        
                    ciudades = x[3].split(",");
                    
                	Ciudad = new ciudad(indice, x[0], Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(x[1]));

                	indice++;
	            }

		            for (int cityIndex : Ciudad) {
		                calculo(cityIndex, bufferedWriter);
		            }
		            
				} catch (IOException e) {
					System.out.println("Ha habido un error al intentar abrir el fichero" + e);
				}
	}
	
	public static void calculo(int cityIndex, BufferedWriter bufferedWriter) throws IOException {
	    	x1 = Ciudad.getCord1(cityIndex);
	        y1 = Ciudad.getCord2(cityIndex);
	        ciudades = array.get(cityIndex);
	        bufferedWriter.write("La ciudad "+Ciudad.getNombre(cityIndex)+" esta en las cordenadas ("+Ciudad.getCord1(cityIndex)+","+Ciudad.getCord2(cityIndex)+") sus ciudades colindantes son:\n");
	        	for (String ciudadColindante : ciudades) {
	                x2 = cord1.get(ciudad_2.get(ciudadColindante));
	                y2 = cord2.get(ciudad_2.get(ciudadColindante));
	                distancia = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	                bufferedWriter.write(ciudadColindante+", que esta a una distancia de "+distancia);
	                bufferedWriter.newLine();
				}
	}
}