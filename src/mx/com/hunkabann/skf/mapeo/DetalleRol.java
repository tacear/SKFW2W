package mx.com.hunkabann.skf.mapeo;


public class DetalleRol {
	
	private int id;
	
	private String clave;
	private String nombre;
	private String descripcion;
	
	
	
	public DetalleRol() {
		super();

	}


	public DetalleRol(int id,String clave, String nombre, String descripcion) {
		super();
		this.id = id;
		this.clave = clave;
		this.nombre = nombre;
		this.descripcion = descripcion;
		
		
	}

	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getClave() {
		return clave;
	}


	public void setClave(String clave) {
		this.clave = clave;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	}
