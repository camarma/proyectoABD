package es.ucm.abd.crossword.View.gui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class PanelAmigos extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton addAmigo;
	private JButton deleteAmigo;
	private JScrollPane scroll;
	private JList<String> lstAmigos;
	private DefaultListModel<String> modeloLista;
	
	public PanelAmigos(){
		build();
	}
	

	 private void build(){
		modeloLista = new DefaultListModel<String>();
		lstAmigos = new JList<String>(modeloLista);
		scroll = new JScrollPane(lstAmigos);
		scroll.setPreferredSize(new Dimension(2000, 430));
		addAmigo = new JButton("Añadir amigo");
		deleteAmigo = new JButton("Borrar amigo");
		lstAmigos.setFont(new Font("Courier",Font.PLAIN,16));
		
		add(scroll);
		add(addAmigo);
		add(deleteAmigo);
	 }
	 
}
