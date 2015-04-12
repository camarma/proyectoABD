package es.ucm.abd.crossword.Model;

import es.ucm.abd.crossword.WordModel;
/**
 * Clase encargada implementar el WordModel panel para montar las palabras en los crucigramas
 * @author Alberto y George
 *
 */
public class ContienePalabra implements WordModel{
	private int x;
	private int y;
	private String word;
	private boolean horizontal;
	private String enunciado;
	private byte[] pista;
	

	public ContienePalabra(int x, int y, String word, boolean horizontal, String enunciado, byte[] pista) {
		this.x = x;
		this.y = y;
		this.word = word;
		this.horizontal = horizontal;
		this.enunciado = enunciado;
		this.pista = pista;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public String getWord() {
		return this.word;
	}
	
	public String getEnunciado() {
		return this.enunciado;
	}
	
	public byte[] getFoto() {
		return pista;
	}

	public boolean isHorizontal() {
		return this.horizontal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (horizontal ? 1231 : 1237);
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContienePalabra other = (ContienePalabra) obj;
		if (horizontal != other.horizontal)
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
