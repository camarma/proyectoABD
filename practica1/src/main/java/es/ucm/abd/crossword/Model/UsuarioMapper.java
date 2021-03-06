package es.ucm.abd.crossword.Model;

import java.io.File;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;


/**
 * Clase encargada de las funciones de base de datos para el usuario, hereda de {@link AbstractMapper}.
 * @author Alberto y George
 *
 */
public class UsuarioMapper extends AbstractMapper<Usuario, Integer> {
	
	public UsuarioMapper(DataSource ds) {
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
	
	protected Usuario buildObject2(ResultSet rs) throws SQLException {
		Integer id_usuario = rs.getInt("Id_usuario");
		Integer id_usuario_responde = rs.getInt("Id_usuario_responde");
		boolean correcto = rs.getBoolean("Correcto");
		Integer puntuacion = rs.getInt("Puntuacion");

		return new Usuario(id_usuario, id_usuario_responde, correcto, puntuacion);
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
	 * @param password -> contrasena del usuario
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
	 * @param password -> contrasena del usuario
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
					return mensaje="true";
				} else {
					 return mensaje="false:El usuario introducido o contrasenas no son correctos";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			 return mensaje;
		}
	}
	
	/**
	 * Metodo encargado de obtener todos los datos del usuario a partir del id
	 * @param name -> nombre del usuario
	 * @return objeto usuario.
	 */
	public Usuario getUsuario(String nombre) {
		Integer IdUser = getIdByName(nombre,getTableName(),"Nombre");
		return findById(IdUser);
	}
	
	/**
	 * Método que devuelve la contrasena del usuario solicitado
	 * @param nombre -> nombre del usuario
	 * @return la contrasena en caso que todo haya ido bien, null en cc
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
	 * Método para cambiar de contrasena
	 * @param nombre -> nombre del usuario
	 * @param password -> contrasena del usuario
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
				return mensaje = "true:"+nombre+" tu contraseña ha sido actualizada correctamente.";
			} catch (Exception e) {
				return mensaje = "false:Contrasena no actualizada";
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			return mensaje;
		}
	}
	
	/**
	 * Método para andair un crucigrama
	 * @param nombre -> nombre del usuario
	 * @param IdCrucigrama -> id del crucigrama
	 * @return true en caso de que todo haya ido bien, false en cc y mensaje
	 */
	public String addCrucigrama(String titulo, String nombre) {
		String mensaje="false:Error base de datos";
		String tableName = "hace_crucigrama";
		Integer IdUser = getIdByName(nombre,getTableName(),"Nombre");
		Integer IdCruci = getIdByName(titulo,"crucigramas","Titulo");
		int activo = 1;
		String sql = "INSERT INTO " +tableName+ " (`Id_usuario`, `Id_crucigrama`, `Activo`) VALUES (?,?,?)";
		
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
				pst.setObject(1,IdUser);
				pst.setObject(2,IdCruci);
				pst.setObject(3,activo);
			try {
				pst.executeUpdate();
				return mensaje = "true";
			} catch (Exception e) {
				return mensaje = "false:Error al asignar nuevo crucigrama anadido";
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			return mensaje;
		}
	}
	

	/**
	 * Método encargado de obtener una lista con los usuarios filtrados.
	 * @param filter -> filtro de busqueda
	 * @param nameUser -> nombre del usuario
	 * @return lista de nombres de usuario
	 */
	public ArrayList<String> getUsuariosByfilter(String filter, String nameUser){
		String tableName = getTableName();
		ArrayList<String> nameUsers = new ArrayList<String>();
		String sql = "SELECT `Nombre` FROM "+tableName+" WHERE `Nombre` LIKE '%' ? '%' AND `Nombre` != ?";
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setObject(1, filter);
			pst.setObject(2, nameUser);
			
			try(ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					nameUsers.add(rs.getString("Nombre"));
				} 
				return nameUsers;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Método encargado asignar amistad.
	 * @param nameUsr -> filtro de busqueda
	 * @param nameAmigo -> nombre del usuario
	 * @return resultado de la operacion
	 */
	public String asignarAmistad(String nameUsr, String nameAmigo) {
		String mensaje="false:Error base de datos";
		String tableName = "amigo_de";
		Integer IdUser = getIdByName(nameUsr,getTableName(),"Nombre");
		Integer IdAmigo = getIdByName(nameAmigo,getTableName(),"Nombre");
		String sql = "INSERT INTO " +tableName+ " (`Id_usuario`, `Id_usuario_amigo`) VALUES (?,?)";
		
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
				pst.setObject(1,IdUser);
				pst.setObject(2,IdAmigo);
			try {
				pst.executeUpdate();
				acceptarAmistad(nameUsr, nameAmigo);
				return mensaje = "true";
			} catch (Exception e) {
				return mensaje = "false:El usuario "+nameAmigo+" ya esta en tu lista de amigos.";
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			return mensaje;
		}
	}
	
	/**
	 * Método encargado de aceptar amistad.
	 * @param nameUsr -> filtro de busqueda
	 * @param nameAmigo -> nombre del usuario
	 */
	private void acceptarAmistad(String nameUsr, String nameAmigo){
		String tableName = "amigo_de";
		Integer IdUser = getIdByName(nameUsr,getTableName(),"Nombre");
		Integer IdAmigo = getIdByName(nameAmigo,getTableName(),"Nombre");
		String sql = "INSERT INTO " +tableName+ " (`Id_usuario`, `Id_usuario_amigo`) VALUES (?,?)";
		
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
				pst.setObject(1,IdAmigo);	
				pst.setObject(2,IdUser);
			try {
				pst.executeUpdate();
			} catch (Exception e) {
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método encargado de devolver la lista de ids de amigos de un usuario
	 * @param idUsr
	 * @return lista de amigos de
	 */
	private ArrayList<Integer> getIdsAmigosDe(Integer idUsr) {
		String tableName = "amigo_de";
		ArrayList<Integer> listIdsAmigos = new ArrayList<Integer>();
		String sql = "SELECT `Id_usuario_amigo` FROM "+tableName+" WHERE `Id_usuario`= ?";
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setObject(1, idUsr);
			
			try(ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					listIdsAmigos.add(rs.getInt("Id_usuario_amigo"));
				} 
				return listIdsAmigos;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Método encargado de obtener una lista con los amigos de un usuario.
	 * @param nameUsr -> nombre del usuario
	 * @return lista de amigos
	 */
	public ArrayList<String> getAmigosDe(String nameUsr) {
		String tableName = getTableName();
		Integer idUser = getIdByName(nameUsr,tableName,"Nombre");
		ArrayList<String> listAmigosDe = new ArrayList<String>();
		ArrayList<Integer> listIdsAmigos = getIdsAmigosDe(idUser);
		for(int i=0; i<listIdsAmigos.size();i++){
			String sql = "SELECT `Nombre` FROM "+tableName+" WHERE `Id`= ?";
			try (Connection con = ds.getConnection();
				 PreparedStatement pst = con.prepareStatement(sql)) {
				
				pst.setObject(1, listIdsAmigos.get(i));
				
				try(ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						listAmigosDe.add(rs.getString("Nombre"));
					} 
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return listAmigosDe;
	}
	
	/**
	 * Método encargado de eliminar relacción de amistad.
	 * @param nameUser -> nombre del usuario
	 * @param nameAmigo -> nombre del amigo
	 * @return resultado de la operación
	 */
	public String eliminarAmistad(String nameUsr, String nameAmigo) {
		String mensaje="false:Error base de datos";
		String tableName = "amigo_de";
		Integer IdUser = getIdByName(nameUsr,getTableName(),"Nombre");
		Integer IdAmigo = getIdByName(nameAmigo,getTableName(),"Nombre");
		String sql = "DELETE FROM " +tableName+ " WHERE `Id_usuario`= ? AND `Id_usuario_amigo`= ?";
		
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
				pst.setObject(1,IdUser);
				pst.setObject(2,IdAmigo);
			try {
				pst.executeUpdate();
				eliminarAmigoMutuo(nameUsr,nameAmigo);
				return mensaje = "true";
			} catch (Exception e) {
				return mensaje = "false:Error al eliminar amigo";
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			return mensaje;
		}
	}
	
	/**
	 * Método encargado de eliminar relacción de amistad en el otro sentido.
	 * @param nameUser -> nombre del usuario
	 * @param nameAmigo -> nombre del amigo
	 * @return resultado de la operación
	 */
	private void eliminarAmigoMutuo(String nameUsr, String nameAmigo){
		String tableName = "amigo_de";
		Integer IdUser = getIdByName(nameUsr,getTableName(),"Nombre");
		Integer IdAmigo = getIdByName(nameAmigo,getTableName(),"Nombre");
		String sql = "DELETE FROM " +tableName+ " WHERE `Id_usuario`= ? AND `Id_usuario_amigo`= ?";
		
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
				pst.setObject(1,IdAmigo);
				pst.setObject(2,IdUser);
			try {
				pst.executeUpdate();
			} catch (Exception e) {
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Método encargado de modificar la fecha de nacimiento del usuario.
	 * @param name -> nombre del usuario
	 * @param edad -> fecha nueva
	 * @return resultado de la operacion
	 */
	public String updateEdad(String nombre, Date edad) {
		String mensaje="false:Error base de datos";
		String tableName = getTableName();
		String sql = "UPDATE "+ tableName + " SET `Fecha_nacimiento`=? WHERE `Nombre`=?";
		
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setObject(1,edad);
			pst.setObject(2,nombre);
			try {
				pst.executeUpdate();
				return mensaje = "true:"+nombre+" tu fecha de nacimiento ha sido actualizada correctamente.";
			} catch (Exception e) {
				return mensaje = "false:Fecha nacimiento no actualizada";
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			return mensaje;
		}
	}
	
	/**
	 * Método encargado de añadir las respuestas de los usuarios al historial.
	 * @param nameUsuario -> nombre del usuario
	 * @param userAyudado -> nombre usuario ayudado
	 * @param tituloCruci -> titulo del crucigrama
	 * @param IdPalabra -> identificador de la palabra
	 * @param respuesta -> respuesta dada
	 * @param correcto -> correcto o incorrecto
	 * @param fecha -> fecha de la respuesta
	 * return resultado de la operacion
	 */
	 public String addRespuesta(String nameUsuario,String userAyudado, String tituloCruci, int IdPalabra, String respuesta, boolean correcto, String fecha) {
	 // TODO Auto-generated method stub
		 String mensaje="false:Error base de datos";
		 String tableName = "historial";
		 Integer IdUser = getIdByName(nameUsuario,getTableName(),"Nombre");
		 Integer IdUserAyudado = getIdByName(userAyudado,getTableName(),"Nombre");
		 Integer IdCruci = getIdByName(tituloCruci,"crucigramas","Titulo");
		 String sql = "INSERT INTO " +tableName+ " (`Id_usuario`, `Id_crucigrama`, `Id_palabra` , `Fecha_respuesta` , `Respuesta` , `Id_usuario_responde` , `Correcto`) VALUES (?,?,?,?,?,?,?)";
		 
		 try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			 pst.setObject(1,IdUserAyudado);
			 pst.setObject(2,IdCruci);
			 pst.setObject(3,IdPalabra);
			 pst.setObject(4,fecha);
			 pst.setObject(5,respuesta);
			 pst.setObject(6,IdUser);
			 pst.setObject(7,correcto);
			 try {
				 pst.executeUpdate();
				 return mensaje = "true";
			 } catch (Exception e) {
				 return mensaje = "false:Error al asignar nuevo crucigrama anadido";
			 }
		 
		 } catch (SQLException e) {
			 e.printStackTrace();
			 return mensaje;
		 }
	 }

	/**
	 * Método encargado de obtener las respuestas correctas de un usuario a un crucigrama.
	 * @param nameUsuario -> nombre del usuario
	 * @param tituloCruci -> titulo del crucigrama
	 * @return lista de respuestas.
	 */
	 public ArrayList<String> getRespuestasCorrectas(String nameUsuario, String tituloCruci) {
		 String tableName = "historial";
		 Integer IdUser = getIdByName(nameUsuario,getTableName(),"Nombre");
		 Integer IdCruci = getIdByName(tituloCruci,"crucigramas","Titulo");
		 ArrayList<String> respuestasCorrectas = new ArrayList<String>();
		 String sql = "SELECT `Respuesta` FROM "+ tableName + " WHERE `Id_usuario`=? AND `Id_crucigrama`=? AND `Correcto`=1";
		 
		 try (Connection con = ds.getConnection();
		     PreparedStatement pst = con.prepareStatement(sql)) {
			 pst.setObject(1, IdUser);
			 pst.setObject(2, IdCruci);
		 
			 try(ResultSet rs = pst.executeQuery()) {
				 while (rs.next()) {
					 respuestasCorrectas.add(rs.getString("Respuesta"));
				 }
				 return respuestasCorrectas;
			 }
			 } catch (SQLException e) {
				 e.printStackTrace();
				 return null;
			 }
	 }
	 
	/**
	 * Método encargado de obtener una lista del usuario que ha respondido a sus crucigramas.
	 * @param name -> nombre del usuario
	 * @return lista del historial de usuario
	 */
	 public ArrayList<Usuario> getHistorialUsuario(String nameUsuario) {
		 Integer IdUser = getIdByName(nameUsuario,getTableName(),"Nombre");
		 ArrayList<Usuario> historial = new ArrayList<Usuario>();
		 String sql = "SELECT h.`Id_usuario`, h.`Id_usuario_responde`, h.`Correcto`, cp.`Puntuacion` FROM `historial` as h, `contiene_palabra` as cp WHERE h.`Id_crucigrama` = cp.`Id_crucigrama` and h.`Id_palabra` = cp.`Id_palabra` and h.`Id_usuario` = ?";
		 try (Connection con = ds.getConnection();
		     PreparedStatement pst = con.prepareStatement(sql)) {
			 pst.setObject(1, IdUser);
		 
			 try(ResultSet rs = pst.executeQuery()) {
				 while (rs.next()) {
					 historial.add(buildObject2(rs));
					} 
				 return historial;
			 }
			 } catch (SQLException e) {
				 e.printStackTrace();
				 return null;
			 }
	 }

	/**
	 * Método encargado de obtener la lista de usuario cuyas respuestas sean de ayuda
	 * @param name -> nombre del usuario
	 * @return puntuacion total
	 */
	public ArrayList<Usuario> getRespuestasAyuda(String name) {
		// TODO Auto-generated method stub
		Integer IdUser = getIdByName(name,getTableName(),"Nombre");
		 ArrayList<Usuario> respuestasAyuda = new ArrayList<Usuario>();
		 String sql = "SELECT h.`Id_usuario`, h.`Id_usuario_responde`, h.`Correcto`, cp.`Puntuacion` FROM `historial` as h, `contiene_palabra` as cp WHERE h.`Id_crucigrama` = cp.`Id_crucigrama` and h.`Id_palabra` = cp.`Id_palabra` and h.`Id_usuario_responde` = ? and h.`Id_usuario_responde` != h.`Id_usuario`";
		 try (Connection con = ds.getConnection();
		     PreparedStatement pst = con.prepareStatement(sql)) {
			 pst.setObject(1, IdUser);
		 
			 try(ResultSet rs = pst.executeQuery()) {
				 while (rs.next()) {
					 respuestasAyuda.add(buildObject2(rs));
					} 
				 return respuestasAyuda;
			 }
			 } catch (SQLException e) {
				 e.printStackTrace();
				 return null;
			 }
	}

	/**
	 * Método encargado realizar peticiones de ayuda
	 * @param nameUserAyudado -> nombre del usuario ayudado
	 * @param nameUserAyuda -> nombre usuario ayuda
	 * @param titulo -> titulo del crucigrama
	 * @return resultado de la operacion
	 */
	public String insertPeticion(String nameUserAyudado, String nameUserAyuda, String titulo) {
		 String mensaje="false:Error base de datos";
		 String tableName = "lista_peticiones";
		 Integer IdUserAyudado = getIdByName(nameUserAyudado,getTableName(),"Nombre");
		 Integer IdUserAyuda = getIdByName(nameUserAyuda,getTableName(),"Nombre");
		 Integer IdCruci = getIdByName(titulo,"crucigramas","Titulo");
		 String sql = "INSERT INTO "+ tableName +" (`Id_usuario`, `Id_crucigrama`, `Id_usuario_ayudado`) VALUES (?,?,?)";
		 
		 try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			 pst.setObject(1,IdUserAyuda);
			 pst.setObject(2,IdCruci);
			 pst.setObject(3,IdUserAyudado);
		
			 try {
				 pst.executeUpdate();
				 return mensaje = "true";
			 } catch (Exception e) {
				 return mensaje = "false:Error al asignar nuevo crucigrama anadido";
			 }
		 
		 } catch (SQLException e) {
			 e.printStackTrace();
			 return mensaje;
		 }
	}

	/**
	 * Método encargado de saber si el crucigrama seleccionado lo has mandado a ayudar o no
	 * @param nameUserAyudado -> nombre del usuario ayudado
	 * @param tituloCruci -> titulo del crucigrama
	 * @return si tienes alguno o no
	 */
	public Integer ayudas(String nameUserAyudado, String tituloCruci) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Integer IdUser = getIdByName(nameUserAyudado,getTableName(),"Nombre");
		Integer IdCruci = getIdByName(tituloCruci,"crucigramas","Titulo");
		Integer resultado=0;
		String sql = "SELECT COUNT(*) FROM `lista_peticiones` WHERE `Id_usuario_ayudado`=? and `Id_crucigrama`=? ";
		try (Connection con = ds.getConnection();
			PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, IdUser);
			pst.setObject(2, IdCruci);
			try(ResultSet rs = pst.executeQuery()) {
				 if (rs.next()) {
					 resultado = (rs.getInt("count(*)"));
				} 
				 return resultado;
			 }
			 } catch (SQLException e) {
				 e.printStackTrace();
				 return null;
			 }
	}

	/**
	 * Método encargado obtener lista de peticiones de ayuda
	 * @param nameUsuario -> nombre del usuario ayudado
	 * @return lista con el crucigrama y su usuario
	 */
	public ArrayList<String> listaIDS(String nameUsuario) {
		// TODO Auto-generated method stub
		Integer IdUser = getIdByName(nameUsuario,getTableName(),"Nombre");
		ArrayList<Integer> listaIDUsuarios = new ArrayList<Integer>();
		ArrayList<Integer> listaIDCrucigramas = new ArrayList<Integer>();
		ArrayList<String> listaIDPeticiones = new ArrayList<String>();
		String sql = "SELECT `Id_usuario_ayudado`,`Id_crucigrama` FROM `lista_peticiones` WHERE `Id_usuario`=?";
		try (Connection con = ds.getConnection();
			PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, IdUser);
			try(ResultSet rs = pst.executeQuery()) {
				 while (rs.next()) {
					 listaIDUsuarios.add(rs.getInt("Id_usuario_ayudado"));
					 listaIDCrucigramas.add(rs.getInt("Id_crucigrama"));		
				} 
				 
			 }
			 } catch (SQLException e) {
				 e.printStackTrace();
				 //return null;
			 }
		
		for(int i=0;i<listaIDUsuarios.size();i++){
			listaIDPeticiones.add(listaIDUsuarios.get(i)+":"+listaIDCrucigramas.get(i));
		}
		
		return obtenerValores(listaIDPeticiones);
		
	}

	/**
	 * Método encargado obtener titulo del crucigrama con id
	 * @param id -> id del crucigrama
	 * @return resultado de la operacion
	 */
	private String getCrucigramaById(Integer id) {
		// TODO Auto-generated method stub
		String tableName = "crucigramas";
		
		String sql = "SELECT `Titulo` FROM "+ tableName + " WHERE  `Id` = ?";
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setObject(1, id);
			
			try(ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return rs.getString("Titulo");
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
	 * Método encargado obtener nombre usuario con id
	 * @param id -> id del usuario
	 * @return resultado de la operacion
	 */
	private String getUserById(Integer id) {
		// TODO Auto-generated method stub
		String tableName = getTableName();
		
		String sql = "SELECT `Nombre` FROM "+ tableName + " WHERE  `Id` = ?";
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setObject(1, id);
			
			try(ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return rs.getString("Nombre");
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
	 * Método encargado de devolver un array con el usuario y el crucigrama que ha solicitado ayuda
	 * @param listaIDPeticiones -> lista de id de peticiones de usuario y crucigrama
	 * @return lista de usuario y crucigrama
	 */
	private ArrayList<String> obtenerValores(ArrayList<String> listaIDPeticiones){
		ArrayList<String> listaUsuarios = new ArrayList<String>();
		ArrayList<String> listaCrucigramas = new ArrayList<String>();
		ArrayList<String> listaPeticiones = new ArrayList<String>();
		for(int i=0;i<listaIDPeticiones.size();i++){
			String id_user = listaIDPeticiones.get(i).split(":")[0];
			listaUsuarios.add(getUserById(Integer.parseInt(id_user)));
			String id_cruci = listaIDPeticiones.get(i).split(":")[1];
			listaCrucigramas.add(getCrucigramaById(Integer.parseInt(id_cruci)));
			listaPeticiones.add(listaUsuarios.get(i)+":"+listaCrucigramas.get(i));
		}
		return listaPeticiones;
	}
	
	/**
	 * Método encargado de eliminar peticiones de ayuda.
	 * @param nameUsuario -> nombre del usuario
	 * @param titulo -> titulo del crucigrama
	 * @param usrAyudado -> nombre usuario ayudado
	 * @return resultado de la operacion
	 */
	public String EliminarPeticion(String nameUsuario, String titulo, String usrAyudado) {
		
		String mensaje="false:Error base de datos";
		String tableName = "lista_peticiones";
		Integer IdUser = getIdByName(nameUsuario,getTableName(),"Nombre");
		Integer IdAyudado = getIdByName(usrAyudado,getTableName(),"Nombre");
		Integer IdCruci = getIdByName(titulo, "crucigramas" , "Titulo");
		String sql = "DELETE FROM " +tableName+ " WHERE `Id_usuario`= ? AND `Id_usuario_ayudado`= ? AND `Id_crucigrama`= ?";
		
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
				pst.setObject(1,IdUser);
				pst.setObject(2,IdAyudado);
				pst.setObject(3,IdCruci);
			try {
				pst.executeUpdate();
				return mensaje = "true";
			} catch (Exception e) {
				return mensaje = "false:Error al eliminar amigo";
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			return mensaje;
		}
	}

	public String updateFoto(String name, byte[] byteAvatar) {
		// TODO Auto-generated method stub
		String mensaje="false:Error base de datos";
		String tableName = getTableName();
		String sql = "UPDATE "+ tableName + " SET `Avatar`=? WHERE `Nombre`=?";
		
		try (Connection con = ds.getConnection();
			 PreparedStatement pst = con.prepareStatement(sql)) {
			
			pst.setObject(1,byteAvatar);
			pst.setObject(2,name);
			try {
				pst.executeUpdate();
				return mensaje = "true:Datos actualizados";
			} catch (Exception e) {
				return mensaje = "false:Avatar no restaurada";
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
			return mensaje;
		}
	}
	 
}

