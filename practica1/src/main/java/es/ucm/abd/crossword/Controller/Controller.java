package es.ucm.abd.crossword.Controller;

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
import es.ucm.abd.crossword.View.gui.Login;

/**
 * Clase encargada de intermediar entre la logica de la aplicación y la vista
 * @author Alberto y George
 *
 */
public class Controller {
	MyConnection conn;
	DataSource ds;
	Usuario usr;
	@SuppressWarnings("unused")
	private Login ack;
	public Controller(){
		conn = new MyConnection();
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
		//conn.close();
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
		//conn.close();
		return result;
		
	}
	
	public Usuario DataUser(String name){
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		Usuario result = usuarioMapper.getUsuario(name);
		//conn.close();
		return result;
	}
	
	public ArrayList<Crucigrama> listCrucigramas(){
		CrucigramaMapper crucigramaMapper = new CrucigramaMapper(ds);
		ArrayList<Crucigrama> resultado = crucigramaMapper.getCricigramas();
		//conn.close();
		return resultado;
	}
	
	public ArrayList<Integer> performFindCrucigrama(String filter){
		CrucigramaMapper crucigramaMapper = new CrucigramaMapper(ds);
		ArrayList<Integer> resultado = crucigramaMapper.getCrucigramasByTitle(filter);
		//conn.close();
		return resultado;
	}
	
	public void activarCrucigrama(String titulo, String name){
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		//conn.close();
		usuarioMapper.addCrucigrama(titulo, name);
		
	}
	public ArrayList<Crucigrama> listarCrucigramasActivos(String name){
		CrucigramaMapper crucigramaMapper = new CrucigramaMapper(ds);
		ArrayList<Crucigrama> resultado = crucigramaMapper.getCricigramasActivos(name);
		//conn.close();
		return resultado;
	}
	
	public ArrayList<Palabra> listaPalabrasByCrucigrama(String title){
		CrucigramaMapper crucigramaMapper = new CrucigramaMapper(ds);
		PalabraMapper    palabraMapper    = new PalabraMapper(ds);
		//ArrayList<Integer> resultado = crucigramaMapper.getIdPalabrasByCrucigrama(title);
		ArrayList<Palabra> listaPalabras = palabraMapper.getIdPalabrasByCrucigrama(title);
		//System.out.println(listaPalabras.get(0).getPosx());
		return listaPalabras;
	}
	//Alberto
	public ArrayList<String> performFindFriends(String filter, String nameUser){
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		ArrayList<String> listaUsuarios = usuarioMapper.getUsuariosByfilter(filter, nameUser);
		return listaUsuarios;		
	}
	//Alberto
	public String performAsignarAmistad(String nameUser, String nameAmigo){
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		String ack = usuarioMapper.asignarAmistad(nameUser,nameAmigo);
		return ack;		
	}
	//Alberto
	public ArrayList<String> performListarAmigosDe(String userName){
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		ArrayList<String> listaUsuarios = usuarioMapper.getAmigosDe(userName);
		return listaUsuarios;		
	}
	//ALberto
	public String performEliminarAmistad(String nameUser, String nameAmigo){
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		String ack = usuarioMapper.EliminarAmistad(nameUser,nameAmigo);
		return ack;		
	}
	
	//ALberto
	public String performModificarDatos(String name,String pass,Date edad){
		UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		String ack="";
		boolean edadOK = false;
		boolean passOK = false;
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
		if(passOK && edadOK){
			ack = "true:"+name+" tu datos se han cambiado correctamente";
		}	
		return ack;
	}
	
	//George
	 public void insertarRespuesta(String nameUsuario, String tituloCruci, int idPalabra, String respuesta, boolean correcto, String fecha) {
	 // TODO Auto-generated method stub
		 UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		 usuarioMapper.addRespuesta(nameUsuario, tituloCruci, idPalabra, respuesta, correcto, fecha);
	 }

	 public ArrayList<String> cargarRespuestas(String nameUsuario, String tituloCruci) {
	 // TODO Auto-generated method stub
		 UsuarioMapper usuarioMapper = new UsuarioMapper(ds);
		 ArrayList<String> respuestasCorrectas = usuarioMapper.getRespuestasCorrectas(nameUsuario, tituloCruci);
		 return respuestasCorrectas;
	 }
}
