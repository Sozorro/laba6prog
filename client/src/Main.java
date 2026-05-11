package client.src;

import java.util.Scanner;

import client.src.io.Input;
import client.src.network.Client;
import server.src.managers.CollectionManager;
import server.src.network.Server;

public class Main {
    public static Scanner scannerNow;
    public static void main(String[] args) {
        scannerNow = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("----------------------");
                String s = scannerNow.nextLine().trim();
                if (s.equals("go")) {
                    Server server = new Server("localhost", 1234);
                    server.run();
                    System.exit(0);
                } else if (s.equals("we")) {
                    Client client = new Client();
                    client.run("localhost", 1234);
                }
            } catch(Exception e) {
                System.exit(0);
            }
        }
        //CollectionManager collectionManager = new CollectionManager();
        //Input.start(collectionManager);
    }
}
