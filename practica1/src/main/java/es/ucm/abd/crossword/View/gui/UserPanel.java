package es.ucm.abd.crossword.View.gui;

import java.awt.BorderLayout;
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
	private Integer puntos=0;
	private JButton btnEdit;
	private JFrame ventana;
	private JButton btnModificar;
	private JButton btnCancelar;
	private JLabel lblNameMod;
	private JLabel lblPassNew;
	private JLabel lblPassRep;
	protected JLabel lblEdadMod;
	private JLabel lblName;
	private JTextField txtPassNew;
	private JTextField txtPassRep;
	private Controller s_ctrl;
	private MessageDialog messageDialog;
	private JButton btnCalendar;
	private PanelCalendar pc;
	private static String fechaNueva ="";
	private boolean tieneFecha=false;
	private JButton btnAvatar;
	private JLabel lblAvatar;
	private static String nombre; 
	
	/**
	 * Constructora para inicializar el toolbar panel con sus botones y llamar al metodo build
	 * @param gui recibe como entrada al controlador
	 */
	public UserPanel(Usuario data, Integer puntuacion){
		this.s_ctrl = new Controller();
		this.messageDialog = new MessageDialog();
		this.usuario = data;
		this.puntos = puntuacion;
		this.nombre = usuario.getNombre();
		if(usuario.getFechaNacimiento()!=null)
			tieneFecha = true;	
		build();
	}
	public UserPanel(){
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
	    	this.lbledad = new JLabel("Edad: "+calculateAge()+" anos");
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
			int dia = Integer.parseInt(dat2[2]) - Integer.parseInt(dat1[2]);
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
		JPanel panelCalendar = new JPanel();
		JPanel panelAvatar = new JPanel();
		ventana = new JFrame("Editar datos");
		//JPanel centerPanel = new JPanel();
		JPanel datos = new JPanel(new GridLayout(6,0));	
		//JPanel botones = new JPanel();
		
		lblNameMod = new JLabel("Nombre:");
		new JLabel("Contrasena:");
		lblPassNew = new JLabel("Nueva contrasena:");
		lblPassRep = new JLabel("Repetir contrasena:");
		lblEdadMod = new JLabel("Fecha de nacimiento:");
		lblAvatar = new JLabel("Avatar:");
		btnModificar = new JButton("Modificar");
		btnCancelar = new JButton("Cancelar");
		btnCalendar = new JButton(new ImageIcon(Login.class.getResource("img/calendar.png")));
		btnCalendar.setPreferredSize(new Dimension(30, 30));
		btnAvatar = new JButton(new ImageIcon(Login.class.getResource("img/addAvatar.png")));
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
		ventana.add(datos,BorderLayout.CENTER);
		ventana.setSize(500, 280);
		ventana.setVisible(true);
	}
	 
	public void modificar(){
		btnModificar.addActionListener(new ActionListener() {
			SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			private String pass="";
			private Date edad = null;
			private String ack="";
			private boolean passOK = false;
			private boolean fechaOK = false;
			
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
				if(passOK || fechaOK){
					ack = s_ctrl.performModificarDatos(usuario.getNombre(), pass, edad);
					messageDialog.reportMessage(ack);
				}
			
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
	
	public void avatar(){
		btnAvatar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	 
	protected void setFecha(String fecha){
		this.fechaNueva = fecha;
	}
	
	private boolean validaPass(String passN, String passR){
		boolean passOK = false;
		if (passN.equals(passR)){
			passOK = true;
		}
		return passOK;
		
	}
}
