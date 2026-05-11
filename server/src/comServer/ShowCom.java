package server.src.comServer;

import java.util.TreeSet;

import api.entites.LabWork;
import server.src.managers.CollectionManager;



public class ShowCom extends Command {
    public ShowCom() {
        this.name = "show";
        this.description = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
    @Override
    public String execute(CollectionManager collectionManager, Object args) {
        TreeSet<LabWork> labWorks = collectionManager.getElems();
        String s = "";
        for(LabWork laba : labWorks) {
            s += (laba.toString() + "\n");
        }
        return s;
    }
    
}