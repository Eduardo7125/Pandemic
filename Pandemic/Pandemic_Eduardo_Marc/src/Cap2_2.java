import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
			System.out.println("Fichero leído correctamente");
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado " + e);
		} catch (IOException e) {
			System.out.println("Ha habido un error de lectura " + e);
		}
        
        try {
            FileReader fileReader = new FileReader(nombreFichero);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileOutputStream fileOutputStream = new FileOutputStream(FicheroCiudadEnfermedad, false);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
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
                    	i = x.length+1;
                    }
                }
                try {
                	dataOutputStream.writeUTF(ciudad);
                    dataOutputStream.writeUTF(enfermedades[enfermedad]);
                } catch (IOException e) {
                    System.out.println("Ha habido un error de escritura: " + e);
                }
            }
            
            System.out.println("");
            System.out.println("Fichero creado correctamente");
            
            dataOutputStream.close();
            fileOutputStream.close();
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e1) {
            System.out.println("Fichero no encontrado " + e1); 
        } catch (IOException e) {
        	System.out.println("Ha habido un error al intentar abrir el fichero" + e);
        }
		
        try {
            FileInputStream fileInputStream = new FileInputStream(FicheroCiudadEnfermedad);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            
            while (dataInputStream.available() > 0) {
            	String Ciudad = dataInputStream.readUTF();
                String Enfermedad = dataInputStream.readUTF();
                
                System.out.println("");
                
                System.out.println("Ciudad: " + Ciudad);
                System.out.println("Enfermedad: " + Enfermedad);
            }
            dataInputStream.close();
            fileInputStream.close();
		}catch(EOFException e1) {
			System.out.println("Fichero leído correctamente");
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado " + e);
		} catch (IOException e) {
			System.out.println("Ha habido un error de lectura " + e);
		}	
	}
}