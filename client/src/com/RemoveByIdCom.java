package client.src.com;

import client.src.exceptions.WrongAction;
import client.src.exceptions.WrongParam;
import client.src.io.Input;
import client.src.io.InputFile;
import client.src.manegers.ComHistory;

public class RemoveByIdCom extends Command {
    public RemoveByIdCom() {
        this.name = "remove";
        this.description = "удалить элемент из коллекции по его id";
    }
    @Override
    public String execute(String... args) {
        try {
            String[] str;
            if (args.length == 1) str = args;
            else if (InputFile.readFile == false) str = Input.getParams("Какой элемент удалить?").split(" ");
            else {
                throw new WrongParam("\tВведены не все параметры или они некорректно заданы, хотите ввести их ещё раз в интерактивном режиме? \n");
            }
            ComHistory.addCom(name, str[0]);
            return str[0];
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат id");
            throw new WrongParam("Неверный формат id");
        } catch (WrongParam e) {
            System.out.println(e.getMessage());
            String prov = null;
            while (prov == null) {
                prov = Input.getParams("\tЕсли хотите попробовать ещё раз введите: \"(y)yes\" \n \tИначе введите: \"(n)no\" \n \t");
            }
            if(prov.equals("yes") || prov.equals("y")) {
                return execute(Input.getParams("Какой элемент удалить?").split(" "));
            } else {
                System.out.println("Комнда была пропущена");
            }
            throw e;
        } catch (WrongAction e) {
            System.out.println("Комнда была остановлена");
            throw e;
        }
    }
}
