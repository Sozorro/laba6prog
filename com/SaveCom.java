package com;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.TreeSet;

import builders.LabWork;
import exceptions.WrongParam;
import io.Input;
import managers.CollectionManager;
import managers.ComHistory;

public class SaveCom extends Command {
    public SaveCom(CollectionManager collectionManager) {
        super(collectionManager);
        this.name = "save";
        this.description = "сохранить коллекцию в файл";
    }
    @Override
    public void execute(String... args) {
        try {
            String str = "";
            if(args == null || args.length == 0) str = Input.getParams("В файл вы хотите записать коллекцию?");
            else if(args.length != 1) throw new WrongParam("Неверное имя файла, проверьте корректность ввода и отсутствие пробелов в названии");
            else str = args[0];
            String[] rash = str.split("\\/")[str.split("\\/").length - 1].split("\\.");
            if(rash.length > 2 || rash.length == 0) {
                System.out.println("Не получается определить расширение файла, проверьте его правильность и отсутствие лишних точек в названии");                
            } else if(rash.length == 1) {
                System.out.println("Расширение не указано");
                System.out.println("Создание файла " + str + ".csv");
                str = str + ".csv";
            } else {
                System.out.println("Расширение файла - " + rash[1]);
                if(!rash[1].equals("csv")) {
                    str = str.replace(rash[1], "csv");
                    System.out.println("Создание файла " + str);
                } 
            }
            
            try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(str), "UTF-8")) {
                TreeSet<LabWork> collection = collectionManager.getElems();
                //CsvSaver.saveCollection(collection, writer);
                String head = "name; id; date; name; coordinatesX; coordinatesY; minimalPoint; personalQualitiesMinimum; description; difficulty;name; height; weight; passportID; hairColor;intparam;stringparam";
                writer.write(head + "\n");
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                for (LabWork laba : collection) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("add").append(";");
                    sb.append(laba.getId()).append(";");
                    sb.append(formatter.format(laba.getCreationDate())).append(";"); 
                    sb.append(laba.getName()).append(";");
                    sb.append(laba.getCoordinates().getX()).append(";");
                    sb.append(laba.getCoordinates().getY()).append(";");
                    sb.append(laba.getMinimalPoint()).append(";");
                    sb.append(laba.getPersonalQualitiesMinimum()).append(";");
                    sb.append(laba.getDescription()).append(";");
                    sb.append(laba.getDifficulty().toString()).append(";");
                    sb.append(laba.getAuthor().getName()).append(";"); 
                    sb.append(laba.getAuthor().getHeight()).append(";");
                    sb.append(laba.getAuthor().getWeight()).append(";");
                    sb.append(laba.getAuthor().getPassportID()).append(";");
                    sb.append(laba.getAuthor().getHairColor()).append(";");
                    sb.append(";");
                    sb.append("\n");
                    writer.write(sb.toString());
                    /*sb.append(laba.getAuthor().getHairColor()).append(";");

                    
                    sb.append(laba.getName()).append(",");
                    sb.append(laba.getId()).append(",");
                    sb.append(laba.getDate).append(",");
                    sb.append(laba.getName2).append(",");
                    sb.append(laba.getCoordinatesX).append(",");
                    sb.append(laba.getCoordinatesY).append(",");
                    sb.append(laba.getMinimalPoint).append(",");
                    sb.append(laba.getPersonalQualitiesMinimum.append(",");
                    sb.append(laba.getDescription).append(",");
                    sb.append(laba.getDifficulty)).append(",");
                    sb.append(laba.getName3).append(",");
                    sb.append(laba.getHeight).append(",");
                    sb.append(laba.getWeight).append(",");
                    sb.appendlaba.passportID.append(",");
                    sb.appendlaba.hairColor.append(",");
                    sb.append(laba.intparam).append(",");
                    sb.appendlaba.stringparam.append("\n");*/

                }
                writer.flush();
            } catch (IOException e) {
                throw new WrongParam("Ошибка ввода");
            }
            ComHistory.addCom(name, str);
        } catch (WrongParam e) {
            System.out.println(e.getMessage());
            String prov = Input.getParams("\tЕсли хотите попробовать ещё раз введите: \"(y)yes\" \n \tИначе введите: \"(n)no\" \n \t");
            if(prov != null && (prov.equals("yes") || prov.equals("y"))) {
                execute();
            }
        }

    }
    /*
public static void saveCollection(Collection<MyObject> collection, OutputStreamWriter writer) throws IOException {
        // Записываем заголовки
        String header = "name,id,date,name,coordinatesX,coordinatesY,minimalPoint,personalQualitiesMinimum,description,difficulty,name,height,weight,passportID,hairColor,intparam,stringparam\n";
        writer.write(header);

        for (MyObject laba : collection) {
            StringBuilder sb = new StringBuilder();

            sb.append((laba.name)).append(",");
            sb.append(laba.id).append(",");
            sb.append((laba.date)).append(",");
            sb.append((laba.name2)).append(",");
            sb.append(laba.coordinatesX).append(",");
            sb.append(laba.coordinatesY).append(",");
            sb.append(laba.minimalPoint).append(",");
            sb.append((laba.personalQualitiesMinimum)).append(",");
            sb.append((laba.description)).append(",");
            sb.append((laba.difficulty)).append(",");
            sb.append((laba.name3)).append(",");
            sb.append(laba.height).append(",");
            sb.append(laba.weight).append(",");
            sb.append((laba.passportID)).append(",");
            sb.append((laba.hairColor)).append(",");
            sb.append(laba.intparam).append(",");
            sb.append((laba.stringparam)).append("\n");

            writer.write(sb.toString());
        }
        writer.flush();
    }

    private static String (String field) {
        if (field == null) {
            return "";
        }
        // Оборачиваем в кавычки, если есть запятые или кавычки внутри
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        } else {
            return field;
        }
    }






    import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.Collection;

// Предположим, что у вас есть класс MyObject с нужными полями
public class MyObject {
    String name;
    long id;
    String date;
    String name2; // В вашем списке есть два поля "name", я добавлю для ясности
    double coordinatesX;
    double coordinatesY;
    int minimalPoint;
    String personalQualitiesMinimum;
    String description;
    String difficulty;
    String name3; // Третье поле "name"
    double height;
    double weight;
    String passportID;
    String hairColor;
    int intparam;
    String stringparam;

    // Конструктор, геттеры и сеттеры
    // ...
}

public class CsvSaver {

    
}


    Для создания файла можно использовать метод File.createNewFile(). 
    Он создаст новый файл, если файл с таким названием еще не существует. 
    Если же такой файл уже есть, то метод createNewFile() выдаст исключение IOException.


    Должно быть:
    Запись данных в файл необходимо реализовать с помощью класса java.io.OutputStreamWriter
     */
}
