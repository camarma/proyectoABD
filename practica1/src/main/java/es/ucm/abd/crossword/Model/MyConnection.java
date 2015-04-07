package es.ucm.abd.crossword.Model;

import java.beans.PropertyVetoException;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Clase encargada de generar las conexiones
 * @author Alberto y George
 *
 */
public class MyConnection {
	private DataSource ds;
	private ComboPooledDataSource cpds = new ComboPooledDataSource();
	public MyConnection() {
		
		try {
			cpds.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			System.err.println("fallo");
			e.printStackTrace();
		}
		cpds.setJdbcUrl("jdbc:mysql://localhost/practica1_511");
		cpds.setUser("UsuarioP1");
		cpds.setPassword("");
		
		cpds.setAcquireRetryAttempts(1);
		cpds.setAcquireRetryDelay(1);
		ds = cpds;
		
	}
	/**
	 * Método para generar el data source
	 * @return el data source
	 */
	public DataSource getDS(){
		return ds;
	}
	
	/**
	 * Método para cerrar la conexión
	 */
	public void close(){
		cpds.close();
	}
}