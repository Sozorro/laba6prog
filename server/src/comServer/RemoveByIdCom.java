package server.src.comServer;

import server.src.managers.CollectionManager;
import api.exceptions.WrongParam;

public class RemoveByIdCom extends Command {
    public RemoveByIdCom() {
        this.name = "remove";
        this.description = "удалить элемент из коллекции по его id";
    }
    @Override
    public String execute(CollectionManager collectionManager, Object args) {
        try {
            if(args != null && args instanceof String) {
                return collectionManager.delLab(Long.parseLong((String) args));
            } else throw new WrongParam();
        } catch (NumberFormatException e) {
            return ("Неверный формат id");
        } catch (Exception e) {
            return ("Комнда была остановлена");
        }
    }
}
