package es.ucm.abd.crossword.View.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

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
	
	/**
	 * Constructora para inicializar el toolbar panel con sus botones y llamar al metodo build
	 * @param gui recibe como entrada al controlador
	 */
	public UserPanel(Usuario data){
		this.usuario = data;
		build();
	}
	
	/**
	 * metodo encargado de construir el panel
	 */
	private void build(){
		setBorder(new TitledBorder("Usuario"));
		setLayout(new BorderLayout());
		JPanel panelFoto = new JPanel(new GridLayout(1, 0));
		add(panelFoto, BorderLayout.WEST);
		JPanel panelDatos = new JPanel(new GridLayout(3, 0));
		add(panelDatos, BorderLayout.EAST);
	      
	    this.lblfoto = new JLabel();
	    if(usuario.getFoto()!=null){
		    this.lblfoto.setIcon(new ImageIcon(usuario.getFoto()));
	    }else{
		    this.lblfoto.setIcon(new ImageIcon(Login.class.getResource("img/success.png")));
	    }
	    
	    this.lblnombre = new JLabel(usuario.getNombre());
	   	    
	    if(usuario.getFechaNacimiento()!=null){
	    	this.lbledad = new JLabel(calculateAge()+" años");
	    }else{
	    	this.lbledad = new JLabel("Sin especificar");
	    }
	    	
	    this.lblpuntos = new JLabel(this.puntos+" puntos");
	    
	    panelFoto.add(lblfoto);
	    panelDatos.add(lblnombre);
	    panelDatos.add(lbledad);
	    panelDatos.add(lblpuntos);
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

}
