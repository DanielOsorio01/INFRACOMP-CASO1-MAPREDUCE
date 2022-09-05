package Caso1;

import java.util.ArrayList;

public class Buffer {

	//Atributos
	private int capacidad;
	private final ArrayList<String> mensajes;
	
	//Constructor
	public Buffer(int tamanioBuffer)
	{
		capacidad = tamanioBuffer;
		mensajes = new ArrayList<String>();
	}

	public synchronized boolean hayItems()
	{
		return !mensajes.isEmpty();
	}
	
	public synchronized boolean hayCapacidad()
	{
		return capacidad > 0;
	}
	
	
	public synchronized void cargarBuffer( String mensaje)
	{
		boolean centinela = true;
		
		while(centinela)
		{
			if(capacidad > 0)
			{
				capacidad --;
				notifyAll();	
				mensajes.add(mensaje);
				centinela = false;
			}
			else
			{
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public synchronized String descargarBuffer()
	{
		while (mensajes.isEmpty())
		{
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		capacidad ++;
		notifyAll();
		
		return mensajes.remove(0);
	}
}
