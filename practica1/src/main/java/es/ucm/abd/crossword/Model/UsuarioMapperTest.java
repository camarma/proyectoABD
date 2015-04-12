package es.ucm.abd.crossword.Model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.sql.DataSource;

/**
 * Clase encargada de que funcionen los test
 * @author Alberto y George
 *
 */
public class UsuarioMapperTest extends AbstractMapper<Usuario, Integer> {
	
	public UsuarioMapperTest(DataSource ds) {
		super(ds);
	}
	
	@Override
	protected Usuario buildObject(ResultSet rs) throws SQLException {
		Integer idTabla = rs.getInt(getKeyColumnName());
		String nombre = rs.getString("Nombre");
		String password = rs.getString("Password");
		Blob foto = rs.getBlob("Avatar");
		Date fecha = rs.getDate("Fecha_Nacimiento");
		
		byte[] fotoBytes = null;
		if (foto != null) {
			fotoBytes = foto.getBytes(1, (int)foto.length());
		}
		
		return new Usuario(idTabla, nombre, password, fotoBytes, fecha);
	}

	@Override
	protected String getKeyColumnName() {
		return "Id";
	}

	@Override
	protected String[] getColumnNames() {
		return new String[] {
				getKeyColumnName(), "Nombre", "Password", "Avatar" , "Fecha_Nacimiento"
		};
	}

	@Override
	protected String getTableName() {
		return "usuarios";
	}
	
	/**
	 * Método para insertar usuarios.
	 * @param nombre -> nombre del usuario
	 * @param password -> contraseña del usuario
	 * @return true si ha ido bien, false cc y mensaje.
	 */
	public String insertUsuario(String nombre, String password) {
		String mensaje="false:Error base de datos";
		String tableName = getTableName();
		String sql = "INSERT INTO " + "`"+tableName+"`" + " (`Nombre`, `Password`) VALUES " + "(?,?)";
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setObject(1,nombre);
			pst.setObject(2,password);
			try {
				pst.executeUpdate();
				return mensaje = "true:Usuario insertado correctamente";
			} catch (Exception e) {
				return mensaje = "false:El usuario ya existe";
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			return mensaje;
		}
	}
	
	/**
	 * Método para comprobar si existe el usuario o no
	 * @param nombre -> nombre del usuario
	 * @param password -> contraseña del usuario
	 * @return devuelve un mensaje que afirma si el usuario existe o no
	 */
	public String existUsuario(String nombre, String password) {
		String mensaje="false:Error base de datos";
		String tableName = getTableName();
		String sql = "SELECT * FROM "+ tableName + " WHERE `Nombre`=? and `Password`=?";
		try (Connection con = ds.getConnection();
			PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setObject(1,nombre);
			pst.setObject(2,password);
			try(ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return mensaje="logado:";
				} else {
					 return mensaje="false:El usuario introducido no existe";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			 return mensaje;
		}
	}
	
	/**
	 * Método que devuelve la contraseña del usuario solicitado
	 * @param nombre -> nombre del usuario
	 * @return la contraseña en caso que todo haya ido bien, null en cc
	 */
	public String getPassword(String nombre) {
		String tableName = getTableName();
		String sql = "SELECT `Password` FROM "+ tableName + " WHERE `Nombre`=?";
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setObject(1, nombre);
			
			try(ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return rs.getString("Password");
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
	 * Método para cambiar de contraseña
	 * @param nombre -> nombre del usuario
	 * @param password -> contraseña del usuario
	 * @return true si ha ido bien, false cc y mensaje
	 */
	public String updatePassword(String nombre, String password) {
		String mensaje="false:Error base de datos";
		String tableName = getTableName();
		String sql = "UPDATE "+ tableName + " SET `Password`=? WHERE `Nombre`=?";
		
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setObject(1,password);
			pst.setObject(2,nombre);
			try {
				pst.executeUpdate();
				return mensaje = "true:Contraseña restaurada de "+nombre;
			} catch (Exception e) {
				return mensaje = "false:Contraseña no restaurada";
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			return mensaje;
		}
	}
	
	/**
	 * Método para añdair un crucigrama
	 * @param nombre -> nombre del usuario
	 * @param IdCrucigrama -> id del crucigrama
	 * @return true en caso de que todo haya ido bien, false en cc y mensaje
	 */
	public String addCrucigrama(String nombre, Integer IdCrucigrama) {
		String mensaje="false:Error base de datos";
		String tableName = "hacer_crucigrama";
		Integer IdUser = getIdByName(nombre, "usuarios", "nombre");
		int activo = 1;
		String sql = "INSERT INTO " +tableName+ " (`Id_usuario`, `Id_crucigrama`, `Activo`) VALUES (?,?,?)";
		
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
				pst.setObject(1,IdUser);
				pst.setObject(2,IdCrucigrama);
				pst.setObject(3,activo);
			try {
				pst.executeUpdate();
				return mensaje = "true:Nuevo crucigrama añadido ";
			} catch (Exception e) {
				return mensaje = "false:Error al asignar nuevo crucigrama añadido";
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			return mensaje;
		}
	}
}

