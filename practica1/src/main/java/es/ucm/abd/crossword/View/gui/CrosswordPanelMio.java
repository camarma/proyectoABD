package es.ucm.abd.crossword.View.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import es.ucm.abd.crossword.CrosswordPanel;
import es.ucm.abd.crossword.CrosswordPanelEventListener;
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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CrosswordPanelMio(ArrayList<Palabra> listWord){
		this.messageDialog = new MessageDialog();
		this.listWord = listWord;
		System.out.println(listWord);
		build();
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
		
		// Registramos los manejadores de eventos del CrosswordPanel
        panel.addEventListener(new CrosswordPanelEventListener<ContienePalabra>() {
            public void wordSelected(CrosswordPanel<ContienePalabra> source, ContienePalabra newWord) {
                if (newWord != null) {
                    //System.out.println("Seleccionada la ContienePalabra " + newWord.getWord()+"  "+newWord.getEnunciado());
                	txtDescripcion.setText(newWord.getEnunciado());
                	lblLetras.setText(newWord.getWord().length()+" Letras:");
                	palabra = newWord.getWord();
                } else {
                    System.out.println("Deseleccionada ContienePalabra");
                }
            }

            public void cellSelected(CrosswordPanel<ContienePalabra> source, Point newCell) {
                if (newCell != null) {
                    System.out.println("Seleccionada la celda (" + newCell.x + ", " + newCell.y + ")");
                } else {
                    System.out.println("Deseleccionada celda");
                }
            }

            public void deselected(CrosswordPanel<ContienePalabra> source) {
                System.out.println("DeselecciÃ³n!");
            }
        });
        JPanel panelSur 	  = new JPanel(new GridLayout(2,1));
        JPanel panelPistas    = new JPanel();
        JPanel panelRespuesta = new JPanel();
        
		lblfoto = new JLabel("Pista");
		panelPistas.add(lblfoto);

		txtDescripcion = new JTextArea(4,45);
		//txtDescripcion.setText(enunciado);
		panelPistas.add(txtDescripcion);
        
		lblLetras = new JLabel("Letras:");
		panelRespuesta.add(lblLetras);

		txtRespuesta = new JTextField(20);
		panelRespuesta.add(txtRespuesta);

		btnAccept = new JButton("Aceptar");
		botonAceptar();
		panelRespuesta.add(btnAccept);
		
		btnAyuda = new JButton("Enviar a amigo...");
		//botonNuevoUsuario();
		panelRespuesta.add(btnAyuda);
		this.add(panelSur, BorderLayout.SOUTH);
		panelSur.add(panelPistas, BorderLayout.NORTH);
		panelSur.add(panelRespuesta, BorderLayout.SOUTH);

		this.setVisible(true);
		this.setSize(600, 750);	
	}
	
	private void botonAceptar(){
		btnAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int index = 0;
				if(txtRespuesta.getText().equals("")){
					messageDialog.reportMessage("false:Debes escribir una respuesta.");
				}else{
					System.out.println(txtRespuesta.getText().toUpperCase()+" = "+palabra);
					if(txtRespuesta.getText().toUpperCase().equals(palabra)){
						index = comparacion();
						panel.showWord(lista.get(index));
					}else{
						messageDialog.reportMessage("false:La respuesta "+txtRespuesta.getText()+" es incorrecta.");
					}
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
}
