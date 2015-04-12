

import java.util.List;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import es.ucm.abd.crossword.Model.Crucigrama;
import es.ucm.abd.crossword.Model.CrucigramaMapperTest;
import es.ucm.abd.crossword.Model.MyConnection;
import es.ucm.abd.crossword.Model.UsuarioMapperTest;

public class CrosswordDAO {
	private DataSource ds;

	/**
	 * Aquí se debe inicializar el pool de conexiones, mediante
	 * la creación de un DataSource, que deberá ser asignado a
	 * la variable ds.
	 */
	public CrosswordDAO() {
		 this.ds = new MyConnection().getDS();
	}

	
	/**
	 * Devuelve la contrasena del usuario cuyo nick se pasa como
	 * parámetro. Devuelve null si el usuario no existe. 
	 */
	public String getPassword(String nick) {
		UsuarioMapperTest UsuarioMapperTest = new UsuarioMapperTest(ds);
		String result = UsuarioMapperTest.getPassword(nick);
		if(result!=null)
			return result;
		else
			return null;
	}
	
	/**
	 * Modifica la contrasena del usuario pasado como parámetro 
	 */
	public void modifyPassword(String nick, String newPassword) {
		UsuarioMapperTest UsuarioMapperTest = new UsuarioMapperTest(ds);
		@SuppressWarnings("unused")
		String result = UsuarioMapperTest.updatePassword(nick,newPassword);
	}

	/**
	 * Devuelve una lista de las claves de aquellos crucigramas
	 * cuyo título contenga str.
	 * 
	 * Si escogisteis una clave numérica para la tabla de crucigramas,
	 * se debe devolver una lista de Integer. Si escogisteis el título
	 * como clave, se debe devolver una lista de String. Si, por el contrario,
	 * escogisteis una clave compuesta, debéis crear una clase para almacenar
	 * los atributos de dicha clave. 
	 */
	public List<?> findCrosswordsByTitle(String str) {
		CrucigramaMapperTest CrucigramaMapperTest = new CrucigramaMapperTest(ds);
		List<?> result = CrucigramaMapperTest.getCrucigramasByTitle(str);
		return result;
	}

	/**
	 * Devuelve el título del crucigrama cuya clave se pasa como
	 * parámetro.
	 */
	public String getCrosswordTitle(Object id) {
		CrucigramaMapperTest CrucigramaMapperTest = new CrucigramaMapperTest(ds);
		Crucigrama result = CrucigramaMapperTest.findById((Integer) id);
		return result.getTitulo();
	}
	
	/**
	 * Anade un crucigrama a la lista de crucigramas activos de un usuario.
	 * 
	 * El crucigrama se especifica mediante su clave
	 */
	public void addCrosswordToUser(String nick, Object crosswordId) {
		UsuarioMapperTest UsuarioMapperTest = new UsuarioMapperTest(ds);
		@SuppressWarnings("unused")
		String result = UsuarioMapperTest.addCrucigrama(nick,(Integer) crosswordId);
	}
	
	/**
	 * Devuelve la lista de identificadores de los crucigramas activos
	 * del usuario pasado como parámetro
	 */
	public List<?> getCrosswordsOf(String nick) {
		CrucigramaMapperTest CrucigramaMapperTest = new CrucigramaMapperTest(ds);
		List<?> result = CrucigramaMapperTest.getCricigramasActivos(nick);
		return result;
	}

	/**
	 * Cierra el dataSource
	 */
	public void close() {
		((ComboPooledDataSource)ds).close();
	}
}
