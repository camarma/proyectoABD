package es.ucm.abd.crossword.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

/**
 * Clase encargada de las funciones de base de datos para el crucigrama, hereda de {@link AbstractMapper}.
 * @author Alberto y George
 *
 */

public class CrucigramaMapperTest extends AbstractMapper<Crucigrama, Integer>{

	public CrucigramaMapperTest(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return "crucigramas";
	}

	@Override
	protected String[] getColumnNames() {
		// TODO Auto-generated method stub
		return new String[] {
				getKeyColumnName(), "Titulo", "Fecha_creacion"
		};
	}

	@Override
	protected String getKeyColumnName() {
		return "Id";
	}

	@Override
	protected Crucigrama buildObject(ResultSet rs) throws SQLException {
		Integer idCrucigrama = rs.getInt(getKeyColumnName());
		String titulo = rs.getString("Titulo");
		Date fecha = rs.getDate("Fecha_creacion");

		return new Crucigrama(idCrucigrama, titulo, fecha);
	}
	
	/**
	 * Método encargado de obtener la lista de ids segun el filtro de busqueda por titulo
	 * @param filter -> la cadena de busqueda
	 * @return devuelve la lista de ids ,y null en cc
	 */
	public List<Integer> getCrucigramasByTitle(String filter) {
		String tableName = getTableName();
		List<Integer> idCruci = new ArrayList<Integer>();
		String sql = "SELECT `Id` FROM "+tableName+" WHERE `Titulo` LIKE '%' ? '%'";
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setObject(1, filter);
			
			try(ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					idCruci.add(rs.getInt("Id"));
				} 
				return idCruci;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Método encargado de devolver una lista de los ids de los crucigramas activos de un usuario
	 * @param nombre -> nombre del usuario
	 * @return devuelve la lista de ids ,y null en cc
	 */
	public List<Integer> getCricigramasActivos(String nombre) {
		String tableName = "hace_crucigrama";
		//Obtenermos el id del usuario
		Integer IdUser = getIdByName(nombre, "usuarios", "Nombre");
		List<Integer> idCruci = new ArrayList<Integer>();
		int activo = 1;
		String sql = "SELECT `Id_crucigrama` FROM "+tableName+" WHERE `Id_usuario`=? and `Activo`=? ";
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setObject(1, IdUser);
			pst.setObject(2, activo);
			
			try(ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					idCruci.add(rs.getInt("Id_crucigrama"));
				} 
				return idCruci;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
