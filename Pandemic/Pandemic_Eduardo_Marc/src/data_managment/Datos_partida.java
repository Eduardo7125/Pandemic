package data_managment;
import java.util.ArrayList;

import objects.Ciudad;
import objects.Vacunas;
import objects.Virus;

public class Datos_partida {
	
	private ArrayList<Ciudad> ciudades = new ArrayList<>();
	private ArrayList<Virus> virus = new ArrayList<>();
	private ArrayList<Vacunas> vacunas = new ArrayList<>();
	private int brotes = 0;
	private int rondas = 0;
	private float pDesarrolloAlfa = 0;
	private float pDesarrolloBeta = 0;
	private float pDesarrolloGama = 0;
	private float pDesarrolloDelta = 0;
	public int acciones = 4;
	
	public void cargarDatos() {
		this.ciudades = Control_de_datos.cargarCiudades();
		this.virus = Control_de_datos.cargarVirus();
		this.vacunas = Control_de_datos.cargarVacunas();
	}
	
	public Datos_partida(ArrayList<Ciudad> ciudades, ArrayList<Virus> virus, ArrayList<Vacunas> vacunas, int brotes,
			int rondas, float pDesarrolloAlfa, float pDesarrolloBeta, float pDesarrolloGama, float pDesarrolloDelta,
			int acciones) {
		this.ciudades = ciudades;
		this.virus = virus;
		this.vacunas = vacunas;
		this.brotes = brotes;
		this.rondas = rondas;
		this.pDesarrolloAlfa = pDesarrolloAlfa;
		this.pDesarrolloBeta = pDesarrolloBeta;
		this.pDesarrolloGama = pDesarrolloGama;
		this.pDesarrolloDelta = pDesarrolloDelta;
		this.acciones = acciones;
	}


	public ArrayList<Ciudad> getCiudades() {
		return ciudades;
	}


	public void setCiudades(ArrayList<Ciudad> ciudades) {
		this.ciudades = ciudades;
	}


	public ArrayList<Virus> getVirus() {
		return virus;
	}


	public void setVirus(ArrayList<Virus> virus) {
		this.virus = virus;
	}


	public ArrayList<Vacunas> getVacunas() {
		return vacunas;
	}


	public void setVacunas(ArrayList<Vacunas> vacunas) {
		this.vacunas = vacunas;
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


	public float getpDesarrolloAlfa() {
		return pDesarrolloAlfa;
	}


	public void setpDesarrolloAlfa(float pDesarrolloAlfa) {
		this.pDesarrolloAlfa = pDesarrolloAlfa;
	}


	public float getpDesarrolloBeta() {
		return pDesarrolloBeta;
	}


	public void setpDesarrolloBeta(float pDesarrolloBeta) {
		this.pDesarrolloBeta = pDesarrolloBeta;
	}


	public float getpDesarrolloGama() {
		return pDesarrolloGama;
	}


	public void setpDesarrolloGama(float pDesarrolloGama) {
		this.pDesarrolloGama = pDesarrolloGama;
	}


	public float getpDesarrolloDelta() {
		return pDesarrolloDelta;
	}


	public void setpDesarrolloDelta(float pDesarrolloDelta) {
		this.pDesarrolloDelta = pDesarrolloDelta;
	}


	public int getAcciones() {
		return acciones;
	}


	public void setAcciones(int acciones) {
		this.acciones = acciones;
	}

}