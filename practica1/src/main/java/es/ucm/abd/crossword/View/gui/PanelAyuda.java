package es.ucm.abd.crossword.View.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.ucm.abd.crossword.Controller.Controller;
import es.ucm.abd.crossword.Model.Palabra;
import es.ucm.abd.crossword.Model.Usuario;

public class PanelAyuda extends JPanel{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scroll;
	private ModeloTabla modelo;
	private JTable tbCrucigrama;
	private JButton openCrucigrama; 
	private JButton deletePeticion; 
	private String nameUsuario;
	private Controller s_ctrl;
	private MessageDialog m_Dialog;
	private ArrayList<String> listaPeticiones;
	@SuppressWarnings("unused")
	private CrosswordPanelMio cp;
	private String titulo = "";
	private String usrAyudado = "";
	
	public PanelAyuda(String name){
		this.nameUsuario = name;
		s_ctrl = new Controller();
		m_Dialog = new MessageDialog();
		listaPeticiones = s_ctrl.listarPeticiones(nameUsuario);
		build();
	
	}
		
	
	private void build(){
		// Crear la tabla con un modelo personalizado
		modelo = new ModeloTabla();
		tbCrucigrama = new JTable(modelo);
		scroll = new JScrollPane(tbCrucigrama);
		openCrucigrama = new JButton("Abrir crucigrama");
		deletePeticion = new JButton("Descartar petición");
		tbCrucigrama.setFont(new Font("Courier",Font.PLAIN,16));
		add(scroll);
		add(openCrucigrama);
		botonAbrirCrucigrama();
		add(deletePeticion);
		botonEliminarPEticion();

	}
	
	private void botonAbrirCrucigrama(){
		openCrucigrama.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int filasSelec[] = tbCrucigrama.getSelectedRows();
				if(filasSelec.length == 0){
					m_Dialog.reportMessage("false:Debe seleccionar algun crucigrama");
				}else if(filasSelec.length>1){
					m_Dialog.reportMessage("false:Debe seleccionar solo un crucigrama");
				}else{
					titulo = String.valueOf(tbCrucigrama.getValueAt(tbCrucigrama.getSelectedRow(),1));
					usrAyudado = String.valueOf(tbCrucigrama.getValueAt(tbCrucigrama.getSelectedRow(),0));
					ArrayList<Palabra> listWord = s_ctrl.listaPalabrasByCrucigrama(titulo);
					ArrayList<String> respuestas = s_ctrl.cargarRespuestas(usrAyudado, titulo);
					cp = new CrosswordPanelMio(listWord, nameUsuario, titulo,respuestas,true,usrAyudado);
				}
			}
		});
	}
	
	private void botonEliminarPEticion(){
		deletePeticion.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub			
				int filasSelec[] = tbCrucigrama.getSelectedRows();
				if(filasSelec.length == 0){
					m_Dialog.reportMessage("false:Debe seleccionar algun crucigrama");
				}else if(filasSelec.length>1){
					m_Dialog.reportMessage("false:Debe seleccionar solo un crucigrama");
				}else{
					titulo = String.valueOf(tbCrucigrama.getValueAt(tbCrucigrama.getSelectedRow(),1));
					usrAyudado = String.valueOf(tbCrucigrama.getValueAt(tbCrucigrama.getSelectedRow(),0));
					s_ctrl.performEliminarPeticion(nameUsuario, titulo, usrAyudado);
					listaPeticiones = s_ctrl.listarPeticiones(nameUsuario);
					refreshView();
				}
			}
		});
	}
	
	void refreshView() {
		modelo.refresh();
	}
	
	private class ModeloTabla extends AbstractTableModel {
		
		
		private static final long serialVersionUID = 1L;
		
		String[] colNames = { "Usuario", "Crucigrama" };
		
		/**
		 * Constructora que llama al metodo refrescar
	 */
	ModeloTabla() {
		refresh();
	}
	
	/**
	 * metodo encargado de repintar la tabla con los nuevos valores
	 */
	public void refresh() {
		this.fireTableDataChanged();
	}

	/**
	 * metodo encargado de recoger el nombre de la columna
	 */
	public String getColumnName(int column){
		return colNames[column];
	}

	/**
	 * metodo que devuelve el numero de columnas
	 */
	@Override
	public int getColumnCount() {
		return 2;
	}
	
	/**
	 * metodo encargado de devolver el numero de celdas
	 */
	@Override
	public int getRowCount() {
		if(listaPeticiones == null){
			return 0;
		}
		else{
			return listaPeticiones.size();
		}
	}

	/**
	 * metodo encargado de pintar la tabla con las columnas y filas de la tabla 
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex==0)
			return listaPeticiones.get(rowIndex).split(":")[0];
		else
			return listaPeticiones.get(rowIndex).split(":")[1];
	}
}
	
}
