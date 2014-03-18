package Online;

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

	public Client() {

		try {
			client = new Socket("localHost", 7777);
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

	}

	public void sendMessage(String message) {
		try {
			out.writeBytes(message + '\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String recieveMessage() {
		String message = null;
		try {
			message = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}
}
