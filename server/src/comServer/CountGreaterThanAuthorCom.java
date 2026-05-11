package server.src.comServer;

import api.builders.PersonBuilder;
import api.entites.Person;
import api.entites.enums.Color;
import api.exceptions.WrongParam;
import client.src.io.Input;
import server.src.managers.CollectionManager;

public class CountGreaterThanAuthorCom extends Command {
    public CountGreaterThanAuthorCom() {
        this.name = "counterByWeight";
        this.description = "Вывести кол-во элементов, вес которых в поле \"author\"  больше заданного";
    }
    @Override
    public String execute(CollectionManager collectionManager, Object args) {
        try {
            if(args != null && args instanceof String) {
                PersonBuilder personBuilder = new PersonBuilder();
                Person author = personBuilder.makePerson("", 0.0, Long.valueOf((String) args), "", Color.WHITE);
                return ("Кол-во элементов, вес которых больше, чем " + ((String) args) + ": " + collectionManager.findElemsHeavierPerson(author).size());
            } else throw new WrongParam("Ошибка в элементе коллекции такой объект невозможно добавить");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
