import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Cap2_1 {
	private static Dictionary<Integer, String> ciudad = new Hashtable<Integer, String>();
	private static Dictionary<Integer, Integer> enfermedades = new Hashtable<Integer, Integer>();
	private static Dictionary<Integer, Integer> cord1 = new Hashtable<Integer, Integer>();
	private static Dictionary<Integer, Integer> cord2 = new Hashtable<Integer, Integer>();
	Map<String, Integer> ciudadReverse = new HashMap<>();
    private static String[] ciudades;
    private static int indice = 1;
    private static String nombreFichero = "ciudades.txt";
    
	public static void main(String[] args) {
		try (FileReader fileReader = new FileReader(nombreFichero);
                BufferedReader bufferedReader = new BufferedReader(fileReader);){
			 	String valor;
			 
	            while ((valor = bufferedReader.readLine()) != null) {
	                String[] x = valor.split(";");
	                for (int i = 1; i < x.length+1; i++) {
	                    if (i == 1) {
	                    	
	                    	ciudad.put(indice,x[0]);
	                    	
	                    } else if (i == 2) {
	                    	
	                    	enfermedades.put(indice,Integer.parseInt(x[1]));
	                    	
	                    } else if (i == 3) {
	                    	
	                        String[] parts = x[2].split(",");
	                        cord1.put(indice,Integer.parseInt(parts[0]));
	                        cord2.put(indice,Integer.parseInt(parts[1]));
	                        
	                    } else {
	                    	
	                        ciudades = x[i-1].split(",");
	                        
	                    }
	                }
                	indice++;
	            }
	            System.out.println(((Map<Integer, String>) cord1).keySet());
	            int i = 1;
	            while(ciudad.get(i) != null) {
	        		int cordenada1 = (Integer)cord1.get(i) - (Integer)cord1.get(ciudades[i]);
	        		int cordenada2 = (Integer)cord2.get(i) - (Integer)cord2.get(ciudades[i]);
	            	int distancia_al_cuadrado = (cordenada1^2) + (cordenada2^2);
	            	int distancia =	(int) Math.sqrt(distancia_al_cuadrado);  
	                System.out.println("La ciudad "+ciudad.get(i)+" esta en las cordenadas ("+cord1.get(i)+","+cord2.get(i)+") sus ciudades colindantes son:");
	                for (int j = 0; j < ciudades.length; j++) {
	                    System.out.println(ciudades[j]+", que esta a una distancia de ");
	        		}
	            	i++;
	            }
		} catch (IOException e) {
			System.out.println("Ha habido un error al intentar abrir el fichero" + e);
		}
	}
	
	public static void calculo() {
//		(300, 540)-(200,605) = (100,65)
//		int cordenada1 = cord1.get(i) - cord1.get(ciudades[i]);
//		int cordenada2 = cord2.get(i) - cord2.get(ciudades[i]);
//    	int distancia_al_cuadrado = (cordenada1^2) + (cordenada2^2);
//    	int distancia =	(int) Math.sqrt(distancia_al_cuadrado);  
//        String valor2 = "La ciudad "+ciudad+"esta en las cordenadas ("+cord1.get(i)+","+cord2.get(i)+") sus ciudades colindantes son:";
//        for (int j = 0; i < ciudades.length; i++) {
//            String valor3 = ciudades[j]+" que esta a una distancia de "+distancia;
//		}
	}
	
}

