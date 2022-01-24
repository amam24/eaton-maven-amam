package fr.amameri.eatonmaven;

import java.io.IOException;
import java.io.PrintWriter; //envoyer le texte saisi
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ClientTest {
	private static final int NUMBERS = 15;
	private static final int AMPLITUDE = 100;

	

	public static void main(String[] args) throws IOException {
		String serverHostname = "127.0.0.1"; //

		System.out.println("En attente de connexion au serveur " + serverHostname + " au port 24000.");

		ClientOut clientOut = new ClientOut(serverHostname);
		clientOut.start();
		ClientIn clientIn = new ClientIn(serverHostname);
		clientIn.start();
	}

	public static class ClientOut extends Thread {
		private Socket echoSocket;
		private PrintWriter writer;//variable qui gère le flux d'écriture out
		private ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

		ClientOut(String serverHostname) throws IOException {
			this.echoSocket = new Socket(serverHostname, 24000);
			this.writer = new PrintWriter(echoSocket.getOutputStream(), true);
			;

			exec.scheduleAtFixedRate(new Runnable() {

				@Override
				public void run() {
					System.out.println("1 client connecté!");
					Random rnd = new Random();
					try {
						for (int i = 0; i < NUMBERS; i++) {
							int num = rnd.nextInt(AMPLITUDE);
							System.out.println(num);
							writer.println(num);
							TimeUnit.SECONDS.sleep(1);
							
						}
						//sortir de la boucle si le client a déconecté
		                System.out.println("Client déconecté");
		                echoSocket.close();
						writer.close();//                //fermer le flux et la session socket
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}, 0, 3, TimeUnit.SECONDS);
		}

	}
}