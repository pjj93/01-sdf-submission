package sdf1;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HttpServer {
    private ServerSocket server;
    private List<Path> dir = new ArrayList<Path>();

    public HttpServer(int port) throws IOException {
        this.server = new ServerSocket(port);
    }

    public boolean checkDir(List<String> docRoots) {
        List<Path> dir = new ArrayList<Path>();

        for (int i=0; i< docRoots.size(); i++) {
            Path p = Paths.get(docRoots.get(i));
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
}
