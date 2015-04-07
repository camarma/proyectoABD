package es.ucm.abd.crossword.View.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import es.ucm.abd.crossword.Controller.Controller;
import es.ucm.abd.crossword.Model.Usuario;

public class Login extends JFrame {

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
		public Login() {  

			// Titulo del la ventana
			super("Bienvenido");
			setSize(400, 230);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			panel = new JPanel();
			panel.setLayout(null);
			build(panel);
			add(panel);
			
			setVisible(true);

		}

		/**
		 * metodo encargado de construir el panel
		 */
		private void build(JPanel panel) {
			
			lblName = new JLabel("Nombre de usuario:");
			lblName.setBounds(30, 30, 120, 25);
			panel.add(lblName);

			txtName = new JTextField(20);
			txtName.setBounds(180, 30, 160, 25);
			panel.add(txtName);

			lblPass = new JLabel("Contraseña:");
			lblPass.setBounds(50, 60, 80, 25);
			panel.add(lblPass);

			txtPass = new JPasswordField(20);
			txtPass.setBounds(180, 60, 160, 25);
			panel.add(txtPass);

			btnAccept = new JButton("Aceptar");
			botonAceptar();
			btnAccept.setBounds(70, 100, 120, 25);
			panel.add(btnAccept);
			
			btnNewUsr = new JButton("Nuevo usuario");
			botonNuevoUsuario();
			btnNewUsr.setBounds(220, 100, 120, 25);
			panel.add(btnNewUsr);
	
		}
		
		/**
		 * Método para el funcionamiento del boton nuevo usuario
		 */
		private void botonNuevoUsuario(){
			btnNewUsr.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(txtName.getText().equals("") || txtPass.getText().equals("")){
						messageDialog.reportMessage("false:Debes rellenar los campos");
					}else{
						String ack = s_ctrl.performNewUser(txtName.getText(),txtPass.getText());
						messageDialog.reportMessage(ack);
					}
				}
			});
		}
		
		/**
		 * Método para el funcionamiento del boton aceptar
		 */
		private void botonAceptar(){
			btnAccept.addActionListener(new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(txtName.getText().equals("") || txtPass.getText().equals("")){
						messageDialog.reportMessage("false:Debes rellenar los campos");
					}else{
						String ack = s_ctrl.performAccept(txtName.getText(),txtPass.getText());
						String parts[] = ack.split(":");
						if(parts[0].equals("true")){
							Usuario dataUsr = s_ctrl.DataUser(txtName.getText());
							StartWindow ventanaInicio = new StartWindow(dataUsr);
							ventanaInicio.setVisible(true);
							Login.this.dispose();
						}else{
							messageDialog.reportMessage(ack);
						}
					}
				}
			});
		}
}
