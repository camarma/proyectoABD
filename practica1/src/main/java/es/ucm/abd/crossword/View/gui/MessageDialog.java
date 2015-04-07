package es.ucm.abd.crossword.View.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class MessageDialog {

/**
 * Método que genera la ventana de notificaciones
 * @param msg devuelve el mensaje
 */
	void reportMessage(String msg){ 
		String parts[] = msg.split(":");
		if(parts[0].equals("true")){
			alertMessage(parts[1],"Exito","img/success.png");
		}else{
			alertMessage(parts[1],"Error","img/warning.png");
		}
	}
	
	private void alertMessage(String msg, String titulo, String rutaFoto){
		JDialog dialogo = new JDialog();
		Image img = new ImageIcon(Login.class.getResource(rutaFoto)).getImage();
		dialogo.setIconImage(img);		
				
		JLabel redLabel = new JLabel(msg); 
	
		redLabel.setHorizontalAlignment(JLabel.CENTER);
	
		dialogo.setLayout(new FlowLayout(FlowLayout.CENTER,10,50));
		redLabel.setFont(new Font("Courier", Font.BOLD, 15));
		
		JLabel label = new JLabel();  
	    label.setIcon(new ImageIcon(Login.class.getResource(rutaFoto)));
	    dialogo.add(label);
	    dialogo.add(redLabel); 
		dialogo.setTitle(titulo);
		dialogo.setModal(true);
	    dialogo.setSize(610, 200);
	    dialogo.setLocationRelativeTo(label);
	    dialogo.setVisible(true);
	}

	
	/**
	 * Método encargado de validar los operandos
	 * @param operando el valor del operando de entrada
	 * @return la validacion
	 */
	boolean validarOperando(String operando){
		return operando.matches("[-+]?\\d*\\.?\\d+");
	}
}
