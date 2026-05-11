package server.src.comServer;

import api.entites.LabWork;
import api.exceptions.WrongParam;
import server.src.managers.CollectionManager;

public class AddCom extends Command {
    public AddCom() {
        this.name = "add";
        this.description = "Добавить элемент LabWork в коллекцию";
    }
    @Override
    public String execute(CollectionManager collectionManager, Object args){
        try { 
            if(args != null && args instanceof LabWork) {
                return collectionManager.addLab((LabWork) args);
            } else throw new WrongParam("Ошибка в элементе коллекции такой объект невозможно добавить");
        } catch (Exception e) {
            System.out.println("Произошла непредвиденная ошибка. Создание элемента было остановлено и он не был добавлен в коллекцию");
            throw e;
        }
    }
}
