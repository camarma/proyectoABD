package es.ucm.abd.crossword.Model;

/**
 * Clase encargada de obtener datos de palabra
 * @author Alberto y George
 *
 */

public class Palabra {
	private int id;
	private String secuencia;
	private String enunciado;
	private byte[] pista;
	private int posx;
	private int posy;
	private String orientacion;
	
	public Palabra(Integer id, String secuencia, String enunciado, byte[] pista) {
		this.id = id;
		this.secuencia = secuencia;
		this.enunciado = enunciado;
		this.pista = pista;
	}
	
	public Palabra(Integer id, String secuencia, String enunciado, byte[] pista, int posx, int posy, String orientacion) {
		this.id = id;
		this.secuencia = secuencia;
		this.enunciado = enunciado;
		this.pista = pista;
		this.posx = posx;
		this.posy = posy;
		this.orientacion = orientacion;
	}

	public String getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public byte[] getPista() {
		return pista;
	}

	public void setPista(byte[] pista) {
		this.pista = pista;
	}


	public void setPosx(int posx) {
		this.posx = posx;
	}
	public int getPosx() {
		return posx;
	}
	
	public void setPosy(int posy) {
		this.posy = posy;
	}
	public int getPosy() {
		return posy;
	}

	public void setOrientacion(String orientacion) {
		this.orientacion = orientacion;
	}
	public String getOrientacion() {
		return orientacion;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public String toString() {
		return "Palabra [id=" + id + ", Secuencia=" + secuencia + ", Enunciado="
				+ enunciado + ", Posx=" + posx + ", Posy=" + posy + ", Orientacion="+orientacion+"]";
	}
}
