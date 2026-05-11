package client.src.comClient;

import client.src.manegers.ComHistory;

public class StopCom extends Command {
    public StopCom() {
        this.name = "stop";
        this.description = "Остановить сервер";
    }
    @Override
    public String execute(String... args) {
        ComHistory.addCom(name, null);
        return null;
    }
    @Override
    public String toString() {
        return "command 'stop'";
    }
    //scan.close();?
}
