package client.src.comClient;

import api.exceptions.WrongParam;
import client.src.manegers.ComHistory;
import client.src.manegers.ComParser;

public class HelpCom extends Command {
    public HelpCom() {
        this.name = "help";
        this.description = "Вывести справку по доступным командам";
    }
    @Override
    public String execute(String... args) {
        try{
            if(args != null && args.length != 0) {
                throw new WrongParam("Введены лишние параметры");
            }
            ComParser pars = new ComParser();
            System.out.println("Список доступных команд: ");
            for(Command com : pars.getCommands().values()) {
                System.out.printf("%s: %s %n", com.getName(), com.getDescription());
            }
            ComHistory.addCom(name, null);
            return null;
        }  catch (WrongParam e) {
            System.out.println("Ошибка ввода.");
            throw e;
        }
    }
    @Override
    public String toString() {
        return "command 'help'";
    }
}
