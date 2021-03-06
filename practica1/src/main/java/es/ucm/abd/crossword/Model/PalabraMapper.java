package es.ucm.abd.crossword.Model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class PalabraMapper extends AbstractMapper<Palabra, Integer>{

	public PalabraMapper(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return "palabras";
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return new String[] {
				getKeyColumnName(), "Secuencia", "Enunciado", "Pista"
		};
	}

	@Override
	protected String getKeyColumnName() {
		return "Id";
	}

	@Override
	protected Palabra buildObject(ResultSet rs) throws SQLException {
		Integer idPalabra = rs.getInt(getKeyColumnName());
		String secuencia = rs.getString("Secuencia");
		String enunciado = rs.getString("Enunciado");
		Blob pista = rs.getBlob("Pista");
		
		byte[] fotoBytes = null;
		if (pista != null) {
			fotoBytes = pista.getBytes(1, (int)pista.length());
		}
		
		return new Palabra(idPalabra, secuencia, enunciado, fotoBytes);
	}
	
	/**
	 * Método para generar objetos palabras con idPalabra, secuencia, enunciado, fotoBytes, posx, posy, orientacion para utilizarlo a la hora de montar un crucigrama
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	protected Palabra buildObject2(ResultSet rs) throws SQLException {
		Integer idPalabra = rs.getInt(getKeyColumnName());
		String secuencia = rs.getString("Secuencia");
		String enunciado = rs.getString("Enunciado");
		Blob pista = rs.getBlob("Pista");
		int posx = rs.getInt("Posicion_x");
		int posy = rs.getInt("Posicion_y");
		String orientacion = rs.getString("Orientacion");
		
		byte[] fotoBytes = null;
		if (pista != null) {
			fotoBytes = pista.getBytes(1, (int)pista.length());
		}
		
		return new Palabra(idPalabra, secuencia, enunciado, fotoBytes, posx, posy, orientacion);
	}
	
	/**
	 * Método encargado de obtener una lista de palabras de un crucigrama.
	 * @param title -> titulo del crucigrama
	 * @return lista de palabaras
	 */
	public ArrayList<Palabra> getIdPalabrasByCrucigrama(String title){
		//Obtenermos el id del usuario
		Integer IdCruci = getIdByName(title,"crucigramas","Titulo");
		ArrayList<Palabra> idPalabras = new ArrayList<Palabra>();
		String sql = "SELECT * FROM `contiene_palabra` as cp, `palabras` as p WHERE cp.`Id_palabra`= p.`Id` and cp.`Id_crucigrama`=?";
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setObject(1, IdCruci);
			
			try(ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					idPalabras.add(buildObject2(rs));
				} 
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return idPalabras;
	}
}
