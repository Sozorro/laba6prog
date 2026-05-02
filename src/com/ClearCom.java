package com;

import managers.CollectionManager;
import managers.ComHistory;

public class ClearCom extends Command {
    public ClearCom(CollectionManager collectionManager) {
        super(collectionManager);
        this.name = "clear";
        this.description = "Очистить коллекцию";
    }
    @Override
    public void execute(String... args) {
        long i = collectionManager.delLabs();
        ComHistory.addCom(name, null);
        System.out.println("Коллекция очищена, удалено " + i + " элемент" + (i == 1 ? "" : ((i < 5 || (i >= 20 && i % 10 == 0)) ? "ов" : "а")));
    }
}
