package it.mat.unical.Helion_Prime.Online;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	private Socket client;

	BufferedReader in;
	private DataOutputStream out;
	boolean isMultiplayerGame;

	public Client(String address, int port, boolean b) {

		try {
			client = new Socket(address, port);
			out = new DataOutputStream(client.getOutputStream());
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.isMultiplayerGame = b;

	}

	public void sendMessage(String message) throws IOException {

		out.writeBytes(message + '\n');

	}

	public static int getDefaultNumberPort() {
		return 10001;
	}

	public String recieveMessage() throws IOException {

		return in.readLine();

	}

	public void closeConnection() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean isMultiplayerGame() {
		return this.isMultiplayerGame;
	}

	public boolean isInRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	public void flush() {
		try {
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
