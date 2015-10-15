public class Principal 
{	public static void main(String args[]) 
	{	
		// creamos el modelo
		ModeloTension temperatura = new ModeloTension();
		// creamos el controlador que atiende a las vistas
		Controlador control = new Controlador(temperatura);
		// creamos las vistas
		new VistaBarra(temperatura,control);
		new VistaConsola(temperatura);
		// esto es necesario para que las vistas se actualicen la 1ra vez
		temperatura.ActualizarObservadores();
	}
}

