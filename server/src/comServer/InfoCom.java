package server.src.comServer;

import server.src.managers.CollectionManager;

public class InfoCom extends Command {
    public InfoCom() {
        this.name = "info";
        this.description = "вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
    @Override
    public String execute(CollectionManager collectionManager, Object args) {
        return collectionManager.toString();
    }
    
}
