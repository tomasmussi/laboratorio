import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.OutputStream;
import java.util.Enumeration;


public class ModeloTension extends java.util.Observable {

	private static final double INCREMENTO_TENSION = 5;
	private static final int INCREMENTO_RESISTENCIA = 40; // 40 ohms es el minimo incremento
	private double tension;
	private double resistencia; // Empieza con 140 ohms
	private static final double R1 = 220;
	
    //Variables de conexión
    private OutputStream output = null;
    private SerialPort serialPort;
    private final String PUERTO="/dev/ttyACM0";
    
    private static final int TIMEOUT = 2000; //Milisegundos
    
    private static final int DATA_RATE=9600;
    
    
    public ModeloTension() {
    	resistencia = 140; // Empieza con 140 ohms
    	calcularNuevaTension();
    	inicializarConexion();
    	ActualizarObservadores();
    }
	
	public double getTension(){
		return tension;
	}
	
	public void setTension(double tension){
		if (tension <= 0 || tension >= 30){
			return;
		}
		double nuevaResistencia = calcularNuevaResistencia(tension);
		boolean reemplazarResistencia = enviarNuevaResistencia(nuevaResistencia);
		if (reemplazarResistencia) {
			setResistencia(nuevaResistencia);
			this.tension = tension;
		}
		ActualizarObservadores();
	}
	
	private void setResistencia(double nuevaResistencia) {
		resistencia = nuevaResistencia;
		System.out.println("Nueva resistencia: " + resistencia);
	}
	
	private double calcularNuevaResistencia(double tension) {
		return ((tension / 1.25) - 1) * R1;
	}
	
	private void calcularNuevaTension() {
		tension = 1.25 * (1 + resistencia / R1);
	}
	
	private boolean enviarNuevaResistencia(double nuevaResistencia) {
		// Cada step de tension es de 40 ohms
		int cantidadNumeros = 0;
		String numeroEnvio = "";
		if (nuevaResistencia > resistencia) {
			numeroEnvio = "1";
		} else {
			numeroEnvio = "0";
		}
		cantidadNumeros = cantidadNumerosEnviar(nuevaResistencia);
		for (int i = 0; i < cantidadNumeros; i++) {
			enviarDatos(numeroEnvio);
			System.out.println("Se envia un " + numeroEnvio);
		}
		return cantidadNumeros != 0;
	}
	
	public int cantidadNumerosEnviar(double nuevaResistencia) {
		return (int) (Math.abs(nuevaResistencia - resistencia) / INCREMENTO_RESISTENCIA);
	}

	public void ActualizarObservadores() {
		setChanged();
		notifyObservers();
	}
	
	public void incrementar(){
		setTension(this.tension + INCREMENTO_TENSION);
	}
	
	public void decrementar(){
		setTension(this.tension - INCREMENTO_TENSION);
	}
	
	private void inicializarConexion(){
        CommPortIdentifier puertoID=null;
        Enumeration puertoEnum=CommPortIdentifier.getPortIdentifiers();
        
        while(puertoEnum.hasMoreElements()){
            CommPortIdentifier actualPuertoID=(CommPortIdentifier) puertoEnum.nextElement();
            if(PUERTO.equals(actualPuertoID.getName())){
                puertoID=actualPuertoID;
                break;
            }
        }
        
        if(puertoID==null){
            System.err.println("No se puede conectar al puerto");
            System.exit(-1);
        }
        
        try{
            serialPort = (SerialPort) puertoID.open(this.getClass().getName(), TIMEOUT);
            //Parámetros puerto serie
            
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            
            output = serialPort.getOutputStream();
        } catch(Exception e){
        	System.err.println("Error al inicializar el puerto");
            System.exit(1);
            
        }
    }
	
	private void enviarDatos(String datos){
        try{
            output.write(datos.getBytes());
        } catch(Exception e){
        	System.err.println("Error al enviar datos: " + datos);
            System.exit(1);
        }
    }

}
