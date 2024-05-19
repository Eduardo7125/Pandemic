package data_managment;
/**
 * @author Eduardo y Marc
 */
public class Datos_partida {
	
	private int identificador;
    private String player;
    private int brotes;
    private int rondas;
    private int acciones;
    private String dificultad;


	public int getIdentificador() {
		return identificador;
	}


	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}


	public String getPlayer() {
		return player;
	}


	public void setPlayer(String player) {
		this.player = player;
	}


	public String getDificultad() {
		return dificultad;
	}


	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}


	public int getBrotes() {
		return brotes;
	}


	public void setBrotes(int brotes) {
		this.brotes = brotes;
	}


	public int getRondas() {
		return rondas;
	}


	public void setRondas(int rondas) {
		this.rondas = rondas;
	}


	public int getAcciones() {
		return acciones;
	}


	public void setAcciones(int acciones) {
		this.acciones = acciones;
	}
}
