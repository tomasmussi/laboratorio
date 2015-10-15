

public class ModeloTension extends java.util.Observable {

	private static final double INCREMENTO = 5;
	private double tension = 20.0;
	
	
	public double getTension(){
		return tension;
	}
	
	public void setTension(double tension){
		if (tension <= 0 || tension >= 30){
			return;
		}
		this.tension = tension;
		ActualizarObservadores();
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

}
