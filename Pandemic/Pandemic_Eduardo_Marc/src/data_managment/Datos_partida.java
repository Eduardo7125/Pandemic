package data_managment;
import java.util.ArrayList;

import objects.Ciudad;
import objects.Vacunas;
import objects.Virus;

public class Datos_partida {
	
	private int id;
	private ArrayList<Ciudad> ciudades = new ArrayList<>();
	private ArrayList<Virus> virus = new ArrayList<>();
	private ArrayList<Vacunas> vacunas = new ArrayList<>();
	public int brotes = 0;
	public int rondas = 0;
	private int pDesarrollo = 0;
	public int acciones = 4;
	
	public void cargarDatos() {
		this.ciudades = Control_de_datos.cargarCiudades();
		this.virus = Control_de_datos.cargarVirus();
		this.vacunas = Control_de_datos.cargarVacunas();
	}
	
	public Datos_partida(int id, ArrayList<Ciudad> ciudades, ArrayList<Virus> virus, ArrayList<Vacunas> vacunas, int brotes, int rondas, int pDesarrollo, int acciones) {
		this.id = id;
		this.ciudades = ciudades;
		this.virus = virus;
		this.vacunas = vacunas;
		this.brotes = brotes;
		this.rondas = rondas;
		this.pDesarrollo = pDesarrollo;
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


	public int getpDesarrollo() {
		return pDesarrollo;
	}


	public void setpDesarrollo(int pDesarrolloAlfa) {
		this.pDesarrollo = pDesarrolloAlfa;
	}


	public int getAcciones() {
		return acciones;
	}


	public void setAcciones(int acciones) {
		this.acciones = acciones;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}