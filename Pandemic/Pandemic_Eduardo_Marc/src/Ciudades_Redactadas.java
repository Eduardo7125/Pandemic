//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Ciudades_Redactadas {
//    private static Map<Integer, Ciudad> ciudades = new HashMap<>();
//    private static Map<String, Integer> ciudadIndex = new HashMap<>();
//    private static Map<Integer, Integer> cord1 = new HashMap<>();
//    private static Map<Integer, Integer> cord2 = new HashMap<>();
//    private static String nombreFichero = "ciudades.txt";
//    private static String nombreFichero_esc = "CiudadesRedactadas.txt";
//    private static String[] x;
//    private static int indice = 0;
//    private static String[] parts;
//    private static String[] ciudadesVecinas;
//    private static int xCoord;
//    private static int yCoord;
//    private static int[] coord = {xCoord, yCoord};
//    private static Ciudad ciudad;
//    
//    public static void main(String[] args) {
//        try (FileReader fileReader = new FileReader(nombreFichero);
//             BufferedReader bufferedReader = new BufferedReader(fileReader);
//             FileWriter fileWriter = new FileWriter(nombreFichero_esc);
//             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
//            String valor;
//            while ((valor = bufferedReader.readLine()) != null) {
//                x = valor.split(";");
//                parts = x[2].split(",");
//                ciudadesVecinas = x[3].split(",");
//                xCoord = Integer.parseInt(parts[0]);
//                yCoord = Integer.parseInt(parts[1]);
//                ciudad = new Ciudad(valor, coord, valor, indice, args)
//                ciudad = new Ciudad(indice, x[0], xCoord, yCoord, Integer.parseInt(x[1]));
//                Ciudad.setCiudadesColindantes(ciudadesVecinas);
//                ciudades.put(indice, ciudad);
//                ciudadIndex.put(x[0], indice);
//                cord1.put(indice, xCoord);
//                cord2.put(indice, yCoord);
//                indice++;
//            }
//
//            for (ciudad ciudad : ciudades.values()) {
//                calcularDistancias(ciudad, bufferedWriter);
//            }
//            System.out.println("Archivo creado correctamente!!!!");
//        } catch (IOException e) {
//            System.out.println("Ha habido un error al intentar abrir el fichero" + e);
//        }
//    }
//
//    public static void calcularDistancias(ciudad ciudad, BufferedWriter bufferedWriter) throws IOException {
//        bufferedWriter.write("La ciudad " + ciudad.getNombre() + " está en las coordenadas (" + ciudad.getCord1() + "," + ciudad.getCord2() + ") y sus ciudades colindantes son:\n");
//        for (String nombreCiudadColindante : ciudad.getCiudadesColindantes()) {
//            ciudad ciudadColindante = ciudades.get(ciudadIndex.get(nombreCiudadColindante));
//            double distancia = Math.sqrt(Math.pow(ciudadColindante.getCord1() - ciudad.getCord1(), 2) + Math.pow(ciudadColindante.getCord2() - ciudad.getCord2(), 2));
//            bufferedWriter.write(nombreCiudadColindante + ", que está a una distancia de " + distancia);
//            bufferedWriter.newLine();
//        }
//    }
//}