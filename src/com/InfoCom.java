package com;

import managers.CollectionManager;
import managers.ComHistory;

public class InfoCom extends Command {
    public InfoCom(CollectionManager collectionManager) {
        super(collectionManager);
        this.name = "info";
        this.description = "вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
    @Override
    public void execute(String... args) {
        ComHistory.addCom(name, null);
        System.out.println(collectionManager.toString());
    }
    
}
