package server;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicBooleans;
import shared.ChatService;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatServer implements ChatService {

    private Bot bot;
    private Chat chatSession;
    private static final boolean TRACE_MODE = false;

    private Bot configureBot() throws RemoteException {
        String resourcesPath = getResourcesPath();
        System.out.println(resourcesPath);
        MagicBooleans.trace_mode = TRACE_MODE;
        return new Bot("super", resourcesPath);
    }

    public ChatServer() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
        bot = configureBot();
        chatSession = new Chat(bot);
        bot.brain.nodeStats();
    }

    private static String getResourcesPath() {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        return path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
    }

    @Override
    public String talk(String message) throws RemoteException {
        String response = chatSession.multisentenceRespond(message);
        response = response.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        return response;
    }
}