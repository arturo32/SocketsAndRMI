package br.ufrn.imd;

import com.sun.jdi.BooleanType;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	private static final int PORT = 5431;
	private static final boolean TRACE_MODE = false;

	private static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		System.out.println(path);
		return path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
	}

	private static Bot configureBot() {
		String resourcesPath = getResourcesPath();
		System.out.println(resourcesPath);
		MagicBooleans.trace_mode = TRACE_MODE;
		return new Bot("super", resourcesPath);
	}

	public static void main(String[] args) {
		ServerSocket server;
		try {
			// Connecting to port 5431 with a TCP socket
			server = new ServerSocket(PORT);

			// Waiting for client to accept connection
			Socket client = server.accept();

			// Writing
			PrintWriter output = new PrintWriter(client.getOutputStream());

			// Creating bot
			Bot bot = configureBot();
			Chat chatSession = new Chat(bot);
			bot.brain.nodeStats();

			// Reading client
			BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String request = input.readLine();

			while(!request.equals("bye")) {
				// Generating response
				String response = chatSession.multisentenceRespond(request);
				response = response.replaceAll("&lt;", "<");
				response = response.replaceAll("&gt;", ">");

				// Writing bot response
				output.println(response);
				output.flush();

				// Reading client response
				request = input.readLine();
			}

			// Closing
			bot.writeQuit();
			output.close();
			input.close();
			client.close();
			server.close();
		} catch (IOException ex) {
			// Probably port already in use
			System.err.println("ERROR IN OPENING PORT " + PORT);
			ex.printStackTrace();
			System.exit(1);
		}
		
	}
}