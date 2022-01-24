package fr.amameri.eatonmaven;

import java.io.BufferedReader; //lire le texte reçu Ã  partir de l'émetteur
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket; //cette classe accepte les connexions venues des clients
import java.net.Socket; //cette classe permet de se connecter Ã  la machine distante.
import java.util.ArrayList;
import java.util.List;

public class ServerTest {
	private int clientPort;
	private ClientThread clientThread;
	private boolean running = false;

	public ServerTest(int clientPort) {

		this.clientPort = clientPort;
	}

	public void startServer() {
		try {
			this.clientThread = new ClientThread(clientPort);
			System.out.println("En attente de connexion client");
			clientThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopServer() {
		running = false;
		this.clientThread.interrupt();

	}

	class ClientThread extends Thread {
		private ServerSocket clientSocket;

		ClientThread(int clientPort) throws IOException {
			this.clientSocket = new ServerSocket(clientPort); // création du socket serveur qui porte le numéro de port
																// 24000

		}

		public void run() {
			running = true;
			int total = 0;

			List<Integer> list = new ArrayList<Integer>();

			while (running) {
				try {
					Socket clSocket = clientSocket.accept(); // acceptation des connexions entrantes
					BufferedReader in = new BufferedReader(new InputStreamReader(clSocket.getInputStream())); //gestion de flux de lecture
					System.out.println("Une nouvelle connexion a été créée Client");

					String inputLine;//message reçu
					while ((inputLine = in.readLine()) != null) {
						System.out.println("Client: " + inputLine);
						list.add(Integer.parseInt(inputLine));

					}
					for (Integer note : list) {
						total += note;
					}
					double moyenne = total / list.size();
					System.out.println("Moyenne: " + moyenne);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		ServerTest server = new ServerTest(24000);
		server.startServer();
		// Automatiquement shutdown dans 1 minute
		try {
			Thread.sleep(60000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		server.stopServer();
	}
}