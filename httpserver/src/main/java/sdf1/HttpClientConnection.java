package sdf1;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpClientConnection implements Runnable {
    private Socket socket;

    public HttpClientConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String request = "";
        List<String> requestrec = new ArrayList<String>();
        String response = "";

        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                // InputStream is = socket.getInputStream();
                // BufferedInputStream bis = new BufferedInputStream(is);
                // DataInputStream dis = new DataInputStream(bis);
                OutputStream os = socket.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);
            ) {
                request = br.readLine();
                System.out.println(request);
                //request = dis.readUTF();
                requestrec = Arrays.asList(request.split(" "));
                
                boolean isGET = getResponse(requestrec);
                if (!isGET) {
                    response = "HTTP/1.1 405 Method Not Allowed\r\n\r\n" + requestrec.get(0) + " not supported\r\n";
                }
                //response = getResponse(requestrec);
            
                dos.writeUTF(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean getResponse(List<String> requestrec) {
        if (!requestrec.get(0).equals("GET"))
        {
            return false;
        }
        return true;
    }
}
