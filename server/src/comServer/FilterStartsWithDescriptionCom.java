package server.src.comServer;

import api.exceptions.WrongParam;
import client.src.io.Input;
import client.src.io.InputFile;
import client.src.manegers.ComHistory;
import server.src.managers.CollectionManager;

public class FilterStartsWithDescriptionCom extends Command {
    public FilterStartsWithDescriptionCom() {
        this.name = "filterStartsWithDescription";
        this.description = "вывести элементы, значение поля description которых начинается с заданной подстроки";
    }
    @Override
    public String execute(CollectionManager collectionManager, Object args) {
        return null;
        /*try {
            String str;
            if((args == null || args.length == 0) && InputFile.readFile == false) str = Input.getParams("Введите подстроку: ");
            else if (args == null || args.length == 0) throw new WrongParam("\tВведены не все параметры или они некорректно заданы, хотите ввести их ещё раз в интерактивном режиме? \n");
            else str = String.join(" ", args);
            for(var elem : collectionManager.findElemsSubstring(str)) {
                System.out.println(elem);
            }
            ComHistory.addCom(name, str);
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат id");
        } catch (WrongParam e) {
            System.out.println(e.getMessage());
            String prov = null;
            while (prov == null) {
                prov = Input.getParams("\tЕсли хотите попробовать ещё раз введите: \"(y)yes\" \n \tИначе введите: \"(n)no\" \n \t");
            }
            if(prov.equals("yes") || prov.equals("y")) {
                execute(Input.getParams("Введите подстроку: "));
            } else {
                System.out.println("Комнда была пропущена");
            }
        }*/
    }
}
