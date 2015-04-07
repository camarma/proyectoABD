package es.ucm.abd.crossword.Model;
import java.util.Date;

/**
 * Clase encargada 
 * @author Alberto y George
 *
 */

public class Usuario {
	
	private Integer id;
	private String nombre;
	private String password;
	private Date fechaNacimiento;
	private byte[] avatar;
	
	public Usuario(Integer id, String nombre, String password,
			byte[] avatar , Date fechaNacimiento) {
		this.id = id;
		this.nombre = nombre;
		this.password = password;
		this.avatar = avatar;
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public byte[] getFoto() {
		return avatar;
	}

	public void setFoto(byte[] avatar) {
		this.avatar = avatar;
	}

	public Integer getId() {
		return id;
	}

	
	public String toString() {
		return "Contacto [id=" + id + ", nombre=" + nombre + ", password="
				+ password + "]";
	}

}
