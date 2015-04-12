package es.ucm.abd.crossword.Model;
import java.util.ArrayList;
import java.util.Date;

/**
 * Clase encargada de obtener datos de un usuario
 * @author Alberto y George
 *
 */

public class Usuario {
	
	private Integer id;
	private String nombre;
	private String password;
	private Date fechaNacimiento;
	private byte[] avatar;
	private Integer id_usuario_responde;
	private boolean correcto;
	private Integer puntuacion;
	
	public Usuario(Integer id, String nombre, String password,
			byte[] avatar , Date fechaNacimiento) {
		this.id = id;
		this.nombre = nombre;
		this.password = password;
		this.avatar = avatar;
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public Usuario(Integer id_usuario,Integer id_usuario_responde, boolean correcto, Integer puntuacion) {
		this.id          		 = id_usuario;
		this.id_usuario_responde = id_usuario_responde;
		this.correcto			 = correcto;
		this.puntuacion 		 = puntuacion;
	}
	public Usuario() {

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
	
	public boolean isCorrecto() {
		return correcto;
	}

	public void setCorrecto(boolean correcto) {
		this.correcto = correcto;
	}
	
	public Integer getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(Integer puntuacion) {
		this.puntuacion = puntuacion;
	}
	
	public Integer getIdUsuarioResponde() {
		return id_usuario_responde;
	}

	
	public String toString() {
		return "Contacto [id=" + id + ", nombre=" + nombre + ", password="
				+ password + "]";
	}

	public String historial() {
		return "historial [id_usuario=" + id + ", id_usuario_responde=" + id_usuario_responde + ", correcto="
				+ correcto + " puntuacion= "+puntuacion+"]";
	}

	/**
	 * Método encargado de calcular la puntuacion de un usuario.
	 * @param historial lista de usuario
	 * @param respuestaAyuda lista de respuestas
	 * @return
	 */
	public Integer calculatePuntos(ArrayList<Usuario> historial,
			ArrayList<Usuario> respuestaAyuda) {
		// TODO Auto-generated method stub
		Integer puntuacion = 0;
		for(int i = 0;historial.size()>i;i++){
			if(historial.get(i).isCorrecto()){
				puntuacion += historial.get(i).getPuntuacion();
			}else{
				puntuacion -= 10;
			}
		}
		for(int i = 0;respuestaAyuda.size()>i;i++){
			if(respuestaAyuda.get(i).isCorrecto()){
				puntuacion += respuestaAyuda.get(i).getPuntuacion();
			}else{
				puntuacion -= 10;
			}
		}
		return puntuacion;
	}
}
