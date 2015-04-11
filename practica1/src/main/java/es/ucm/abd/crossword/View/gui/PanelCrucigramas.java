package es.ucm.abd.crossword.View.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import es.ucm.abd.crossword.Controller.Controller;
import es.ucm.abd.crossword.Model.Crucigrama;
import es.ucm.abd.crossword.Model.Palabra;

public class PanelCrucigramas extends JPanel{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scroll;
	private ModeloTabla modelo;
	private JTable tbCrucigrama;
	private JButton btnOpenCrucigrama; 
	private JButton btnfindCrucigrama; 
	private Controller s_ctrl;
	private ArrayList<Crucigrama> listaCrucigramas;
	private ArrayList<Integer> listaCrucigramasFiltrados;
	private ArrayList<Crucigrama> listaCrucigramasActvos;
	private String nameUsuario;
	private MessageDialog m_Dialog;
	private CrosswordPanelMio cp;
	JFrame ventana;
	JButton btnAnadir;
	JButton btnBuscar;
	JTextField txtFind;
	JScrollPane scrollLista;
	JList<String> lstCrucigramas;
	DefaultListModel<String> modeloLista;
	
	
	public PanelCrucigramas(String name){
		this.nameUsuario = name;
		s_ctrl = new Controller();
		m_Dialog = new MessageDialog();
		listaCrucigramasActvos = s_ctrl.listarCrucigramasActivos(nameUsuario);
		build();
	}
		
	
	private void build(){
		// Crear la tabla con un modelo personalizado
		modelo = new ModeloTabla();
		tbCrucigrama = new JTable(modelo);
		scroll = new JScrollPane(tbCrucigrama);
		btnOpenCrucigrama = new JButton("Abrir crucigrama");
		btnfindCrucigrama = new JButton("Buscar crucigrama");
		tbCrucigrama.setFont(new Font("Courier",Font.PLAIN,16));
		add(scroll);
		add(btnOpenCrucigrama);
		botonAbrirCrucigrama();
		add(btnfindCrucigrama);
		botonAnadirCrucigrama();
	}
	
	private void botonAnadirCrucigrama(){
		btnfindCrucigrama.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				listaCrucigramas = s_ctrl.listCrucigramas();
				ventanaBusqueda();
				refresh();
			}
		});
	}
	
	private void botonAbrirCrucigrama(){
		btnOpenCrucigrama.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int filasSelec[] = tbCrucigrama.getSelectedRows();
				if(filasSelec.length == 0){
					m_Dialog.reportMessage("false:Debe seleccionar algun crucigrama");
				}else if(filasSelec.length>1){
					m_Dialog.reportMessage("false:Debe seleccionar solo un crucigrama");
				}else{
					String valor =String.valueOf(tbCrucigrama.getValueAt(tbCrucigrama.getSelectedRow(),0));
					ArrayList<Palabra> listWord = s_ctrl.listaPalabrasByCrucigrama(valor);
					cp = new CrosswordPanelMio(listWord, nameUsuario, valor);
				}
			}
		});
	}
	
	private void ventanaBusqueda(){
		ventana = new JFrame("Load");
		JPanel centerPanel = new JPanel();
		modeloLista = new DefaultListModel<String>();
		lstCrucigramas = new JList<String>(modeloLista);
		scroll = new JScrollPane(lstCrucigramas);
		scroll.setPreferredSize(new Dimension(1000, 430));
		lstCrucigramas.setFont(new Font("Courier",Font.PLAIN,16));
			
		btnAnadir = new JButton("Anadir");
		btnBuscar = new JButton("Buscar");
		txtFind = new JTextField(30);
		centerPanel.add(txtFind);
		centerPanel.add(btnBuscar);
		findCrucigramas();
		centerPanel.add(scroll);
		centerPanel.add(btnAnadir);
		anadirCrucigramas();
		ventana.add(centerPanel, BorderLayout.CENTER);
		ventana.setSize(new Dimension(1000,800));
		//botonLoad();
		ventana.setVisible(true);
		
	}
	public void anadirCrucigramas(){
		btnAnadir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String valor = lstCrucigramas.getSelectedValue();
				//System.out.println("El nombre del valor: "+valor.g);
				if(valor!=null){
					String parts[] = valor.split("   ");
					String titulo = parts[3];
					s_ctrl.activarCrucigrama(titulo,nameUsuario);
					listaCrucigramasActvos = s_ctrl.listarCrucigramasActivos(nameUsuario);
					refreshView();
					ventana.dispose();
				}
			}
		});
	}
	
	public void findCrucigramas(){
		btnBuscar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				listaCrucigramasFiltrados = s_ctrl.performFindCrucigrama(txtFind.getText());
				refresh();
			}
		});
	}
	
	public void refresh(){
		if(listaCrucigramasFiltrados == null){
			String programLista[] = new String[listaCrucigramas.size()];
	    	modeloLista.clear();
	        for (int i=0;i<listaCrucigramas.size();i++){
	        	programLista[i] = "   "+(i+1)+"-   Titulo:   "+listaCrucigramas.get(i).getTitulo()+"   Fecha de Creación: "+listaCrucigramas.get(i).getFecha();
	        	modeloLista.addElement(programLista[i]+"\n");
	        }
		}else{
			int i=0,j = 0,cont = 0;
			String programLista[] = new String[listaCrucigramasFiltrados.size()];
			modeloLista.clear();
			while(listaCrucigramasFiltrados.size()>i){
				while(listaCrucigramas.size()>j){
					if(listaCrucigramasFiltrados.get(i)==listaCrucigramas.get(j).getId()){
			        	programLista[cont] = "   "+(cont+1)+"-   Titulo:   "+listaCrucigramas.get(j).getTitulo()+"   Fecha de Creación: "+listaCrucigramas.get(j).getFecha();
			        	modeloLista.addElement(programLista[cont]+"\n");
			        	cont++;
					}
					j++;
				}
				i++;
				j=0;
			}
		}
	}
	
	void refreshView() {
		modelo.refresh();
	}
	
	private class ModeloTabla extends AbstractTableModel {
			
	
			private static final long serialVersionUID = 1L;
			
			String[] colNames = { "Titulo", "Fecha" };
			
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
			if(listaCrucigramasActvos == null){
				return 0;
			}
			else{
				return listaCrucigramasActvos.size();
			}
		}

		/**
		 * metodo encargado de pintar la tabla con las columnas y filas de la tabla 
		 */
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if(columnIndex==0)
				return listaCrucigramasActvos.get(rowIndex).getTitulo();
			else
				return listaCrucigramasActvos.get(rowIndex).getFecha();
		}
	}
}
