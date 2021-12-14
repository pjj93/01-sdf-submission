package sdf1;

import java.net.Socket;

public class HttpClientConnection implements Runnable {
    private Socket socket;

    public HttpClientConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        
    }
}
