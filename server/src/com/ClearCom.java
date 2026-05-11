package server.src.com;

import client.src.manegers.ComHistory;

public class ClearCom extends Command {
    public ClearCom() {
        this.name = "clear";
        this.description = "Очистить коллекцию";
    }
    @Override
    public String execute(String... args) {
        ComHistory.addCom(name, null);
        return null;
        /*long i = collectionManager.delLabs();
        System.out.println("Коллекция очищена, удалено " + i + " элемент" + (i == 1 ? "" : ((i < 5 || (i >= 20 && i % 10 == 0)) ? "ов" : "а")));
    */
    }
}
