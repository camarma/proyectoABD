package es.ucm.abd.crossword.View.gui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

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
	
	public PanelAyuda(){
			
			
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
		add(deletePeticion);

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
		return 0;
	}

	/**
	 * metodo encargado de pintar la tabla con las columnas y filas de la tabla 
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return null;
	}
}
	
}
