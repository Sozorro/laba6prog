package server.src.comServer;

import server.src.managers.CollectionManager;

public abstract class ExitCom extends Command {
    public ExitCom() {
        this.name = "exit";
        this.description = "добавить элемент LabWork";
    }
    @Override
    public String execute(CollectionManager collectionManager, Object args) {
        return null;
    }
    //scan.close();?
}
