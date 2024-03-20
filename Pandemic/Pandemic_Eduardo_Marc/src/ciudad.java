
public class ciudad {	
	int id;
	String nombre;
	int cord1;
	int cord2;
	int enfermedades;
    private String[] ciudadesColindantes;

	public ciudad(int id, String nombre, int cord1, int cord2, int enfermedades) {
		this.id = id;
		this.nombre = nombre;
		this.cord1 = cord1;
		this.cord2 = cord2;
		this.enfermedades = enfermedades;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getCord1() {
		return cord1;
	}
	
	public void setCord1(int cord1) {
		this.cord1 = cord1;
	}
	
	public int getCord2() {
		return cord2;
	}
	
	public void setCord2(int cord2) {
		this.cord2 = cord2;
	}
	
	public int getEnfermedades() {
		return enfermedades;
	}
	
	public void setEnfermedades(int enfermedades) {
		this.enfermedades = enfermedades;
	}
	
	public int getId(int id) {
		if (id == this.id) {
			return this.id;
		}
		return this.id;
	}
	
	public String getNombre(int id) {
		if (id == this.id) {
			return nombre;
		}
		return null;
	}
	
	public int getCord1(int id) {
		if (id == this.id) {
			return cord1;
		}
		return 0;
	}
	
	public int getCord2(int id) {
		if (id == this.id) {
			return cord2;
		}
		return 0;
	}
	
	public int getEnfermedades(int id) {
		if (id == this.id) {
			return enfermedades;
		}
		return 0;
	}
	
    public void setCiudadesColindantes(String[] ciudadesColindantes) {
        this.ciudadesColindantes = ciudadesColindantes;
    }
    
    public String[] getCiudadesColindantes() {
        return ciudadesColindantes;
    }
}
