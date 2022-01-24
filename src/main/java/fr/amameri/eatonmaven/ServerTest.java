package fr.amameri.eatonmaven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerTest {
	private int slavePort;
	private int clientPort;
	private SlaveThread slaveThread;
	private ClientThread clientThread;
	private boolean running = false;
	public int slaveConnected; // Slave connection counter

	public ServerTest(int slavePort, int clientPort) {
		this.slavePort = slavePort;
		this.clientPort = clientPort;
		this.slaveConnected = 0;
	}

	public void startServer() {
		try {
			this.slaveThread = new SlaveThread(slavePort);
			this.clientThread = new ClientThread(clientPort);
			System.out.println("En attente de connexion client / slave");
			slaveThread.start();
			clientThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopServer() {
		running = false;
		this.slaveThread.interrupt();
		this.clientThread.interrupt();

	}

	class SlaveThread extends Thread {
		private ServerSocket slaveSocket;

		SlaveThread(int slavePort) throws IOException {
			this.slaveSocket = new ServerSocket(slavePort);
		}

		@Override
		public void run() {
			running = true;
			while (running) {
				try {
					// Call accept() to receive the next connection
					Socket slSocket = slaveSocket.accept();
					System.out.println("Une nouvelle connexion a été créée Slave");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class ClientThread extends Thread {
		private ServerSocket clientSocket;

		ClientThread(int clientPort) throws IOException {
			this.clientSocket = new ServerSocket(clientPort);

		}

		public void run() {
			running = true;
			int total = 0;

			List<Integer> list = new ArrayList<Integer>();

			while (running) {
				try {
					Socket clSocket = clientSocket.accept();
					BufferedReader in = new BufferedReader(new InputStreamReader(clSocket.getInputStream()));
					System.out.println("Une nouvelle connexion a été créée Client");

					String inputLine;
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
		ServerTest server = new ServerTest(24000, 15000);
		server.startServer();
		// Automatically shutdown in 1 minute
		try {
			Thread.sleep(60000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		server.stopServer();
	}
}