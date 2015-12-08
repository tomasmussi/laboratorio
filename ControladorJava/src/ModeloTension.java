import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.OutputStream;
import java.util.Enumeration;


public class ModeloTension extends java.util.Observable {

	private static final double INCREMENTO = 5;
	private double tension = 20.0;
	private double resistencia = 140; // Empieza con 140 ohms
	
    //Variables de conexión
    private OutputStream output = null;
    private SerialPort serialPort;
    private final String PUERTO="/dev/ttyACM0";
    
    private static final int TIMEOUT=2000; //Milisegundos
    
    private static final int DATA_RATE=9600;
	
    public ModeloTension() {
    	inicializarConexion();
    }
	
	public double getTension(){
		return tension;
	}
	
	public void setTension(double tension){
		if (tension <= 0 || tension >= 30){
			return;
		}
		this.tension = tension;
		enviarNuevaResistencia();
		ActualizarObservadores();
	}
	
	private void enviarNuevaResistencia() {
		// Cada step de tension es de 40 ohms
		enviarDatos("0");
		System.out.println("Se envia un 0");
	}

	public void ActualizarObservadores() {
		setChanged();
		notifyObservers();
	}
	
	public void incrementar(){
		setTension(this.tension + INCREMENTO);
	}
	
	public void decrementar(){
		setTension(this.tension - INCREMENTO);
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
