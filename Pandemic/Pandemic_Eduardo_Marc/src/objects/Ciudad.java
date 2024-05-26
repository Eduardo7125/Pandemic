package objects;

import data_managment.*;
import main.game;
import main.gameSAVE;

/**
 * En esta clase se gestionan las distintas variables que afectan a las ciudades.
 * @author Eduardo y Marc
 */
public class Ciudad {    

    private String nombre;
    private int[] coordenadas;
    private String enfermedad;
    private int infeccion;
    private String[] ciudadesColindantes;
    private boolean OutbreakHappened;

    /**
     * Constructor para crear ciudades.
     * 
     * @param nombre              Nombre de la ciudad.
     * @param coordenadas         Coordenadas de la ciudad.
     * @param enfermedad          Enfermedad que puede afectar a la ciudad.
     * @param infeccion           Nivel de infección de la ciudad.
     * @param ciudadesColindantes Nombres de las ciudades colindantes a esta ciudad.
     * @param OutbreakHappened    Variable para declarar si ha habido un brote.
     */
    public Ciudad(String nombre, int[] coordenadas, String enfermedad, int infeccion, String[] ciudadesColindantes, boolean OutbreakHappened) {
        this.nombre = nombre;
        this.coordenadas = coordenadas;
        this.enfermedad = enfermedad;
        this.infeccion = infeccion;
        this.ciudadesColindantes = ciudadesColindantes;
        this.OutbreakHappened = OutbreakHappened;
    }

    /**
     * Aumentar el nivel de infeccion de la ciudad.
     */
    public void aumentarInfeccion() {
        this.infeccion += 1;
        Control_de_partida.infectedcities++;
    }

    /**
     * Reducir el nivel de infeccion de la ciudad.
     */
    public void disminuirInfeccion() {
        if (this.infeccion > 0) {
            this.infeccion -= 1;
            Control_de_partida.acciones--;
            Control_de_partida.infectedcities--;
        }
    }

    /**
     * Reducir el nivel de infeccion de la ciudad a 0 al completar la vacuna.
     * 
     * @param identificador Identificador de la enfermedad.
     */
    public void disminuirInfeccionConVacuna() {
        Control_de_partida.acciones--;
        this.setInfeccion(0);
    }

    /**
     * Propagar la infeccion a las ciudades colindantes debido a un brote.
     */
    public void propagarInfeccion() {
        System.out.println("INFECTED CITIES BY THE OUTBREAK:");
        for (String colindantes : this.ciudadesColindantes) {
            Ciudad ciudad = obtenerCiudad(colindantes);
            if (!ciudad.getOutbreakHappened()) {
                ciudad.aumentarInfeccion();
                System.out.println("Name: " + ciudad.getNombre() + " | Virus: " + ciudad.getNombreEnfermedad() + " | Infection: " + ciudad.getInfeccion());
                game.actualizarEstadoCiudades();
                ciudad.setOutbreakHappened(true);
                if (ciudad.getInfeccion() > 3) {
    	            Control_de_partida.outbreak++;
    	            game.brotes();
    	            System.out.println("AN OUTBREAK IS HAPPENING");
                    ciudad.setInfeccion(3);
                    propagarInfeccion(ciudad);
                }
            }
        }
        System.out.println();
    }

    public void propagarInfeccion2() {
        System.out.println("INFECTED CITIES BY THE OUTBREAK:");
        for (String colindantes : this.ciudadesColindantes) {
            Ciudad ciudad = obtenerCiudad(colindantes);
            if (!ciudad.getOutbreakHappened()) {
                ciudad.aumentarInfeccion();
                System.out.println("Name: " + ciudad.getNombre() + " | Virus: " + ciudad.getNombreEnfermedad() + " | Infection: " + ciudad.getInfeccion());
                gameSAVE.actualizarEstadoCiudades();
                ciudad.setOutbreakHappened(true);
                if (ciudad.getInfeccion() > 3) {
    	            Control_de_partida.outbreak++;
    	            game.brotes();
    	            System.out.println("AN OUTBREAK IS HAPPENING");
                    ciudad.setInfeccion(3);
                    propagarInfeccion2(ciudad);
                }
            }
        }
        System.out.println();
    }
    /**
     * Propagar la infeccion a las ciudades colindantes de forma recursiva por si de un brote han habido otros.
     * 
     * @param ciudad Ciudad desde la que ha habido el brote.
     */
    public void propagarInfeccion(Ciudad ciudad) { 
        System.out.println("INFECTED CITIES BY THE OUTBREAK:");
        for (String colindantes : ciudad.getCiudadesColindantes()) {
            Ciudad ciudadColindante = obtenerCiudad(colindantes);
            if (!ciudadColindante.getOutbreakHappened()) {
                ciudadColindante.aumentarInfeccion();
                System.out.println("Name: " + ciudadColindante.getNombre() + " | Virus: " + ciudadColindante.getNombreEnfermedad() + " | Infection: " + ciudadColindante.getInfeccion());
                game.actualizarEstadoCiudades();
                ciudadColindante.setOutbreakHappened(true);
                if (ciudadColindante.getInfeccion() > 3) {
                    ciudadColindante.setInfeccion(3);
                    propagarInfeccion(ciudadColindante);
                }
            }
        }
    }
    
    public void propagarInfeccion2(Ciudad ciudad) { 
        System.out.println("INFECTED CITIES BY THE OUTBREAK:");
        for (String colindantes : ciudad.getCiudadesColindantes()) {
            Ciudad ciudadColindante = obtenerCiudad(colindantes);
            if (ciudadColindante.getOutbreakHappened() == false) {
                ciudadColindante.aumentarInfeccion();
                System.out.println("Name: " + ciudadColindante.getNombre() + " | Virus: " + ciudadColindante.getNombreEnfermedad() + " | Infection: " + ciudadColindante.getInfeccion());
                gameSAVE.actualizarEstadoCiudades();
                ciudadColindante.setOutbreakHappened(true);
                if (ciudadColindante.getInfeccion() > 3) {
                    ciudadColindante.setInfeccion(3);
                    propagarInfeccion2(ciudadColindante);
                }
            }
        }
    }

    /**
     * Restablecer el valor de infeccion de las ciudades.
     */
    public static void resetValues() {
        for (Ciudad ciudad : Control_de_datos.Ciudades) {
            ciudad.setInfeccion(0);
        }
    }

    /**
     * Obtiener ciudades colindantes.
     * 
     * @param ciudadColindante Nombre de la ciudad colindante.
     * @return Ciudad encontrada o null si no se ha encontrado nada.
     */
    public Ciudad obtenerCiudad(String ciudadColindante) {
        for (Ciudad ciudad : Control_de_datos.Ciudades) {
            if (ciudad.getNombre().equals(ciudadColindante)) {
                return ciudad;
            }
        }
        return null;
    }

    /**
     * Obtener el nombre de la ciudad.
     * 
     * @return El nombre de la ciudad.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establecer el nombre de la ciudad.
     * 
     * @param nombre El nuevo nombre de la ciudad.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtener las coordenadas de la ciudad.
     * 
     * @return Las coordenadas de la ciudad.
     */
    public int[] getCoordenadas() {
        return coordenadas;
    }

    /**
     * Establecer las coordenadas de la ciudad.
     * 
     * @param coordenadas Las nuevas coordenadas de la ciudad.
     */
    public void setCoordenadas(int[] coordenadas) {
        this.coordenadas = coordenadas;
    }

    /**
     * Obtener la enfermedad que afecta a la ciudad.
     * 
     * @return Enfermedad que afecta a la ciudad.
     */
    public String getEnfermedad() {
        return enfermedad;
    }

    /**
     * Obtener el nombre de la enfermedad que afecta a la ciudad.
     * 
     * @return Nombre de la enfermedad que afecta a la ciudad.
     */
    public String getNombreEnfermedad() {
        String numeroVirus = enfermedad;

        for (Virus virus : Control_de_datos.Virus) {
            if (virus.getIdentificador().equals(numeroVirus)) {
                return virus.getNombre();
            }
        }

        return "Virus desconocido";
    }

    /**
     * Establecer la enfermedad que afecta a la ciudad.
     * 
     * @param enfermedad Nueva enfermedad que afecta a la ciudad.
     */
    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    /**
     * Obtener el nivel de infeccion de la ciudad.
     * 
     * @return infeccion Nivel de infección de la ciudad.
     */
    public int getInfeccion() {
        return infeccion;
    }

    /**
     * Establecer el nivel de infeccion de la ciudad.
     * 
     * @param infeccion Nuevo nivel de infección de la ciudad.
     */
    public void setInfeccion(int infeccion) {
        this.infeccion = infeccion;
    }

    /**
     * Obtener las ciudades colindantes a esta ciudad.
     * 
     * @return ciudadesColindantes Ciudades colindantes de esta ciudad.
     */
    public String[] getCiudadesColindantes() {
        return ciudadesColindantes;
    }

    /**
     * Establecer las ciudades colindantes a esta ciudad.
     * 
     * @param ciudadesColindantes Nuevas ciudades colindantes de esta ciudad.
     */
    public void setCiudadesColindantes(String[] ciudadesColindantes) {
        this.ciudadesColindantes = ciudadesColindantes;
    }

    /**
     * Verificar si ha ocurrido un brote en la ciudad.
     * 
     * @return true si ha ocurrido un brote, false si no ha ocurrido.
     */
    public boolean getOutbreakHappened() {
        return OutbreakHappened;
    }

    /**
     * Establecer si ha ocurrido un brote en la ciudad.
     * 
     * @param outbreakHappened true si ha ocurrido un brote, false si no ha ocurrido.
     */
    public void setOutbreakHappened(boolean outbreakHappened) {
        OutbreakHappened = outbreakHappened;
    }
}
