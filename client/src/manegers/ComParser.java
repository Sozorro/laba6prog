package client.src.manegers;

import java.util.HashMap;

import api.Request;
import api.entites.LabWork;
import api.exceptions.WrongParam;
import client.src.comClient.AddCom;
import client.src.comClient.ClearCom;
import client.src.comClient.Command;
import client.src.comClient.CountGreaterThanAuthorCom;
import client.src.comClient.ExecuteScriptCom;
import client.src.comClient.ExitCom;
import client.src.comClient.FilterStartsWithDescriptionCom;
import client.src.comClient.HelpCom;
import client.src.comClient.HistoryCom;
import client.src.comClient.InfoCom;
import client.src.comClient.RemoveByIdCom;
import client.src.comClient.ShowCom;
import client.src.comClient.StopCom;

public class ComParser {
    private HashMap<String, Command> commands = new HashMap<>();
    public ComParser() {
        commands.put("add", new AddCom());
        commands.put("clear", new ClearCom());
        commands.put("counterByWeight", new CountGreaterThanAuthorCom());
        commands.put("execute", new ExecuteScriptCom());
        commands.put("exit", new ExitCom());
        commands.put("filterStartsWithDescription", new FilterStartsWithDescriptionCom());
        commands.put("help", new HelpCom());
        commands.put("history", new HistoryCom());
        commands.put("info", new InfoCom());
        commands.put("remove", new RemoveByIdCom());
        commands.put("show", new ShowCom());
        commands.put("stop", new StopCom());
        //commands.put("update", new UpdateCom());

    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public Request interpret(String name, String... args) {
        System.out.println(name);
        Command command = this.commands.get(name);
        Object res = command.execute(args);
        if(res == null) return new Request(command);
        if(res instanceof LabWork) return new Request(command, (LabWork) command.execute(args));
        if(res instanceof String) return new Request(command, (String) command.execute(args));
        else throw new WrongParam();
    }
    
}
