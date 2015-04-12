package es.ucm.abd.crossword.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import es.ucm.abd.crossword.Model.Crucigrama;
import es.ucm.abd.crossword.Model.CrucigramaMapper;
import es.ucm.abd.crossword.Model.MyConnection;
import es.ucm.abd.crossword.Model.Palabra;
import es.ucm.abd.crossword.Model.PalabraMapper;
import es.ucm.abd.crossword.Model.Usuario;
import es.ucm.abd.crossword.Model.UsuarioMapper;

/**
 * Clase encargada de intermediar entre la logica de la aplicación y la vista
 * @author Alberto y George
 *
 */
public class Controller {
	MyConnection conn;
	DataSource ds;
	Usuario usr;
	public Controller(){
		conn = new MyConnection();
		usr = new Usuario();
		ds = conn.getDS();
	}
	
	/**
	 * Mètodo encargado de establecer la conexion para generar un nuevo usuario
	 * @param name -> nombre de usuario
	 * @param pass -> contrasena de usuario
	 */
	public String performNewUser(String name, String pass) {
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		String result = usuarioMapper.insertUsuario(name, pass);
		return result;
	}
	
	/**
	 * Mètodo encargado de establecer la conexion para ingresar en la aplicación
	 * @param name -> nombre de usuario
	 * @param pass -> contrasena de usuario
	 */
	public String performAccept(String name, String pass) {
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		String result = usuarioMapper.existUsuario(name, pass);
		return result;
		
	}
	
	/**
	 * Método encargado de obtener todos los datos del usuario
	 * @param name -> nombre del usuario
	 * @return objeto usuario.
	 */
	public Usuario DataUser(String name){
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		Usuario result = usuarioMapper.getUsuario(name);
		return result;
	}
	
	/**
	 * Método encargado de obtener una lista con los crucigramas.
	 * @return lista de crucigramas
	 */
	public ArrayList<Crucigrama> listCrucigramas(){
		CrucigramaMapper crucigramaMapper = new CrucigramaMapper(ds);
		ArrayList<Crucigrama> resultado = crucigramaMapper.getCricigramas();
		return resultado;
	}
	
	/**
	 * Método encargado de obtener una lista con los crucigramas filtradas por un título.
	 * @param filter -> filtro para buscar crucigramas
	 * @return lista de crucigramas
	 */
	public ArrayList<Integer> performFindCrucigrama(String filter){
		CrucigramaMapper crucigramaMapper = new CrucigramaMapper(ds);
		ArrayList<Integer> resultado = crucigramaMapper.getCrucigramasByTitle(filter);
		return resultado;
	}
	
	/**
	 * Método encargado de activar un crucigrama a un usuario.
	 * @param titulo -> titulo del crucigrama
	 * @param name -> nombre del usuario
	 */
	public void activarCrucigrama(String titulo, String name){
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		usuarioMapper.addCrucigrama(titulo, name);
		
	}
	
	/**
	 * Método encargado de obtener una lista con los crucigramas de un usuario.
	 * @param name -> nombre del usuario
	 * @return lista de crucigramas
	 */
	public ArrayList<Crucigrama> listarCrucigramasActivos(String name){
		CrucigramaMapper crucigramaMapper = new CrucigramaMapper(ds);
		ArrayList<Crucigrama> resultado = crucigramaMapper.getCricigramasActivos(name);
		return resultado;
	}
	
	/**
	 * Método encargado de obtener una lista con los crucigramas de un usuario.
	 * @param title -> titulo del crucigrama
	 * @return lista de crucigramas
	 */
	public ArrayList<Palabra> listaPalabrasByCrucigrama(String title){
		PalabraMapper    palabraMapper    = new PalabraMapper(ds);
		ArrayList<Palabra> listaPalabras = palabraMapper.getIdPalabrasByCrucigrama(title);
		return listaPalabras;
	}

	/**
	 * Método encargado de obtener una lista con los nombres de usuarios filtrados por un nombre.
	 * @param filter -> filtro de busqueda
	 * @param nameUser -> nombre del usuario
	 * @return lista de crucigramas
	 */
	public ArrayList<String> performFindFriends(String filter, String nameUser){
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		ArrayList<String> listaUsuarios = usuarioMapper.getUsuariosByfilter(filter, nameUser);
		return listaUsuarios;		
	}

	/**
	 * Método encargado asignar una amistad.
	 * @param nameAmigo -> nombre de amigo
	 * @param nameUser -> nombre del usuario
	 * @return resultado de la operacion
	 */
	public String performAsignarAmistad(String nameUser, String nameAmigo){
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		String ack = usuarioMapper.asignarAmistad(nameUser,nameAmigo);
		return ack;		
	}

	/**
	 * Método encargado de obtener una lista con los crucigramas de un usuario.
	 * @param title -> titulo del crucigrama
	 * @return lista de crucigramas
	 */
	public ArrayList<String> performListarAmigosDe(String userName){
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		ArrayList<String> listaUsuarios = usuarioMapper.getAmigosDe(userName);
		return listaUsuarios;		
	}

	/**
	 * Método encargado de eliminar relacción de amistad.
	 * @param nameUser -> nombre del usuario
	 * @param nameAmigo -> nombre del amigo
	 * @return resultado de la operación
	 */
	public String performEliminarAmistad(String nameUser, String nameAmigo){
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		String ack = usuarioMapper.eliminarAmistad(nameUser,nameAmigo);
		return ack;		
	}
	
	/**
	 * Método encargado de modificar la contraseña y la fecha de nacimiento del usuario.
	 * @param name -> nombre del usuario
	 * @param pass -> password nueva
	 * @param edad -> fecha nueva
	 * @return resultado de la operacion
	 */
	public String performModificarDatos(String name,String pass,Date edad, byte[] byteAvatar){
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		String ack="";
		boolean edadOK = false;
		boolean passOK = false;
		boolean fileOK = false;
		if(!pass.equals("")){
			ack = usuarioMapper.updatePassword(name, pass);
			if(ack.split(":")[0].equals("true")){
				passOK=true;
			}
		}
		if(edad != null){
			ack = usuarioMapper.updateEdad(name, edad);
			if(ack.split(":")[0].equals("true")){
				edadOK=true;
			}
		}
		if(byteAvatar != null){
			ack = usuarioMapper.updateFoto(name, byteAvatar);
			if(ack.split(":")[0].equals("true")){
				fileOK=true;
			}
		}
		if(passOK && edadOK){
			ack = "true:"+name+" tu datos se han cambiado correctamente";
		}	
		return ack;
	}
	
	/**
	 * Método encargado de añadir las respuestas de los usuarios al historial.
	 * @param nameUsuario -> nombre del usuario
	 * @param userAyudado -> nombre usuario ayudado
	 * @param tituloCruci -> titulo del crucigrama
	 * @param idPalabra -> identificador de la palabra
	 * @param respuesta -> respuesta dada
	 * @param correcto -> correcto o incorrecto
	 * @param fecha -> fecha de la respuesta
	 */
	 public void insertarRespuesta(String nameUsuario, String userAyudado, String tituloCruci, int idPalabra, String respuesta, boolean correcto, String fecha) {
		 // TODO Auto-generated method stub
		 UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		 usuarioMapper.addRespuesta(nameUsuario, userAyudado, tituloCruci, idPalabra, respuesta, correcto, fecha);
	 }

	/**
	 * Método encargado de obtener las respuestas correctas de un usuario a un crucigrama.
	 * @param nameUsuario -> nombre del usuario
	 * @param tituloCruci -> titulo del crucigrama
	 * @return lista de respuestas correctas
	 */
	 public ArrayList<String> cargarRespuestas(String nameUsuario, String tituloCruci) {
		 // TODO Auto-generated method stub
		 UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		 ArrayList<String> respuestasCorrectas = usuarioMapper.getRespuestasCorrectas(nameUsuario, tituloCruci);
		 return respuestasCorrectas;
	 }

	/**
	 * Método encargado de obtener los puntos que un usuario ha hido obteniendo con sus respuestas y calcular el resultado.
	 * @param name -> nombre del usuario
	 * @return puntuacion total
	 */
	public Integer performPuntuacion(String name) {
		// TODO Auto-generated method stub
		Integer puntuacion=0;
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		ArrayList<Usuario> historial = usuarioMapper.getHistorialUsuario(name);
		ArrayList<Usuario> respuestaAyuda = usuarioMapper.getRespuestasAyuda(name);
		puntuacion = usr.calculatePuntos(historial,respuestaAyuda);
		return puntuacion;
	}

	/**
	 * Método encargado realizar peticiones de ayuda
	 * @param nameUserAyudado -> nombre del usuario ayudado
	 * @param nameUserAyuda -> nombre usuario ayuda
	 * @param titulo -> titulo del crucigrama
	 * @return resultado de la operacion
	 */
	public String performPeticion(String nameUserAyudado, String nameUserAyuda, String titulo) {
		// TODO Auto-generated method stub
		String ack="";
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		ack = usuarioMapper.insertPeticion(nameUserAyudado, nameUserAyuda, titulo);
		return ack;
	}

	/**
	 * Método encargado de saber si el crucigrama seleccionado lo has mandado a ayudar o no
	 * @param nameUserAyudado -> nombre del usuario ayudado
	 * @param tituloCruci -> titulo del crucigrama
	 * @return si tienes alguno o no
	 */
	public boolean panelBloqueado(String nameUserAyudado, String tituloCruci) {
		// TODO Auto-generated method stub
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		Integer ack;
		ack = usuarioMapper.ayudas(nameUserAyudado, tituloCruci);
		if(ack!=0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Método encargado obtener lista de peticiones de ayuda
	 * @param nameUsuario -> nombre del usuario ayudado
	 * @return lista de usuario ayudado y crucigrama a ayudar
	 */
	public ArrayList<String> listarPeticiones(String nameUsuario) {
		// TODO Auto-generated method stub
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		ArrayList<String> listPeticiones = new ArrayList<String>();
		listPeticiones = usuarioMapper.listaIDS(nameUsuario);
		return listPeticiones;
	}

	/**
	 * Método encargado de eliminar peticiones de ayuda
	 * @param nameUsuario -> nombre del usuario
	 * @param titulo -> titulo del crucigrama
	 * @param usrAyudado -> nombre usuario ayudado
	 * @return resultado de la operacion
	 */
	public String performEliminarPeticion(String nameUsuario, String titulo, String usrAyudado) {
		// TODO Auto-generated method stub
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		String ack = usuarioMapper.EliminarPeticion(nameUsuario, titulo, usrAyudado);
		return ack;	
	}

	public ArrayList<Integer> performFindEtuiqueta(String filter) {
		// TODO Auto-generated method stub
		CrucigramaMapper crucigramaMapper = new CrucigramaMapper(ds);
		ArrayList<Integer> resultado = crucigramaMapper.getCrucigramasByEtiqueta(filter);
		return resultado;
	}
}
