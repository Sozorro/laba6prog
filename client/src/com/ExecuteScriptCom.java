package client.src.com;

import java.io.File;
import java.io.FileNotFoundException;

import client.src.exceptions.WrongParam;
import client.src.io.Input;
import client.src.io.InputFile;
import server.src.managers.CollectionManager;
import server.src.managers.ComHistory;

public class ExecuteScriptCom extends Command {
    public ExecuteScriptCom(CollectionManager collectionManager) {
        super(collectionManager);
        this.name = "execute";
        this.description = "запустить скрипт";
    }
    @Override
    public void execute(String... args) {
        try {
            String str = ""; ///Users/kessi.kissa/Desktop/jlab/lab5/file1.csv
            if(args == null || args.length == 0) str = Input.getParams("Какой файл вы хотите запустить?");
            else if(args.length != 1) throw new WrongParam("Неверное имя файла, проверьте корректность ввода и отсутствие пробелов в названии");
            else str = args[0];
            File myFile = new File(str);
            InputFile.start(myFile, collectionManager);
            ComHistory.addCom(name, str);
        } catch (FileNotFoundException e) {
            System.out.println("Данный файл не найден");
            String prov = Input.getParams("\tЕсли хотите попробовать ещё раз введите: \"(y)yes\" \n \tИначе введите: \"(n)no\" \n \t");
            if(prov != null && (prov.equals("yes") || prov.equals("y"))) {
                execute();
            }
        } catch (WrongParam e) {
            System.out.println(e.getMessage());
            String prov = Input.getParams("\tЕсли хотите попробовать ещё раз введите: \"(y)yes\" \n \tИначе введите: \"(n)no\" \n \t");
            if(prov != null && (prov.equals("yes") || prov.equals("y"))) {
                execute();
            }
        }
    }
}
