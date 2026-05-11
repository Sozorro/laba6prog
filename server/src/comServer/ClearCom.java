package server.src.comServer;

import server.src.managers.CollectionManager;

public class ClearCom extends Command {
    public ClearCom() {
        this.name = "clear";
        this.description = "Очистить коллекцию";
    }
    @Override
    public String execute(CollectionManager collectionManager, Object args) {
        long i = collectionManager.delLabs();
        return ("Коллекция очищена, удалено " + i + " элемент" + (i == 1 ? "" : ((i < 5 || (i >= 20 && i % 10 == 0)) ? "ов" : "а")));
    }
}
