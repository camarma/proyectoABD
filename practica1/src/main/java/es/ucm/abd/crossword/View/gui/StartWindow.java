package es.ucm.abd.crossword.View.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import es.ucm.abd.crossword.Model.Usuario;

/**
 * Clase encargada de pintar el panel posterior al login y dividir dos paneles que son el de los datos del usuario
 * logado y el del panel principal
 * @author George y Alberto
 * 
 */
public class StartWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private UserPanel up;
	private PanelPrincipal lp;
	
	/**
	 * la costructora recibe los datos del usuario logado y su puntuacion
	 * @param data los datos del usuario
	 * @param puntuacion la puntuacion del usuario
	 */
	public StartWindow(Usuario data, Integer puntuacion) {

		// Titulo del la ventana
		super("Bienvenido");
		this.up = new UserPanel(data,puntuacion);
		this.lp = new PanelPrincipal((String)data.getNombre());
		build();

	}

	/**
     * metodo encargado de construir el panel
     */
	private void build() {
		// Definimos el tipo de estructura general de nuestra ventana
		setLayout(new BorderLayout());
		// Dividimos la ventana en secciones
			
		add(up, BorderLayout.NORTH);
		add(lp, BorderLayout.CENTER);

		setVisible(true);
		setSize(500, 740);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
