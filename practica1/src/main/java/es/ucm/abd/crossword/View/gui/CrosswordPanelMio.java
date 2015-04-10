package es.ucm.abd.crossword.View.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import es.ucm.abd.crossword.CrosswordPanel;
import es.ucm.abd.crossword.CrosswordPanelEventListener;
import es.ucm.abd.crossword.Controller.Controller;
import es.ucm.abd.crossword.Model.ContienePalabra;
import es.ucm.abd.crossword.Model.Palabra;

public class CrosswordPanelMio extends JFrame{
	
	private ArrayList<Palabra> listWord;
	private boolean horizontal;
	private JLabel lblLetras; 
	private JTextField txtRespuesta;
	private JTextArea txtDescripcion;
	private JLabel lblfoto;  
	private JButton btnAccept;
	private JButton btnAyuda;
	private static String palabra;
	private MessageDialog messageDialog;
	private CrosswordPanel<ContienePalabra> panel;
	private JScrollPane jScrollPane;
	private List<ContienePalabra> lista;
	ArrayList<String> listaRespCorrectas;
	private Controller s_ctrl;
	private String nameUsuario;
	private String tituloCruci;
	private String fecha;
	private int numLetras;
	final private String src = "img/no_foto.png";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CrosswordPanelMio(ArrayList<Palabra> listWord, String nameUsuario, String tituloCruci){
		this.messageDialog = new MessageDialog();
		this.listWord = listWord;
		this.nameUsuario = nameUsuario;
		this.tituloCruci = tituloCruci;
		this.s_ctrl = new Controller();
		listaRespCorrectas = new ArrayList<String>();


		build();
		txtDescripcion.setEnabled(false);
		cargarRespuestas();

	}

	/**
	 * metodo encargado de construir el panel
	 */
	private void build() {
		
		// Creamos la lista inicial con tres ContienePalabras
		lista = new LinkedList<ContienePalabra>();
		for(int i=0;listWord.size()>i;i++){
			if(listWord.get(i).getOrientacion().equals("H")){
				horizontal = true;
			}else{
				horizontal = false;
			}
			final ContienePalabra word = new ContienePalabra(listWord.get(i).getPosx(),listWord.get(i).getPosy(),listWord.get(i).getSecuencia().toUpperCase(),horizontal, listWord.get(i).getEnunciado(),listWord.get(i).getPista());
			lista.add(word);
		}

		// Creamos el CrosswordPanel a partir de la lista.
		// Lo incrustamos en un JScrollPane para obtener barras de desplazamiento
		jScrollPane = new JScrollPane();
		this.add(jScrollPane);		
		panel = new CrosswordPanel<ContienePalabra>(jScrollPane, lista);
		jScrollPane.setViewportView(panel);
		
		claseSelect();
		
        JPanel panelSur 	  = new JPanel(new GridLayout(2,1));
        JPanel panelPistas    = new JPanel();
        JPanel panelRespuesta = new JPanel();

		lblfoto = new JLabel();
		lblfoto.setIcon(new ImageIcon(Login.class.getResource(src)));
		panelPistas.add(lblfoto);

		txtDescripcion = new JTextArea(4,30);
		txtDescripcion.setFont(new Font("Courier", Font.BOLD, 20));
		panelPistas.add(txtDescripcion);
        
		lblLetras = new JLabel("Letras:");
		panelRespuesta.add(lblLetras);

		txtRespuesta = new JTextField(20);
		panelRespuesta.add(txtRespuesta);

		btnAccept = new JButton("Aceptar");
		botonAceptar();
		panelRespuesta.add(btnAccept);
		
		btnAyuda = new JButton("Enviar a amigo...");
		
		bloquearTextos();
		panelRespuesta.add(btnAyuda);
		this.add(panelSur, BorderLayout.SOUTH);
		panelSur.add(panelPistas, BorderLayout.NORTH);
		panelSur.add(panelRespuesta, BorderLayout.SOUTH);

		this.setVisible(true);
		this.setSize(700, 900);	
	}
	
	private void claseSelect() {
		// Registramos los manejadores de eventos del CrosswordPanel
        panel.addEventListener(new CrosswordPanelEventListener<ContienePalabra>() {
            public void wordSelected(CrosswordPanel<ContienePalabra> source, ContienePalabra newWord) {
            	int cont=0;
            	numLetras = newWord.getWord().length();
            	boolean encontrada=false;
        		cargarRespuestas();
                lblLetras.setText(numLetras+" Letras:");
				txtDescripcion.setText(newWord.getEnunciado());

				while(cont < listaRespCorrectas.size()){
					if(newWord.getWord().equals(listaRespCorrectas.get(cont).toUpperCase())){
						encontrada=true;
					}
					cont++;            		
				}
				
				if(encontrada == true){
					bloquearTextos();
				}else{
					desbloquearTextos();
					txtRespuesta.setText("");
				}

				if(newWord.getFoto() != null){
					lblfoto.setIcon(new ImageIcon(newWord.getFoto()));
				}else{
					lblfoto.setIcon(new ImageIcon(Login.class.getResource(src)));
				}
				palabra = newWord.getWord();
            }

            public void cellSelected(CrosswordPanel<ContienePalabra> source, Point newCell) {
                if (newCell != null) {
            		lblfoto.setIcon(new ImageIcon(Login.class.getResource(src)));
                	txtRespuesta.setText("");
                	txtDescripcion.setText("");
            		lblLetras.setText("Letras:");
                    bloquearTextos();
                }
            }

            public void deselected(CrosswordPanel<ContienePalabra> source) {
            	bloquearTextos();
            }
        });
		
	}


	private void botonAceptar(){
		btnAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean correcto=false;
				int index = 0;
				if(txtRespuesta.getText().equals("")){
					messageDialog.reportMessage("false:Debes escribir una respuesta.");
				}else{
					index = comparacion();
					if(txtRespuesta.getText().toUpperCase().equals(palabra)){
						panel.showWord(lista.get(index));
						correcto=true;
						
					}else{
						messageDialog.reportMessage("false:La respuesta "+txtRespuesta.getText()+" es incorrecta.");
						correcto=false;
					}
					fecha= getFechaActual();
					s_ctrl.insertarRespuesta(nameUsuario, tituloCruci, listWord.get(index).getId(), txtRespuesta.getText(), correcto, fecha);
				}
			}
		});
	}
	
	private int comparacion(){
		int i=0;
		while(i< lista.size()){
			if(lista.get(i).getWord().toUpperCase().equals(palabra)){
				return i;
			}
			i++;
		}
		return -1;
	}
	
	private static String getFechaActual() {
	    Date ahora = new Date();
	    SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd- hh:mm:ss");
	    return formateador.format(ahora);
	}
	
	private void cargarRespuestas(){
		listaRespCorrectas = s_ctrl.cargarRespuestas(nameUsuario, tituloCruci);
			for(int i=0; i < listaRespCorrectas.size(); i++){
				for(int j=0;j <lista.size(); j++){
					if(listaRespCorrectas.get(i).toUpperCase().equals(lista.get(j).getWord())){
						panel.showWord(lista.get(j));
					}
				}
			}
	}
	
	private void bloquearTextos(){
		txtRespuesta.setEnabled(false);
		btnAccept.setEnabled(false);
		btnAyuda.setEnabled(false);	
	}
	
	private void desbloquearTextos(){
		txtRespuesta.setEnabled(true);
		btnAccept.setEnabled(true);
		btnAyuda.setEnabled(true);	
	}
	
}
