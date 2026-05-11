package client.src.comClient;

import client.src.manegers.ComHistory;

public class InfoCom extends Command {
    public InfoCom() {
        this.name = "info";
        this.description = "вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
    @Override
    public String execute(String... args) {
        ComHistory.addCom(name, null);
        return null;
    }
    
}
