import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Cap2_1 {
	private static Dictionary<Integer, String> ciudad = new Hashtable<Integer, String>();
	private static Dictionary<Integer, Integer> enfermedades = new Hashtable<Integer, Integer>();
	private static Dictionary<Integer, Integer> cord1 = new Hashtable<Integer, Integer>();
	private static Dictionary<Integer, Integer> cord2 = new Hashtable<Integer, Integer>();
	
    private static String[] ciudades;
    private static int indice = 1;
    private static String nombreFichero = "ciudades.txt";
    
	public static void main(String[] args) {
		try (FileReader fileReader = new FileReader(nombreFichero);
                BufferedReader bufferedReader = new BufferedReader(fileReader);){
			 	String valor;
			 
	            while ((valor = bufferedReader.readLine()) != null) {
	                String[] x = valor.split(";");
	                for (int i = 0; i < x.length; i++) {
	                    if (i == 0) {
	                    	
	                    	ciudad.put(indice,x[i]);
	                    	
	                    } else if (i == 1) {
	                    	
	                    	enfermedades.put(indice,Integer.parseInt(x[i]));
	                    	
	                    } else if (i == 2) {
	                    	
	                        String[] parts = x[i].split(",");
	                        cord1.put(indice,Integer.parseInt(parts[0]));
	                        cord2.put(indice,Integer.parseInt(parts[1]));
	                        
	                    } else {
	                    	
	                        ciudades = x[i].split(",");
	                        
	                    }
	                }
                	indice++;
	            }
	            int i = 1;
	            while(ciudad != null) {
		            System.out.println(ciudad.get(i));
		            if (ciudad == null) {
						return;
					}
		            System.out.println(enfermedades.get(i));
		            System.out.println(cord1.get(i));
		            System.out.println(cord2.get(i));
	            	i++;
	            }
		} catch (IOException e) {
			System.out.println("Ha habido un error al intentar abrir el fichero" + e);
		}
	}
	
	public static void calculo() {
//        //(300, 540)-(200,605) = (100,65)
//    	int distancia_al_cuadrado = (coordenadas[1]^2) + (coordenadas[2]^2);
//    	int distancia =	(int) Math.sqrt(distancia_al_cuadrado);  
//        String valor2 = "La ciudad "+ciudad+"esta en las cordenadas ("+coordenadas[1]+","+coordenadas[2]+") sus ciudades colindantes son:";
//        for (int i = 0; i < ciudades.length; i++) {
//            String valor3 = ciudades[i]+" que esta a una distancia de "+distancia;
//		}
	}
	
}

