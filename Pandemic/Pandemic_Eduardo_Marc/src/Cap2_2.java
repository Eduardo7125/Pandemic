import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Cap2_2 {
    private static String FicheroEnfermedades = "CCP.bin";
    private static String FicheroCiudadEnfermedad = "ciudades-enfermedad.bin";
    private static String nombreFichero = "ciudades.txt";
    private static String enfermedades[] = new String[4];

    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream(FicheroEnfermedades);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            
            String enfermedad = dataInputStream.readUTF();
            System.out.println(enfermedad);
            System.out.println("");
            
            int contador = 0;
            
            while (contador != 4) {
            	int codigoEnfermedad = dataInputStream.readInt();
            	System.out.println("Código de la enfermedad: " + codigoEnfermedad);
            	
                String nombreEnfermedad = dataInputStream.readUTF();
                System.out.println("Nombre de la enfermedad: " + nombreEnfermedad);
                enfermedades[contador] = nombreEnfermedad;
                
                String colorEnfermedad = dataInputStream.readUTF();
                System.out.println("Color de la enfermedad: " + colorEnfermedad);
                
                System.out.println("");
                contador++;
            }
            
            contador = 0;
            
            while (contador != 4) {
                int X = dataInputStream.readInt();
                System.out.println("Coordenada X: " + X);
                
                int Y = dataInputStream.readInt();
                System.out.println("Coordenada Y: " + Y);

                System.out.println("");
                contador++;
            }

            dataInputStream.close();
            fileInputStream.close();
		}catch(EOFException e1) {
			System.out.println("Fichero le�do correctamente");
		} catch (FileNotFoundException e) {
			System.out.println("Ficheor no encontrado " + e);
		} catch (IOException e) {
			System.out.println("Ha habido un error de lectura " + e);
		}
        
        //parte2
        
        try {
            FileReader fileReader = new FileReader(nombreFichero);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String valor;
            String ciudad = null;
            int enfermedad = 0;
            while ((valor = bufferedReader.readLine()) != null) {
                String[] x = valor.split(";");
                for (int i = 0; i < x.length; i++) {
                    if (i == 0) {
                        ciudad = x[i];
                    } else if (i == 1) {
                    	enfermedad = Integer.parseInt(x[i]);
                    }
                    try {
                    	boolean primera = true;
            			FileWriter fileWriter = new FileWriter(FicheroCiudadEnfermedad, false);
            			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            			
            			if (primera == true) {
            				primera = false;
            			} else if (primera == false) { 
            			bufferedWriter.newLine();
            			}
            			
            			bufferedWriter.write(ciudad);
            			bufferedWriter.newLine();
            			
            			bufferedWriter.write(enfermedades[enfermedad]);
            			
            			bufferedWriter.close();
            			fileWriter.close();
                    } catch (IOException e) {
            			System.out.println("Ha habido un error de escritura: " + e);
            		}
                }
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Ha habido un error al intentar abrir el fichero" + e);
        }
		
		try {
            FileInputStream fileInputStream = new FileInputStream(FicheroEnfermedades);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            
            String información = dataInputStream.readUTF();
            System.out.println(información);
            System.out.println("");
            
            while (dataInputStream.available() > 0) {
                String nombreEnfermedad = dataInputStream.readUTF();
                System.out.println(nombreEnfermedad);
                
                int codigoEnfermedad = dataInputStream.readInt();
                System.out.println(codigoEnfermedad);
                System.out.println("");
            }
            
            dataInputStream.close();
            fileInputStream.close();
		}catch(EOFException e1) {
			System.out.println("Fichero le�do correctamente");
		} catch (FileNotFoundException e) {
			System.out.println("Ficheor no encontrado " + e);
		} catch (IOException e) {
			System.out.println("Ha habido un error de lectura " + e);
		}	
	}
}