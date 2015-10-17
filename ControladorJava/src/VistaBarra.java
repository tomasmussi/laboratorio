import java.awt.Button;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextArea;

public class VistaBarra implements Observer
{	
	private ModeloTension modelo; //referencia al modelo
	private Frame frameTemp; //marco que contendr� los controles
	//private TextField textoTemp = new TextField(); //texto que mostrara la temperatura
	private Button botonSubir = new Button("Subir 5 Volts");  //boton para subir la temperatura
	private Button botonBajar = new Button("Bajar 5 Volts");  //boton para bajar la temperatura
	//barra para mostrar y controlar la temperatura
	
	private JTextArea textoTension = new JTextArea("20");
	private Button botonTension = new Button("Aplicar");
	private Scrollbar barraTension = new Scrollbar(Scrollbar.HORIZONTAL, 20, 1, 0, 30);

	//Clase auxiliar para escuchar el evento de cerrado de la ventana
	public static class CloseListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {	
			e.getWindow().setVisible(false);
			System.exit(0);
		}
	}

	//Constructor de la vista
	public VistaBarra(ModeloTension modelo, Controlador control) {	
		//armado de la ventana
		frameTemp = new Frame("Control de tension"); //creamos el marco
		frameTemp.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;	
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		frameTemp.add(new Label("Tension"),gbc);  //agregamos un titulo
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		frameTemp.add(textoTension, gbc);
		gbc.gridx = 1;
		frameTemp.add(botonTension, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		frameTemp.add(barraTension, gbc); //agregamos la barra
		Panel panelBotones = new Panel(); //creamos un panel para los botones
		panelBotones.add(botonSubir);  //agregamos el boton de "Subir" al panel
		panelBotones.add(botonBajar);  //agregamos el boton de "Bajar" al panel
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		frameTemp.add(panelBotones, gbc);  //agregamos el panel al marco
		
		frameTemp.setSize(400,150);  //seteamos las dimensiones del marco
		frameTemp.setResizable(false);		
		frameTemp.setVisible(true);  //mostramos el marco

		//agregamos el listener del evento de cerrado de la ventana		
		frameTemp.addWindowListener(new CloseListener());
		
		//agregamos el listener de los botones "Subir" y "Bajar"
		//Notar que los listeners se los pedimos al controlador
		botonSubir.addActionListener(control.getListenerBotonSubir());
		botonBajar.addActionListener(control.getListenerBotonBajar());
		botonTension.addActionListener(control.getBotonListener(textoTension));

		//agregamos el listener para cuando se mueve la barra
		//Notar que el listener se lo pedimos al controlador
		barraTension.addAdjustmentListener(control.getListenerBarra());

		// Conectamos esta vista con el modelo
		this.modelo = modelo;
		this.modelo.addObserver(this); 
	}

	//Metodo que es llamado por el modelo al actualizarse el mismo
	public void update(Observable t, Object o) {	
		double tension = modelo.getTension();
		textoTension.setText(String.valueOf(tension));
		barraTension.setValue((int)tension);
	}
		
}
