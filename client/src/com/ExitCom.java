package client.src.com;

import server.src.managers.CollectionManager;

public class ExitCom extends Command {
    public ExitCom(CollectionManager collectionManager) {
        super(collectionManager);
        this.name = "exit";
        this.description = "добавить элемент LabWork";
    }
    @Override
    public void execute(String... args) {
        System.exit(0);
    }
    //scan.close();?
}
