package server.src.managers;

import java.util.HashMap;

import client.src.com.*;

public class ComParser {
    private HashMap<String, Command> commands = new HashMap<>();
    public ComParser(CollectionManager collectionManager) {
        commands.put("add", new AddCom(collectionManager));
        commands.put("clear", new ClearCom(collectionManager));
        commands.put("counterByWeight", new CountGreaterThanAuthorCom(collectionManager));
        commands.put("execute", new ExecuteScriptCom(collectionManager));
        commands.put("exit", new ExitCom(collectionManager));
        commands.put("filterStartsWithDescription", new FilterStartsWithDescriptionCom(collectionManager));
        commands.put("help", new HelpCom(collectionManager));
        commands.put("history", new HistoryCom(collectionManager));
        commands.put("info", new InfoCom(collectionManager));
        commands.put("remove", new RemoveByIdCom(collectionManager));
        commands.put("save", new SaveCom(collectionManager));
        commands.put("show", new ShowCom(collectionManager));
        commands.put("update", new UpdateCom(collectionManager));

    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public void interpret(String name, String... args) {
        Command command = this.commands.get(name);
        command.execute(args);
    }
    
}
