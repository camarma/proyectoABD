package es.ucm.abd.crossword.View.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import es.ucm.abd.crossword.Controller.Controller;
import es.ucm.abd.crossword.Model.Usuario;

public class UserPanel  extends JPanel{

	private static final long serialVersionUID = 1L;
	;

	private JLabel lblfoto;  
	private JLabel lblnombre;  
	private JLabel lbledad;  
	private JLabel lblpuntos;  
	private Usuario usuario;
	private String puntos;
	private JButton btnEdit;
	private JFrame ventana;
	private JButton btnModificar;
	private JButton btnCancelar;
	private JLabel lblNameMod;
	private JLabel lblPassMod;
	private JLabel lblPassNew;
	private JLabel lblPassRep;
	private JLabel lblEdadMod;
	private JLabel lblName;
	private JTextField txtPassNew;
	private JTextField txtPassRep;
	private JTextField txtEdadMod;
	private Controller s_ctrl;
	private MessageDialog messageDialog;
	
	/**
	 * Constructora para inicializar el toolbar panel con sus botones y llamar al metodo build
	 * @param gui recibe como entrada al controlador
	 */
	public UserPanel(Usuario data){
		this.s_ctrl = new Controller();
		this.messageDialog = new MessageDialog();
		this.usuario = data;
		build();
	}
	
	/**
	 * metodo encargado de construir el panel
	 */
	private void build(){
		setBorder(new TitledBorder("Usuario"));
		setLayout(new BorderLayout());
		JPanel panelFoto = new JPanel();
		//add(panelFoto);
		JPanel panelDatos = new JPanel(new GridLayout(3,0));
		//add(panelDatos);
		JPanel panelEdit = new JPanel();
		//add
	      
	    this.lblfoto = new JLabel();
	    if(usuario.getFoto()!=null){
		    this.lblfoto.setIcon(new ImageIcon(usuario.getFoto()));
	    }else{
		    this.lblfoto.setIcon(new ImageIcon(Login.class.getResource("img/success.png")));
	    }
	    
	    this.lblnombre = new JLabel("Nombre: "+usuario.getNombre());
	   	    
	    if(usuario.getFechaNacimiento()!=null){
	    	this.lbledad = new JLabel("Edad: "+calculateAge()+" años");
	    }else{
	    	this.lbledad = new JLabel("Edad: "+"Sin especificar");
	    }
	    	
	    this.lblpuntos = new JLabel("Puntos: "+this.puntos+" puntos");
	    
	    this.btnEdit = new JButton(new ImageIcon(Login.class.getResource("img/edit.png")));
	    this.btnEdit.setPreferredSize(new Dimension(50, 50));
	    
	    panelFoto.add(lblfoto);
	    panelDatos.add(lblnombre);
	    panelDatos.add(lbledad);
	    panelDatos.add(lblpuntos);
	    panelEdit.add(btnEdit);
	    editar();
	    this.add(panelEdit ,BorderLayout.EAST);
	    this.add(panelFoto,BorderLayout.WEST);
	    this.add(panelDatos,BorderLayout.CENTER);
	    
	}
	
	private String calculateAge(){
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		String hoy = formato.format(fechaActual);
		String fechanNaci = formato.format(usuario.getFechaNacimiento());
		String[] dat1 = fechanNaci.split("-");
		String[] dat2 = hoy.split("-");
		int anos = Integer.parseInt(dat2[0]) - Integer.parseInt(dat1[0]);
		int mes = Integer.parseInt(dat2[1]) - Integer.parseInt(dat1[1]);
		if (mes < 0) {
			anos = anos - 1;
		} else if (mes == 0) {
			int dia = Integer.parseInt(dat2[3]) - Integer.parseInt(dat1[3]);
			if (dia > 0) {
				anos = anos - 1;
			}
		}

		return String.valueOf(anos);
	}
	
	public void editar(){
		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ventanaEditar();
			}
		});
	}
	
	 private void ventanaEditar(){
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		ventana = new JFrame("Editar datos");
		//JPanel centerPanel = new JPanel();
		JPanel datos = new JPanel(new GridLayout(5,0));	
		JPanel botones = new JPanel();
		
		lblNameMod = new JLabel("Nombre:");
		lblPassMod = new JLabel("Contraseña:");
		lblPassNew = new JLabel("Nueva contraseña:");
		lblPassRep = new JLabel("Repetir contraseña:");
		lblEdadMod = new JLabel("Fecha de nacimiento:");
		btnModificar = new JButton("Modificar");
		btnCancelar = new JButton("Cancelar");
		lblName = new JLabel(usuario.getNombre());
		txtPassNew = new JPasswordField();
		txtPassRep = new JPasswordField();
		txtEdadMod = new JTextField(formato.format(usuario.getFechaNacimiento()));
			
		datos.add(lblNameMod);
		datos.add(lblName);
		datos.add(lblPassNew);
		datos.add(txtPassNew);
		datos.add(lblPassRep);
		datos.add(txtPassRep);
		datos.add(lblEdadMod);
		datos.add(txtEdadMod);
		datos.add(btnModificar);
		datos.add(btnCancelar);
		modificar();
		cancelar();
		ventana.add(datos,BorderLayout.CENTER);
		ventana.add(botones,BorderLayout.SOUTH);
		ventana.setSize(400, 200);
		ventana.setVisible(true);
	}
	 
	public void modificar(){
		btnModificar.addActionListener(new ActionListener() {
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			private String pass="";
			private Date edad = null;
			private boolean passNOOK = false;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if((txtPassNew.getText().equals("") || txtEdadMod.getText().equals(formato.format(usuario.getFechaNacimiento()))) && (txtEdadMod.getText().equals("") && txtPassRep.getText().equals(""))){
					ventana.dispose();
				}else{
					if(txtPassNew.getText().equals(txtPassRep.getText())){
						pass = txtPassNew.getText();
						passNOOK = false;
					}else{
						passNOOK = true;
						messageDialog.reportMessage("false:Las contraseñas no coinciden");
					}
					if(!txtEdadMod.getText().equals(formato.format(usuario.getFechaNacimiento()))){
						if(validaFormato(txtEdadMod.getText())){
							try {
								edad = formato.parse(txtEdadMod.getText());
								passNOOK = false;
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}else{
							passNOOK = true;
							messageDialog.reportMessage("false:El formato de la fecha es incorrecto. Debe ser yyyy-MM-dd");
						}
					}
				}
				if(pass.equals("") && edad == null){
					cancelar();
				}else if(passNOOK){
					
				}
				else{
					String ack = s_ctrl.performModificarDatos(usuario.getNombre(),pass,edad);
					messageDialog.reportMessage(ack);
					ventana.dispose();
				}
				
				
				/*if(!txtPassNew.getText().equals("")){
					if(!txtPassRep.getText().equals("") && txtPassNew.getText().equals(txtPassRep.getText())){
						pass = txtPassNew.getText();
					}else{
						passNOOK = true;
						messageDialog.reportMessage("false:Las contraseñas no coinciden");
					}
				}
				if(!txtEdadMod.getText().equals("")){
					if(!txtEdadMod.getText().equals(formato.format(usuario.getFechaNacimiento()))){
						if(validaFormato(txtEdadMod.getText())){
							try {
								edad = formato.parse(txtEdadMod.getText());
								System.out.println(edad);
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}else{
							messageDialog.reportMessage("false:El formato de la fecha es incorrecto. Debe ser yyyy-MM-dd");
						}
					}else{
						ventana.dispose();
					}
				}
				if(pass.equals("") && edad == null){
					cancelar();
				}else if(passNOOK){
					
				}
				else{
					String ack = s_ctrl.performModificarDatos(usuario.getNombre(),pass,edad);
					messageDialog.reportMessage(ack);
					ventana.dispose();
				}*/
			}
		});
	}
	
	public void cancelar(){
		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ventana.dispose();
			}
		});
	}
	 
	private boolean validaFormato(String fecha){
		boolean fechaOK = false;
		String parts[] = fecha.split("-");
		System.out.println(fecha+" "+parts);
		if(parts.length==3){
			if(parts[0].length()!=4 || !isNumeric(parts[0])){
				fechaOK = false;
			}else if(parts[1].length()!=2 || !isNumeric(parts[1]) || Integer.parseInt(parts[1])>12){
				fechaOK = false;
			}else if(parts[2].length()!=2 || !isNumeric(parts[2]) || Integer.parseInt(parts[2])>31){
				fechaOK = false;
			}else{
				fechaOK = true;
			}
		}else{
			fechaOK = false;
		}
		return fechaOK;
	}
	
	private static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
}
