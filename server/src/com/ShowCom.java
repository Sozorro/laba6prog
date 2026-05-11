package server.src.com;

import client.src.manegers.ComHistory;

public class ShowCom extends Command {
    public ShowCom() {
        this.name = "show";
        this.description = "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
    @Override
    public String execute(String... args) {
        ComHistory.addCom(name, null);
        return null;
    }
    
}