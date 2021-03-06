package es.ucm.abd.crossword.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * Clase encargada de las funciones de base de datos para el crucigrama, hereda de {@link AbstractMapper}.
 * @author Alberto y George
 *
 */

public class CrucigramaMapper extends AbstractMapper<Crucigrama, Integer>{

	public CrucigramaMapper(DataSource ds) {
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
	public ArrayList<Integer> getCrucigramasByTitle(String filter) {
		String tableName = getTableName();
		ArrayList<Integer> idCruci = new ArrayList<Integer>();
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
	public List<Integer> getCricigramasActivosID(String nombre) {
		String tableName = "hace_crucigrama";
		//Obtenermos el id del usuario
		Integer IdUser = getIdByName(nombre,"usuarios","Nombre");
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
	
	/**
	 * Método encargado de obtener una lista con los crucigramas de un usuario.
	 * @param nombre -> nombre del usuario
	 * @return lista de crucigramas
	 */
	public ArrayList<Crucigrama> getCricigramasActivos(String nombre) {
		List<Integer> idCruci = getCricigramasActivosID(nombre);
		String tableName = getTableName();
		ArrayList<Crucigrama> listaCrucigramas = new ArrayList<Crucigrama>();
		for (int i = 0;i<idCruci.size();i++){
			String sql = "SELECT * FROM "+tableName+" WHERE `Id`=? ";
			try (Connection con = ds.getConnection();
				 PreparedStatement pst = con.prepareStatement(sql)) {
				
				pst.setObject(1, idCruci.get(i));
				
				try(ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						listaCrucigramas.add(buildObject(rs));
					} 
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return listaCrucigramas;
	}
		
	/**
	 * Método encargado de obtener de bd los crucigramas.
	 * @return lista de crucigramas
	 */
	public ArrayList<Crucigrama> getCricigramas() {
		String tableName = "crucigramas";
		String sql = "SELECT * FROM "+tableName;
		ArrayList<Crucigrama> listaCrucigramas = new ArrayList<Crucigrama>();
		try (Connection con = ds.getConnection();
			PreparedStatement pst = con.prepareStatement(sql)) {
		
			try(ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					listaCrucigramas.add(buildObject(rs));
				} 
				return listaCrucigramas;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Método encargado de devolver lista de crucigramas por id
	 * @param id
	 * @return lista crucigramas
	 */
	protected Crucigrama getCrucigramasById(Integer id){
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

	public ArrayList<Integer> getCrucigramasByEtiqueta(String filter) {
		// TODO Auto-generated method stub
		ArrayList<Integer> idCruci = new ArrayList<Integer>();
		String sql = "SELECT c.`Id` "
				+ " FROM crucigramas as c, palabras as p, etiquetas as e, tener_etiqueta te, contiene_palabra as cp "
				+ " WHERE c.`Id` = cp.`Id_crucigrama` and p.`Id`=te.`Id_palabra` and e.`Id`=te.`Id_etiqueta` and p.`Id`=cp.`Id_palabra` and e.`Descripcion` LIKE '%' ? '%'";
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

}
