package client.src.io;

import java.util.NoSuchElementException;
import java.util.Scanner;

import client.src.exceptions.WrongParam;
import server.src.managers.CollectionManager;
import server.src.managers.ComParser;

public class Input {
    private static ComParser comParser;
    public static Scanner scannerNow;
    public static void start(CollectionManager collectionManager) {
        comParser = new ComParser(collectionManager);
        scannerNow = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("----------------------");
                System.out.println("Ведите команду. Для справки введите: help");
                String s = readNextLine();
                if (s == null) continue;
                String[] command = s.split(" ", 2);
                if(!comParser.getCommands().containsKey(command[0])) continue;
                if(command.length == 1) comParser.interpret(command[0]);
                else comParser.interpret(command[0], command[1]);
            } catch (NoSuchElementException e) { //if end file(ctrl+D)
                scannerNow = new Scanner(System.in);
            } catch (WrongParam e) { //if end file(ctrl+D)
                System.out.println(e.getMessage());
                scannerNow = new Scanner(System.in);
            }
        }
    }
    private static String readNextLine() throws WrongParam { 
        try {
            String s = scannerNow.nextLine().trim();
            while (s.equals("") || s == null) {
                System.out.println("ожидание строки");
                s = scannerNow.nextLine().trim();
            }
            return s;
        } catch (NoSuchElementException e) { //if end file(ctrl+D)
            throw new WrongParam("Недопустимые символы для ввода");
        }
        
    }

    public static String getParams(String s, String... args) throws WrongParam { 
        System.out.println(s);
        for(String arg : args) {
            System.out.println(arg);
        }
        String str = readNextLine().strip();
        return str;
    }
}
