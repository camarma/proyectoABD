package es.ucm.abd.crossword.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
/**
 * Clase abstracta encargada de generar los metodos que tiene en comun las tablas del modelo de la base de datos 
 * @author Alberto y George
 *
 * @param <T>
 * @param <K>
 */
public abstract class AbstractMapper<T, K> {
	
	protected DataSource ds;

	protected abstract String getTableName();

	protected abstract String[] getColumnNames();

	protected abstract String getKeyColumnName();

	protected abstract T buildObject(ResultSet rs) throws SQLException;

	public AbstractMapper(DataSource ds) {
		this.ds = ds;
	}

	/**
	 * Método encargado de hacer busquedas por id
	 * @param id -> id de la tabla
	 * @return un objeto construido con todos los datos de la tabla, y null en cc 
	 */
	public T findById(K id) {
		String tableName = getTableName();
		String[] columnNames = getColumnNames();
		String keyColumnName = getKeyColumnName();
		
		String sql = "SELECT " + StringUtils.join(columnNames, ", ") + " FROM "
				+ tableName + " WHERE "+ keyColumnName + " = ?";
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setObject(1, id);
			
			try(ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return buildObject(rs);
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Método encargado de devolver el id de la tabla de usuario por el nombre ya que nuestro identificador es un id y no un nick
	 * @param nick -> nombre de usuario
	 * @return el id, y null en cc
	 */
	public Integer getIdByName(String nick, String tablename, String columName){
		String sql = "SELECT `Id` FROM "+ tablename+" WHERE "+columName+" =?";
		try (Connection con = ds.getConnection();
				 PreparedStatement pst = con.prepareStatement(sql)) {
				
				pst.setObject(1, nick);
				
				try(ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						return rs.getInt("Id");
					} else {
						return null;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}
}
