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
import javax.swing.JTextField;

import es.ucm.abd.crossword.Controller.Controller;
import es.ucm.abd.crossword.Model.Usuario;


public class PanelAmigos extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnaddAmigo;
	private JButton btndeleteAmigo;
	private JScrollPane scroll;
	private JList<String> lst;
	private JList<String> lstAmigos;
	private DefaultListModel<String> modeloListaAmigos;
	private DefaultListModel<String> modeloLista;
	private JFrame ventana;
	private JButton btnAnadir;
	private JButton btnBuscar;
	private JTextField txtFind;
	private ArrayList<String> listaUsuarios;
	private ArrayList<String> listaAmigosDe;
	private Controller s_ctrl;
	private String nameUsuario;
	private MessageDialog messageDialog;
	
	public PanelAmigos(String name){
		this.nameUsuario = name;
		this.messageDialog = new MessageDialog();
		s_ctrl = new Controller();
		listaAmigosDe = s_ctrl.performListarAmigosDe(nameUsuario);
		build();
		refreshPanel();
	}

	 private void build(){
		modeloListaAmigos = new DefaultListModel<String>();
		lstAmigos = new JList<String>(modeloListaAmigos);
		scroll = new JScrollPane(lstAmigos);
		scroll.setPreferredSize(new Dimension(450, 430));
		btnaddAmigo = new JButton("Anadir amigo");
		btndeleteAmigo = new JButton("Borrar amigo");
		lstAmigos.setFont(new Font("Courier",Font.PLAIN,16));
		
		add(scroll);
		add(btnaddAmigo);
		anadirAmigos();
		add(btndeleteAmigo);
		borrarAmigos();
	 }
	 
	 public void anadirAmigos(){
		 btnaddAmigo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ventanaBusqueda();
				}
			});
		}
	 private void ventanaBusqueda(){
			ventana = new JFrame("Buscar Amigos");
			JPanel centerPanel = new JPanel();
			modeloLista = new DefaultListModel<String>();
			lst = new JList<String>(modeloLista);
			scroll = new JScrollPane(lst);
			scroll.setPreferredSize(new Dimension(500, 500));
			lst.setFont(new Font("Courier",Font.PLAIN,16));
				
			btnAnadir = new JButton("Anadir");
			btnBuscar = new JButton("Buscar");
			txtFind = new JTextField(30);
			centerPanel.add(txtFind);
			centerPanel.add(btnBuscar);
			findAmigos();
			centerPanel.add(scroll);
			centerPanel.add(btnAnadir);
			anadriAmigos();
			ventana.add(centerPanel, BorderLayout.CENTER);
			ventana.setSize(500, 740);
			ventana.setVisible(true);
			
	}
	 
		public void findAmigos(){
			btnBuscar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					listaUsuarios = s_ctrl.performFindFriends(txtFind.getText(),nameUsuario);
					refresh();
				}
			});
		}
		
		public void anadriAmigos(){
			btnAnadir.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String valor = lst.getSelectedValue();
					if(valor!=null){
						String parts[] = valor.split("   ");
						String nameAmigo = parts[3].trim();
						String ack = s_ctrl.performAsignarAmistad(nameUsuario,nameAmigo);
						if(ack.equals("true")){
							listaAmigosDe = s_ctrl.performListarAmigosDe(nameUsuario);
							refreshPanel();
							ventana.dispose();
						}else{
							messageDialog.reportMessage(ack);
						}
					}
				}
			});
		}
		
		public void borrarAmigos(){
			btndeleteAmigo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String valor = lstAmigos.getSelectedValue();
					if(valor!=null){
						String parts[] = valor.split("   ");
						String nameAmigo = parts[3].trim();
						String ack = s_ctrl.performEliminarAmistad(nameUsuario,nameAmigo);
						if(ack.equals("true")){
							listaAmigosDe = s_ctrl.performListarAmigosDe(nameUsuario);
							refreshPanel();
						}else{
							messageDialog.reportMessage(ack);
						}
					}
				}
			});
		}
		
		public void refresh(){
			String programLista[] = new String[listaUsuarios.size()];
	    	modeloLista.clear();
	        for (int i=0;i<listaUsuarios.size();i++){
	        	programLista[i] = "   "+(i+1)+"-   Nombre:   "+listaUsuarios.get(i);
	        	modeloLista.addElement(programLista[i]+"\n");
	        }
		}
		
		public void refreshPanel(){
			String programLista[] = new String[listaAmigosDe.size()];
			modeloListaAmigos.clear();
	        for (int i=0;i<listaAmigosDe.size();i++){
	        	programLista[i] = "   "+(i+1)+"-   Nombre:   "+listaAmigosDe.get(i);
	        	modeloListaAmigos.addElement(programLista[i]+"\n");
	        }
		}
}
