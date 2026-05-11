package server.src.managers;

import java.util.HashMap;

import api.Request;
import server.src.comServer.*;

public class ComParser {
    private HashMap<String, Command> commands = new HashMap<>();
    CollectionManager collectionManager;
    public ComParser(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        commands.put("add", new AddCom());
        commands.put("clear", new ClearCom());
        commands.put("counterByWeight", new CountGreaterThanAuthorCom());
        //commands.put("exit", new ExitCom());
        commands.put("filterStartsWithDescription", new FilterStartsWithDescriptionCom());
        commands.put("info", new InfoCom());
        commands.put("remove", new RemoveByIdCom());
        commands.put("show", new ShowCom());
        //commands.put("stop", new StopCom());
        //commands.put("update", new UpdateCom());

    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public String interpret(String name, Object args) {
        Command command = this.commands.get(name);
        return command.execute(collectionManager, args);
    }
    
}
