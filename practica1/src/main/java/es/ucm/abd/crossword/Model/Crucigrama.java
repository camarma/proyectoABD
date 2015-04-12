package es.ucm.abd.crossword.Model;

import java.util.Date;

/**
 * Clase encargada obtener datos de crucigramas
 * @author Alberto y George
 *
 */

public class Crucigrama {
	private int id;
	private String titulo;
	private Date fecha;
	
	public Crucigrama(Integer id, String titulo, Date fecha) {
		this.id = id;
		this.titulo = titulo;
		this.fecha = fecha;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFechaNacimiento(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getId() {
		return id;
	}

	public String toString() {
		return "Crucigrama [id=" + id + ", titulo=" + titulo + ", fecha creacion="
				+ fecha + "]";
	}
}
