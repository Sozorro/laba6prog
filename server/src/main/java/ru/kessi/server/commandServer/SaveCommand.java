package ru.kessi.server.commandServer;


public abstract class SaveCommand {// extends ru.kessi.common.commandManager.command.SaveCommand implements ServerCommand {
    /*public SaveCom() {
        this.name = "save";
        this.description = "сохранить коллекцию в файл";
    }
    @Override
    public void execute(CollectionManager collectionManager, Object args) {
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

    }*/
}