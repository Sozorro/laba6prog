package server.src;

import client.src.io.Input;
import server.src.managers.CollectionManager;

public class Main {
    public static void main(String[] args) {
        CollectionManager collectionManager = new CollectionManager();
        Input.start(collectionManager);
    }
}
