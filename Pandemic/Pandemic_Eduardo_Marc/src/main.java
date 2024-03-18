import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class main {
    private static String ciudad;
    private static int[] coordenadas = new int[3];
    private static String[] ciudades;
    private static String nombreFichero = "ciudades.txt";

    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader(nombreFichero);
                BufferedReader bufferedReader = new BufferedReader(fileReader);){

            String valor;
            while ((valor = bufferedReader.readLine()) != null) {
                String[] x = valor.split(";");
                for (int i = 0; i < x.length; i++) {
                    if (i == 0) {
                        ciudad = x[i];
                    } else if (i == 1) {
                        coordenadas[0] = Integer.parseInt(x[i]);
                    } else if (i == 2) {
                        String[] parts = x[i].split(",");
                        coordenadas[1] = Integer.parseInt(parts[0]);
                        coordenadas[2] = Integer.parseInt(parts[1]);
                    } else {
                        ciudades = x[i].split(",");
                    }
                }

                String valor2 = "Silco actúa en " + ciudad + " con los números "
                        + coordenadas[0] + ", " + coordenadas[1] + " y " + coordenadas[2] + " y cuyas ciudades "
                        + "colindantes son ";

                for (int i = 0; i < ciudades.length - 1; i++) {
                    valor2 += ciudades[i];
                    if (i < ciudades.length - 2) {
                        valor2 += ", ";
                    }
                }

                valor2 += " y " + ciudades[ciudades.length - 1];
                System.out.println(valor2);
            }
        } catch (IOException e) {
            System.out.println("Ha habido un error al intentar abrir el fichero" + e);
        }
    }
}
