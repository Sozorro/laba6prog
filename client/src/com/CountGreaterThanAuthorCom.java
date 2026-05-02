package client.src.com;

import client.src.exceptions.WrongAction;
import client.src.exceptions.WrongParam;
import client.src.io.Input;
import client.src.io.InputFile;
import server.src.builders.Person;
import server.src.builders.PersonBuilder;
import server.src.enums.Color;
import server.src.managers.CollectionManager;
import server.src.managers.ComHistory;

public class CountGreaterThanAuthorCom extends Command {
    public CountGreaterThanAuthorCom(CollectionManager collectionManager) {
        super(collectionManager);
        this.name = "counterByWeight";
        this.description = "Вывести кол-во элементов, вес которых в поле \"author\"  больше заданного";
    }
    @Override
    public void execute(String... args) {
        try {
            String[] str;
            if(args == null || args.length == 0) {
                if(InputFile.readFile == false) str = Input.getParams("введите вес").split(" ");
                else {
                    System.out.println("Не введены параметры, необходимые для выполнения команды, хотите ввести их в интерактивном режиме?");
                    String prov = Input.getParams("\tВведите: \"(y)yes\" \n \tИли: \"no\" и тогда команда будет пропущена");
                    while(prov == null) {
                        prov = Input.getParams("\tВведите: \"(y)yes\" \n \tИли: \"no\" и тогда команда будет пропущена");
                    }
                    if(prov.equals("yes") || prov.equals("y")) {
                        str = Input.getParams("введите вес").split(" ");
                    }//
                    System.out.println("Комнда была пропущена");
                    throw new WrongAction();
                }
            }
            else str = args;
            String ext = "Неверный формат, проверьте корректность ввода";
            if(str.length > 1 || str.length == 0) {
                throw new WrongParam(ext);
            }
            PersonBuilder personBuilder = new PersonBuilder();
            Person author = personBuilder.makePerson("", 0.0, Long.valueOf(str[0]), "", Color.WHITE);
            ComHistory.addCom(name, str[0]);
            System.out.println("Кол-во элементов, вес которых больше, чем " + str[0] + ": " + collectionManager.findElemsHeavierPerson(author).size());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат веса");
        } catch (WrongParam e) {
            System.out.println(e.getMessage());
            String prov = Input.getParams("\tЕсли хотите попробовать ещё раз введите: \"(y)yes\" \n \tИначе введите: \"(n)no\" \n \t");
            if(prov != null && (prov.equals("yes") || prov.equals("y"))) {
                execute();
            }
        }
    }
}
