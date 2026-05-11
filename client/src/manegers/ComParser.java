package client.src.manegers;

import java.util.HashMap;

import client.src.com.*;
import client.src.network.Request;

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
        Command command = this.commands.get(name);
        Request req = new Request(command, command.execute(args));
        return req;
    }
    
}
