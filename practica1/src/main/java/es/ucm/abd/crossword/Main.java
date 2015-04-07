package es.ucm.abd.crossword;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import es.ucm.abd.crossword.View.gui.Login;

/**
 * Clase principal donde abrimos la ventana de la aplicación.
 * @author Alberto y George
 *
 */
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub 
		ModoSwing();
	}
	
	/**
	 * Metodo para abrir la ventana
	 */
	private static void ModoSwing(){
		
		//le metemos un poco de look&feel predeterminado
		try {
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		//le metemos nimbus que es el predeterminado
			if ("Nimbus".equals(info.getName())) {
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}

		} catch (Exception e) {
		// si nimbus no existe, le metemos el predeterminado del sistema operativo
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception ex) {
				//si no existe, no hay más que hacer
				System.exit(0);
			}
		}
		@SuppressWarnings("unused")
		Login ventana = new Login();
	}
}

