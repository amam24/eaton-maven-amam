package fr.amameri.eatonmaven;

import java.io.BufferedReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ClientIn extends Thread {
	private BufferedReader in;
	private String serverHostname;

	public ClientIn(BufferedReader in) {
		this.in = in;
	}

	public ClientIn(String serverHostname) {
		this.serverHostname = serverHostname;
	}

	@Override
	public void run() {

	}
}