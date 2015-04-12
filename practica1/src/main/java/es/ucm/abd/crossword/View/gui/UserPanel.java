package es.ucm.abd.crossword.View.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import es.ucm.abd.crossword.Controller.Controller;
import es.ucm.abd.crossword.Model.Usuario;

/**
 * Clase encargada de mostrar los datos del usuario y modificarlos
 * @author Alberto y George
 *
 */
public class UserPanel  extends JPanel{

	private static final long serialVersionUID = 1L;

	private JLabel lblfoto;  
	private JLabel lblnombre;  
	private static JLabel lbledad;  
	private static JLabel lblpuntos;  
	private Usuario usuario;
	private Integer puntos=0;
	private JButton btnEdit;
	private JFrame ventana;
	private JButton btnModificar;
	private JButton btnCancelar;
	private JLabel lblNameMod;
	private JLabel lblPassNew;
	private JLabel lblPassRep;
	private JLabel lblEdadMod;
	private JLabel lblName;
	private JTextField txtPassNew;
	private JTextField txtPassRep;
	private Controller s_ctrl;
	private MessageDialog messageDialog;
	private JButton btnCalendar;
	@SuppressWarnings("unused")
	private PanelCalendar pc;
	private static String fechaNueva ="";
	private boolean tieneFecha=false;
	private JButton btnAvatar;
	private JLabel lblAvatar;
	@SuppressWarnings("unused")
	private static String nombre; 
	final private String src_avatar = "img/avatar.png"; 
	final private String src_editar = "img/edit.png";
	final private String src_calendar = "img/calendar.png";
	final private String src_addavatar = "img/addAvatar.png";
	private JFileChooser fc;
	private static File fileAvatar;
	private static byte [] byteAvatar;
	

	/**
	 * La constructora recibe los datos del usuario y su puntuacion
	 * @param data datos del usuario 
	 * @param puntuacion la puntuacion del usuario
	 */
	@SuppressWarnings("static-access")
	public UserPanel(Usuario data, Integer puntuacion){
		this.fc = new JFileChooser();
		this.s_ctrl = new Controller();
		this.messageDialog = new MessageDialog();
		this.usuario = data;
		this.puntos = puntuacion;
		this.nombre = usuario.getNombre();
		if(usuario.getFechaNacimiento()!=null)
			tieneFecha = true;	
		build();
		if(fileAvatar!=null){
			new ImageIcon(usuario.getFoto());
		}else{
			
		}
	}
	
	/**
	 * Costructora vacia
	 */
	public UserPanel(){
		
	}
	
	/**
	 * metodo encargado de construir el panel
	 */
	@SuppressWarnings("static-access")
	private void build(){
		
		setBorder(new TitledBorder("Usuario"));
		setLayout(new BorderLayout());
		JPanel panelFoto = new JPanel();
		JPanel panelDatos = new JPanel(new GridLayout(3,0));
		JPanel panelEdit = new JPanel();
	      
	    this.lblfoto = new JLabel();
	    if(usuario.getFoto()!=null){
		    this.lblfoto.setIcon(new ImageIcon(usuario.getFoto()));
	    }else{
		    this.lblfoto.setIcon(new ImageIcon(Login.class.getResource(src_avatar)));
	    }
	    
	    this.lblnombre = new JLabel("Nombre: "+usuario.getNombre());
	   	    
	    if(usuario.getFechaNacimiento()!=null){
	    	this.lbledad = new JLabel("Edad: "+calculateAge()+" años");
	    }else{
	    	this.lbledad = new JLabel("Edad: "+"Sin especificar");
	    }
	    	
	    this.lblpuntos = new JLabel("Puntos: "+this.puntos+" puntos");
	    
	    this.btnEdit = new JButton(new ImageIcon(Login.class.getResource(src_editar)));
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
	
	/**
	 * metodo quue calcule la edad el usuario
	 * @return devuelve la edad calculada
	 */
	private String calculateAge(){
		
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		String hoy = formato.format(fechaActual);
		String fechanNaci = "";
		if(tieneFecha){
			fechanNaci = formato.format(usuario.getFechaNacimiento());
		}else{
			fechanNaci = fechaNueva;
		}
		
		String[] dat1 = fechanNaci.split("-");
		String[] dat2 = hoy.split("-");
		int anos = Integer.parseInt(dat2[0]) - Integer.parseInt(dat1[0]);
		int mes = Integer.parseInt(dat2[1]) - Integer.parseInt(dat1[1]);
		if (mes < 0) {
			anos = anos - 1;
		} else if (mes == 0) {
			int dia = Integer.parseInt(dat2[2]) - Integer.parseInt(dat1[2]);
			if (dia > 0) {
				anos = anos - 1;
			}
		}

		return String.valueOf(anos);
	}
	
	/**
	 * Boton de editar datos
	 */
	public void editar(){
		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ventanaEditar();
			}
		});
	}
	
	/**
	 * Metodo que abre la ventana de editar la informacion
	 */
	private void ventanaEditar(){
		JPanel panelCalendar = new JPanel();
		JPanel panelAvatar = new JPanel();
		ventana = new JFrame("Editar datos");
		JPanel datos = new JPanel(new GridLayout(6,0));	
		
		lblNameMod = new JLabel("Nombre:");
		new JLabel("Contrasena:");
		lblPassNew = new JLabel("Nueva contrasena:");
		lblPassRep = new JLabel("Repetir contrasena:");
		lblEdadMod = new JLabel("Fecha de nacimiento:");
		lblAvatar = new JLabel("Avatar:");
		btnModificar = new JButton("Modificar");
		btnCancelar = new JButton("Cancelar");
		btnCalendar = new JButton(new ImageIcon(Login.class.getResource(src_calendar)));
		btnCalendar.setPreferredSize(new Dimension(30, 30));
		btnAvatar = new JButton(new ImageIcon(Login.class.getResource(src_addavatar)));
		btnAvatar.setPreferredSize(new Dimension(30, 30));
		lblName = new JLabel(usuario.getNombre());
		txtPassNew = new JPasswordField();
		txtPassRep = new JPasswordField();
			
		datos.add(lblNameMod);
		datos.add(lblName);
		datos.add(lblPassNew);
		datos.add(txtPassNew);
		datos.add(lblPassRep);
		datos.add(txtPassRep);
		datos.add(lblEdadMod);
		
		panelCalendar.add(btnCalendar);
		datos.add(panelCalendar,BorderLayout.WEST);
		datos.add(lblAvatar);
		panelAvatar.add(btnAvatar);
		datos.add(panelAvatar,BorderLayout.WEST);
		datos.add(btnCancelar);
		datos.add(btnModificar);
		
		modificar();
		cancelar();
		calendario();
		avatar();
		ventana.add(datos,BorderLayout.CENTER);
		ventana.setSize(500, 280);
		ventana.setVisible(true);
	}
	
	/**
	 * Boton para modificar los datos que el usuario quiera cambiar
	 */
	public void modificar(){
		btnModificar.addActionListener(new ActionListener() {
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			private String pass="";
			private Date edad = null;
			private String ack="";
			private boolean passOK = false;
			private boolean fechaOK = false;
			private boolean fileOK = false;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(txtPassNew.getText().equals("") && txtPassRep.getText().equals("") && fechaNueva.equals("") && tieneFecha){
					ventana.dispose();
				}else{
					if(!txtPassNew.getText().equals("") && !txtPassRep.getText().equals("")){
						if(!validaPass(txtPassNew.getText(),txtPassRep.getText())){
							passOK = false;
							messageDialog.reportMessage("false:Las contrasenas no coinciden.");
						}else{
							passOK = true;
						}
					}
					if(fechaNueva.equals("") && tieneFecha){
						fechaOK = false;
					}if(byteAvatar!=null){
						fileOK = true;
					}else{
						fechaOK = true;
					}
				}
				if(passOK){
					pass = txtPassNew.getText();
				}

				if(fechaOK){
					try {
						edad = formato.parse(fechaNueva);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if(passOK || fechaOK || fileOK){
					ack = s_ctrl.performModificarDatos(usuario.getNombre(), pass, edad,byteAvatar);
					refreshAvatar();
					messageDialog.reportMessage(ack);
				}
			
			}
		});
	}
	
	/**
	 * Boton para salir de la ventana de modificar
	 */
	public void cancelar(){
		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ventana.dispose();
			}
		});
	}
	
	/**
	 * boton para abrir un calendario para eligir la fecha de nacimiento
	 */
	public void calendario(){
		btnCalendar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
				String fecha="";
			    if(usuario.getFechaNacimiento()!=null){
			    	fecha = formato.format(usuario.getFechaNacimiento());
			    }
				pc = new PanelCalendar(fecha);
			}
		});
	}
	
	/**
	 * boton para modificar avatar
	 */
	public void avatar(){
		btnAvatar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Handle open button action.
			    if (e.getSource() == btnAvatar) {
			    	int returnVal = fc.showOpenDialog(null);
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fc.getSelectedFile();
			            //This is where a real application would open the file.
			            System.out.println("Opening: " + file.getPath());
			            fileAvatar = new File(file.getName());
			            try {
							byteAvatar = Files.readAllBytes(file.toPath());
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
			            
			        } else {
			        	messageDialog.reportMessage("false:error");
			        }
			   } 
			}
		});
	}

	/**
	 * metodo para actualizar a fecha
	 * @param fecha la nueva fecha
	 */
	@SuppressWarnings("static-access")
	protected void setFecha(String fecha){
		this.fechaNueva = fecha;
	}
	
	/**
	 * metodo para validar la contraseña modificada
	 * @param passN la nueva contraseña
	 * @param passR la confirmacion de la nueva contraseña
	 * @return
	 */
	private boolean validaPass(String passN, String passR){
		boolean passOK = false;
		if (passN.equals(passR)){
			passOK = true;
		}
		return passOK;
		
	}
	
	/**
	 * refrescar edad
	 */
	@SuppressWarnings("static-access")
	public void refresh(){
		this.lbledad.setText("Edad: "+calculateAge()+" años");
	}
	
	/**
	 * refrescar puntuacion
	 * @param puntuacion
	 */
	@SuppressWarnings("static-access")
	public void refreshPuntuacion(Integer puntuacion) {
		// TODO Auto-generated method stub
		this.lblpuntos.setText("Puntos: "+puntuacion+" puntos");
	}
	
	public void refreshAvatar(){
		this.lblfoto.setIcon(new ImageIcon(byteAvatar));
	}
}
