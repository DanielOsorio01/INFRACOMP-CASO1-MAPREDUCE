package Caso1;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el número de niveles (recomendado 3): ");
		int niveles = scanner.nextInt();
		System.out.println("Ingrese el número de procesos (recomendado 3): ");
		int procesos = scanner.nextInt();
		System.out.println("Ingrese el tamaño de los buzones de los extremos: ");
		int tamanioExtremos = scanner.nextInt();
		System.out.println("Ingrese el tamaño de los buzones intermedios: ");
		int tamanioMedios = scanner.nextInt();
		System.out.println("Ingrese el número de mensajes a producir (Recomendado 10): ");
		int producir = scanner.nextInt();
		scanner.close();
		Buffer bufferInicio = new Buffer(tamanioExtremos);
		Buffer bufferFinal = new Buffer(tamanioExtremos);
		//Atributos
		Productor productor = new Productor(bufferInicio, producir, procesos);
		Consumidor consumidor = new Consumidor(bufferFinal, productor.darProducir() + procesos);


		//Empiezo los threads
		Buffer bufferEntrada;
		Buffer bufferSalida = null;
		NodoIntermedio procesadorInterno = null;
		for (int i = 1; i <= procesos; i++) {
			for (int n = 1; n <= niveles; n++) {
				if (niveles == 1){
					// Caso cuando solo hay un nivel, el buffer inicial es el productor y el final es el consumidor
					bufferEntrada = bufferInicio;
					bufferSalida = bufferFinal;
				}
				else if (n == 1) {
					// Los procesos del primer nivel reciben el buffer inicial
					bufferEntrada = bufferInicio;
					bufferSalida = new Buffer(tamanioMedios);
				}
				else if (n == niveles) {
					// Los procesos del último nivel de transformación se conectan con el buffer final
					bufferEntrada = bufferSalida;
					bufferSalida = bufferFinal;
				}
				else{
					// El buffer de entrada del proceso intermedio es el buffer de salida del nivel anterior
					bufferEntrada = bufferSalida;
					// Se crea un nuevo buffer de salida y su referencia sirve como entrada en el siguiente nivel
					bufferSalida = new Buffer(tamanioMedios);
					
				}
				// Se crea el proceso
				procesadorInterno = new NodoIntermedio(bufferEntrada, bufferSalida, i, n);
				procesadorInterno.start();
			}

		}
		productor.start();
		consumidor.start();
	}
}
