package es.ucm.abd.crossword.View.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

/**
 * Clase encargada de mostrar el panel principal para jugar, buscar amigos o ayudar al amigo en caso de que el lo solicite
 * @author Alberto y George
 *
 */
public class PanelPrincipal  extends JPanel{

	private static final long serialVersionUID = 1L;
	;
	private PanelCrucigramas lc;
	private PanelAmigos la;
	private PanelAyuda lay;
	/**
	 * Constructora para inicializar el toolbar panel con sus botones y llamar al metodo build
	 * @param gui recibe como entrada al controlador
	 */
	public PanelPrincipal(String nameUsuario){
		
		this.lc = new PanelCrucigramas(nameUsuario);
		this.la = new PanelAmigos(nameUsuario);
		this.lay = new PanelAyuda(nameUsuario);
		
		build();
	}
	
	/**
	 * metodo encargado de construir el panel
	 */
	private void build(){
		
		setBorder(new TitledBorder("Panel"));
		setLayout(new BorderLayout());
				
		JPanel panelCrucigramas = new JPanel(new BorderLayout());
		JPanel panelAmigos  = new JPanel(new BorderLayout());
		JPanel panelAyuda  = new JPanel(new BorderLayout());
		JPanel panelLista = new JPanel(new BorderLayout());
		add(panelLista);
		panelCrucigramas.add(lc);
		panelAmigos.add(la);
		panelAyuda.add(lay);
		JTabbedPane tp = new JTabbedPane();
	        tp.add("Crucigramas", panelCrucigramas);
	        tp.add("Amigos", panelAmigos);
	        tp.add("Peticiones de Ayuda", panelAyuda);
	        panelLista.add(tp);

	}
}
