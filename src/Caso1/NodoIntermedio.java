package Caso1;

import java.util.concurrent.ThreadLocalRandom;

public class NodoIntermedio extends Thread {
	
	
	//Constantes
	private final static String FIN="FIN";
	
	//Atributos
	//De donde obtengo mensajes y adonde los voy a mandar
	private final Buffer bufferEntrada;
	private final Buffer bufferSalida;
	private final int nivel;
	private final int proceso;
	
	public NodoIntermedio(Buffer bufferEntrada, Buffer bufferSalida, int proceso, int nivel)
	{
		this.bufferEntrada = bufferEntrada;
		this.bufferSalida = bufferSalida;
		this.nivel =nivel;
		this.proceso = proceso;

	}
	
	
	public void producir()
	{
		boolean centinela = true; 

		while(centinela)
		{
			String mensajeObtener = bufferEntrada.descargarBuffer();
			//Simular procesos (espera un tiempo entre 50 y 500 milisegundos
			int tiempo=ThreadLocalRandom.current().nextInt(50, 500);
			try {
				Thread.sleep(tiempo);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(mensajeObtener.equals(FIN)) 
			{

				
				//Procesar
				System.out.println("Proceso " +  proceso + " del Nivel " + nivel+ " --> " + FIN);
				bufferSalida.cargarBuffer(FIN);
				//Caso de parada
				centinela = false;
			}
			else
			{
				mensajeObtener+= "T"+ nivel+proceso;
				//Proceso Terminado
				String mensajeId = mensajeObtener.substring(0, mensajeObtener.indexOf("T"));
				System.out.println("Mensaje " + mensajeId + " procesado a: " + mensajeObtener);
				bufferSalida.cargarBuffer(mensajeObtener);
			}
						
		}
	}
	
	public void run()
	{
		producir();
	}

}
