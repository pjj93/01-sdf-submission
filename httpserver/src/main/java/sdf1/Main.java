package sdf1;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        List<String> docRoots = new ArrayList<String>(); //stores the list of directories as an ArrayList of type String
        int port = 3000; // default port number
        // boolean isDir = false;
        
        // if condition specifies default port and directory if no args passed
        if (args.length == 0) {
            //Path path = Paths.get("./static");
            docRoots.add("./static");
            // System.out.println("Pathname: " + path);
        }
        else {
            for (int i=0; i<args.length;i++) {
                //cmdargs.add(args[i]);
                if (args[i].equals("--port")) {
                    if (i+1 < args.length) {
                        port = Integer.parseInt(args[i+1]);
                    }
                }
                if (args[i].equals("--docRoot")) {
                    if (i+1 < args.length) {
                        docRoots = Arrays.asList(args[i+1].split(":"));
                    }
                }
            }
            if (docRoots.isEmpty()) {
                docRoots.add("./static");
            }
        }
        
        startServer(port, docRoots);
        // test output for Task 3
        // System.out.println("Port number: " + port);
        // for (int i = 0; i< docRoots.size(); i++) {
        //     System.out.println(docRoots.get(i));
        // }
        
    }

    public static void startServer(int port, List<String> docRoots) {
        try {
            HttpServer server = new HttpServer(port, docRoots);
            // System.out.println("Starting server on port " + port + "....");
            boolean isDir = server.checkDir(docRoots);
            if (!isDir) {
                server.stop();
                System.exit(1);
            }
            ExecutorService threadpool = Executors.newFixedThreadPool(3);

            //while (true) {
                Socket socket = server.accept();
                System.out.println("A client has connected!");
                HttpClientConnection worker = new HttpClientConnection(socket);
                threadpool.submit(worker);
            //}
                // Thread t = new Thread();
                // t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}