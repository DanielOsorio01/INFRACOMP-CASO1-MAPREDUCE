package Caso1;

import java.util.concurrent.ThreadLocalRandom;

public class Productor extends Thread{
	
	//Constantes 
	private final static String FIN="FIN";
	
	//Atibutos
	private final Buffer bufferSalida;
	private final int producir;
	private final int procesosMaximos;

	//Constructor
	public Productor(Buffer buffer, int producir, int procesosMaximos)
	{
		this.bufferSalida = buffer;
		this.producir =  producir;
		this.procesosMaximos = procesosMaximos;
	}
	
	public int darProducir() {
		return producir;
	}
	
	//Métodos
	private void generarMensaje()
	{
		int producidos = 0;
		while(producidos < producir + 1)
		{

			//System.out.println("Estoy produciendo");
			if(bufferSalida.hayCapacidad())
			{
				producidos ++;
				//  un thread debe esperar un tiempo aleatorio de entre 50 y 500 milisegundos
				// antes de intentar enviar el mensaje.
				int tiempo=ThreadLocalRandom.current().nextInt(50, 500);//Simular procesos
				try {
					Thread.sleep(tiempo);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (producir + 1 == producidos)
				{
					//Proceso Terminado
					for(int i =0; i < procesosMaximos; i++)
					{
						bufferSalida.cargarBuffer(FIN);
					}
					System.out.println("Productor --> Fin");
					return;
				}
				else
				{
					bufferSalida.cargarBuffer("M" +  producidos);
					System.out.println("Mensaje 'M" + producidos + "': Producido");
				}
			}
			else {
				Productor.yield();
			}
		}
	}
	
	
	public void run()
	{
		generarMensaje();
		
		
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
