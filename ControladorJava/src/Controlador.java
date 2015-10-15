import java.awt.event.*;

import javax.swing.JTextArea;

public class Controlador {
	
	private ModeloTension modelo;
	private TextoListener textoListener;
	
	public Controlador(ModeloTension modelo) {
		this.modelo = modelo;
	}
	
	private class EscuchaBotonSubir implements ActionListener {	
		public void actionPerformed(ActionEvent e) {	
			modelo.incrementar();
		}
	}
	
	public ActionListener getListenerBotonSubir() {
		return new EscuchaBotonSubir();
	}
	
	private class EscuchaBotonBajar implements ActionListener
	{	public void actionPerformed(ActionEvent e)
		{
			modelo.decrementar();
		}
	}

	public ActionListener getListenerBotonBajar() {
		return new EscuchaBotonBajar();
	}

	private class EscuchaBarra implements AdjustmentListener
	{	public void adjustmentValueChanged(AdjustmentEvent e)
		{	modelo.setTension(e.getAdjustable().getValue());
		}
	}

	public AdjustmentListener getListenerBarra() {
		return new EscuchaBarra();
	}
	
	public ActionListener getBotonListener(JTextArea texto){
		this.textoListener = new TextoListener(texto);
		return textoListener;
	}
	
	private class TextoListener implements ActionListener {
		
		private JTextArea texto;
		public TextoListener(JTextArea texto){
			this.texto = texto;			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Aplicar tension: " + texto.getText());
			double tension = Double.parseDouble(texto.getText());
			modelo.setTension(tension);
		}
	}
	
}