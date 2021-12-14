package sdf1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HttpServer {
    private ServerSocket server;
    private List<Path> dir = new ArrayList<Path>();

    public HttpServer(int port, List<String> docRoots) throws IOException {
        this.server = new ServerSocket(port);
        for (int i=0; i< docRoots.size(); i++) {
            Path p = Paths.get(docRoots.get(i));
            dir.add(p);
        }
    }

    public boolean checkDir(List<String> docRoots) {
        List<Path> dir = new ArrayList<Path>();

        for (int i=0; i< docRoots.size(); i++) {
            // Path path = Paths.get("").toAbsolutePath().getRoot();
            // String s = path.toString() + docRoots.get(i);
            Path p = Paths.get(docRoots.get(i));
            //Path p = Paths.get(docRoots.get(i));
            if (!Files.exists(p)) {
                System.err.println("Path: " + p.toString() + " does not exist!");
                return false;
            }
            if (!Files.isDirectory(p)) {
                System.err.println(p.toString() + " is not a directory!");
                return false;
            }
            if (!Files.isReadable(p)) {
                System.err.println(p.toString() + " is not readable!");
                return false;
            }

        }

        return true;
    }

    public void stop() throws IOException {
        this.server.close();
    }

    public Socket accept() throws IOException {
        return this.server.accept();
    }

    public Path getDirs(int i) {
        return this.dir.get(i);
    }
}
