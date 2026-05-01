package com;

import java.util.TreeSet;

import builders.LabWork;
import managers.CollectionManager;
import managers.ComHistory;

public class ShowCom extends Command {
    public ShowCom(CollectionManager collectionManager) {
        super(collectionManager);
        this.name = "show";
        this.description = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
    @Override
    public void execute(String... args) {
        TreeSet<LabWork> labWorks = collectionManager.getElems();
        for(LabWork laba : labWorks) {
            System.out.println(laba.toString() + "\n");
        }
        ComHistory.addCom(name, null);
    }
    
}