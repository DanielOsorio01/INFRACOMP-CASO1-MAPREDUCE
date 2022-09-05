package Caso1;

//Actua como end node de los proceso
public class Consumidor extends Thread{

	//Constantes
	private final static String FIN="FIN";
	
	//Buffer de entrada
	private final Buffer bufferEntrada;
	private final int consumir;
	
	//Considera que tambien debe consumir todos los mensajes de FIN
	private int consumidos;

	//Constructor
	public Consumidor(Buffer buffer, int consumir)
	{
		this.bufferEntrada = buffer;
		this.consumir = consumir;
	}


	public void obtenerMensaje()
	{
		int procesos = 0;
		while(consumidos <= consumir)
		{			
			if(bufferEntrada.hayItems())
			{
				String mensajeObtener = bufferEntrada.descargarBuffer();
				consumidos ++;
				if(mensajeObtener.equals(FIN))
				{
					procesos ++;
					System.out.println("Proceso " + procesos + ", Mensaje fin: "+ mensajeObtener);
					if (consumir == consumidos)
					{
						System.out.println("ya consumi todos: " + "Consumidor --> " + mensajeObtener);
						return;
					}
				}
				else
					System.out.println("Mensaje: " + mensajeObtener + " consumido: " + consumidos);
			}
			else {
				Thread.yield();
			}

		}
		System.out.println("llegue");
	}
	
	public void run()
	{
		obtenerMensaje();
	}

}

