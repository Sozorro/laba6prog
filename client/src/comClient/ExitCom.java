package client.src.comClient;

import client.src.manegers.ComHistory;

public class ExitCom extends Command {
    public ExitCom() {
        this.name = "exit";
        this.description = "добавить элемент LabWork";
    }
    @Override
    public String execute(String... args) {
        ComHistory.addCom(name, null);
        return null;
    }
    @Override
    public String toString() {
        return "command 'exit'";
    }
    //scan.close();?
}
