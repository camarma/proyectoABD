package es.ucm.abd.crossword.View.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.toedter.calendar.JCalendar;

/**
 * Clase para pintar un calendario
 * @author Alberto y George
 *
 */
public class PanelCalendar extends JPanel{
	
	private JCalendar jc;
	private JFrame ventana;
	private static final long serialVersionUID = 1L;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private MessageDialog messageDialog;
	private String fechaActual;
	private UserPanel up;
	private JLabel lblFechaTitulo;
	private JLabel lblFechaDatos;

	/**
	 * La constructora recibe la fecha de naciemiento actual del usuario logado por si la quiere cambiar
	 * @param fecha la fecha de nacimiento del usuario logado
	 */
	public PanelCalendar(String fecha){
		jc = new JCalendar();
		up = new UserPanel();
		up.lblEdadMod = new JLabel();
		this.messageDialog = new MessageDialog();
		this.fechaActual = fecha;
		build();
		aceptar();
	}
	
	/**
	 * metodo encargado de construir el panel
	 */
	private void build(){

		JPanel panelCalendar = new JPanel();
		JPanel panelBotones = new JPanel();
		JPanel datos = new JPanel();	
		JPanel fecha = new JPanel();
		//SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		ventana = new JFrame("Calendario");
		
		btnAceptar = new JButton("Aceptar");
		btnCancelar = new JButton("Cancelar");
		lblFechaTitulo = new JLabel("Fecha de nacimiento");
		lblFechaDatos = new JLabel(fechaActual);
		panelCalendar.add(jc);	
		panelBotones.add(btnAceptar);
		panelBotones.add(btnCancelar);
		fecha.add(lblFechaTitulo);
		fecha.add(lblFechaDatos);
		datos.add(fecha, BorderLayout.NORTH);
		datos.add(panelCalendar,BorderLayout.CENTER);
		//datos.add(panelCalendar,BorderLayout.SOUTH);
		datos.add(panelBotones, BorderLayout.SOUTH);
		ventana.add(datos,BorderLayout.CENTER);
		//ventana.add(botones,BorderLayout.SOUTH);
		ventana.setSize(500, 400);
		ventana.setVisible(true);
	}
	
	/**
	 * Boton para acceptar el cambio de de fecha de nacimiento
	 */
	public void aceptar(){
		btnAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int year = jc.getYearChooser().getYear();
				String fechaNueva="";
				if(year>1900 && year<2016){
					fechaNueva = jc.getYearChooser().getYear() + "-" + (jc.getMonthChooser().getMonth()+1) + "-" + jc.getDayChooser().getDay();
					up.setFecha(fechaNueva);
					ventana.dispose();
				}else{
					messageDialog.reportMessage("false:El formato de la fecha es incorrecto.");
				}
			}
		});
	}	
}
