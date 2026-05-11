package client.src.io;

import java.util.NoSuchElementException;
import java.util.Scanner;

import api.Request;
import api.exceptions.WrongParam;
import client.src.manegers.ComParser;

public class Input {
    private static ComParser comParser;
    public static Scanner scannerNow;
    public static Request start(Scanner scanner) {
        comParser = new ComParser();
        scannerNow = scanner;
        while (true) {
            try {
                System.out.println("----------------------");
                System.out.println("Ведите команду. Если хотите выйти введите: 'exit'. Для справки введите: help");
                String s = readNextLine();
                if (s == null) continue;
                String[] command = s.split(" ", 2);
                if(!comParser.getCommands().containsKey(command[0])) {
                    System.out.println("Неверная команда");
                    continue;
                }
                if(command.length == 1) {
                    return comParser.interpret(command[0]);
                }
                else {
                    return comParser.interpret(command[0], command[1]);
                }
            } catch (NoSuchElementException e) { //if end file(ctrl+D)
                scannerNow = new Scanner(System.in);
            } catch (Exception e) {//WrongParam | WrongAction e) { //if end file(ctrl+D)
                System.out.println(e.getMessage());
                scannerNow = new Scanner(System.in);
            }
        }
    }
    private static String readNextLine() throws WrongParam { 
        try {
            String s = scannerNow.nextLine().trim();
            while (s.equals("") || s == null) {
                System.out.println("Ожидание строки");
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