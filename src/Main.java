import io.Input;
import managers.CollectionManager;

public class Main {
    public static void main(String[] args) {
        CollectionManager collectionManager = new CollectionManager();
        Input.start(collectionManager);
    }
}
