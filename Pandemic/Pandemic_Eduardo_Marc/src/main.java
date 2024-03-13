import java.util.Scanner;

public class main {
	private static Scanner scan = new Scanner(System.in);
	private static String ciudad;
	private static int[] coordenadas = new int[3];
	private static String[] ciudades;
	
	public static void main(String[] args) {
		
		String valor = scan.nextLine();
//		San Francisco;0;235,315;Chicago,Los Angeles,Manila,Tokio;
		
		String[] x = valor.split(";");
		for (int i = 0; i < x.length; i++) {
			if(i == 0) {
				ciudad = x[i];
			} else if(i == 1) {
				coordenadas[0] = Integer.parseInt(x[i]);
			}else if(i == 2) {
				 String[] parts = x[i].split(",");
	                coordenadas[1] = Integer.parseInt(parts[0]);
	                coordenadas[2] = Integer.parseInt(parts[1]);
			} else {
				ciudades = x[i].split(",");
			}
		}
		System.out.println("Silco actúa en " + ciudad +" con los números "
		+ coordenadas[0] +", "+ coordenadas[1] +" y "+ coordenadas[2] + " y cuyas ciudades "
				+ "colindantes son " + ciudades[0]+", "+ ciudades[1]+", "+ ciudades[2]+" y "+ ciudades[3]);
//		try {
//			while() {
//				
//			}
//		}catch (Exception e) {
//
//		}
	}
	
	public static int valor() {
		
		
		return 0;
	}
}
