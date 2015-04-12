package es.ucm.abd.crossword.View.gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.ucm.abd.crossword.Controller.Controller;
import es.ucm.abd.crossword.Model.Usuario;

public class StartWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	/**
	 * Clase encargada de pintar el panel de login
	 * 
	 * @author George y Alberto
	 * 
	 */
		private Controller s_ctrl = new Controller();
		private MessageDialog messageDialog = new MessageDialog();
		private JLabel lblName; 
		private JLabel lblPass; 
		private JTextField txtName;
		private JTextField txtPass;
		private JButton btnAccept;
		private JButton btnNewUsr;
		private JPanel panel;
		private static String msg="";
		private UserPanel up;
		private PanelPrincipal lp;
		
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
