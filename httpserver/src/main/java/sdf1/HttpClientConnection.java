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
    //private List<Path> dir = new ArrayList<Path>();

    public HttpClientConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String request = "";
        List<String> requestrec = new ArrayList<String>();
        String response = "";

        try (
                InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                // InputStream is = socket.getInputStream();
                
                OutputStream os = socket.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(os);
                DataOutputStream dos = new DataOutputStream(bos);
            ) {
                // BufferedInputStream bis = new BufferedInputStream(is);
                // DataInputStream dis = new DataInputStream(bis);
                request = br.readLine();
                //request = dis.readUTF();
                //System.out.println(request);
                
                requestrec = Arrays.asList(request.split(" "));
                
                boolean isGET = getResponse(requestrec);
                if (!isGET) {
                    response = "HTTP/1.1 405 Method Not Allowed\r\n\r\n" + requestrec.get(0) + " not supported\r\n";
                    dos.writeUTF(response);
                    dos.flush();
                    return;
                }
                String reqresource = requestrec.get(1);
                if (reqresource.equals("/")) {
                    reqresource = "/index.html";
                }

                boolean isResourceExist = findResource(reqresource);
                if (!isResourceExist) {
                    response = "HTTP/1.1 404 Not Found\r\n\r\n" + reqresource + " not found\r\n";
                    dos.writeUTF(response);
                    dos.flush();
                    return;
                }
                else {
                    response = "HTTP/1.1 200 OK\r\n\r\n";
                    return;
                }
                //response = getResponse(requestrec);
            
                //dos.writeUTF(response);
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

    public static boolean findResource(String resource) {
        
        return false;
    }


}
