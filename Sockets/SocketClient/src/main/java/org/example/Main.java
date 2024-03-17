package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {
	private static final String IP = "127.0.0.1";
	private static final int PORT = 5431;
	public static void main(String[] args) {
		Socket client;

		try {
			// Listening on port 5431
			client = new Socket(IP, PORT);

			PrintWriter output = new PrintWriter(client.getOutputStream(), true);
			BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			BufferedReader clientReader = new BufferedReader(new InputStreamReader(System.in));

			String response;
			do {
				// Reading from console
				System.out.print("You: ");
				String request = clientReader.readLine();

				// Sending request
				output.println(request);
				output.flush();

				// Receiving answer
				response = input.readLine();
				System.out.println("Alice: " + response);

			} while(!response.equals("bye"));

			// Closing
			output.close();
			input.close();
			clientReader.close();
			client.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(1);
		}

	}
}