import java.util.Observable;
import java.util.Observer;

public class VistaConsola implements Observer
{	
	private ModeloTension modelo; //referencia al modelo

	//Constructor de la vista
	public VistaConsola(ModeloTension modelo) {	
		// Conectamos esta vista con el modelo
		this.modelo = modelo;
		this.modelo.addObserver(this); 
	}

	//Metodo que es llamado por el modelo al actualizarse el mismo
	public void update(Observable t, Object o) {	
		System.out.println("La tension es: " + modelo.getTension() + " volts.");
	}
		
}
