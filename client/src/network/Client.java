package client.src.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {
    //private String host;
    //private int port;
    private SocketChannel socketChannel;

    public Client() { //String host, int port) {
        //this.host = host;
        //this.port = port;
    }

    public void createClient(){
        try {
            socketChannel = SocketChannel.open();
            System.out.println("Клиент создан");
        } catch (Exception e) {
            System.out.println("Ошибка при попытке создать клиента");
        }
    }

    public void closeClient(){
        try {
            socketChannel.close();
            System.out.println("Клиент удалён");
        } catch (Exception e) {
            System.out.println("Ошибка при попытке удалить клиента");
        }
    }

    public void run(String host, int port) {
        try {
            createClient();
            System.out.println("Ожидаем подключения сервера ...");
            socketChannel.connect(new InetSocketAddress(host, port));
            System.out.println("Сервер " + socketChannel.getRemoteAddress() + " подключен");
            request();
        } catch (Exception e) {
            System.out.println("Ошибка при попытке подключить сервер");
        } finally {
            closeClient();
        }
    }

    public void request() {
        try(
            ObjectOutputStream out = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socketChannel.socket().getInputStream());
            Scanner scanner = new Scanner(System.in);
        ) {
            String line;
            System.out.println("Введите сообщение для отправки (или 'exit' для выхода или 'discon' для отключения от сервера):");
            while (true) { //!(line = scanner.nextLine()).equalsIgnoreCase("exit")
                line = scanner.nextLine();
                out.writeObject(line);
                out.flush();
                Object response = in.readObject();
                System.out.println("Ответ сервера: " + response);
                if (line != null && (line.toString().equals("discon") ||  line.toString().equals("exit"))) {
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Ошибка при попытке передать запрос");
        }
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*private int port;
    private String host;
    private SocketChannel socket;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void open() {
        try {
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress(host, port));
            System.out.println("Клиент пришёл");
        } catch (IOException e) {
            System.out.println("Ошибка при попытке создать сервер");
        }

    }
    public void close() {
        try {
            socket.close();
            System.out.println("Клиент ушёл:(");
        } catch (IOException e) {
            System.out.println("Ошибка при попытке закрыть сервер");
        }

    }*/
    /*public void run(String path) {
        try {
            openServer();
            while (true) {
                SocketChannel clientSocket = serverSocket.accept(); //ждём, пока кто-то подключится
                System.out.println("Клиент подключен: " + clientSocket.getRemoteAddress());
                clientRequest(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Ошибка");
        } finally {
            closeServer();
        }
        
    }*/
    /*public void request() {
        open();
        ObjectOutputStream out;
        ObjectInputStream in;
        try {
            out = new ObjectOutputStream(socket.socket().getOutputStream());
            in = new ObjectInputStream(socket.socket().getInputStream());
            out.writeObject("запрос");
            out.flush();
            System.out.println(in.readObject().toString());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка");
        } finally {
            //out.close();
            //in.close();
        }

    }
}*/

/*
 * Клиентский модуль должен в интерактивном режиме считывать
 * команды, передавать их для выполнения на сервер и выводить результаты
 * выполнения.
 * 
 * Объекты между клиентом и сервером должны передаваться в сериализованном виде.
 * Клиент должен корректно обрабатывать временную недоступность сервера.
 * Обмен данными между клиентом и сервером должен осуществляться по протоколу
 * TCP
 * Для обмена данными на клиенте необходимо использовать потоки ввода-вывода
 * 
 * Обязанности клиентского приложения:
 * 
 * Чтение команд из консоли.
 * Валидация вводимых данных.
 * Сериализация введённой команды и её аргументов.
 * Отправка полученной команды и её аргументов на сервер.
 * Обработка ответа от сервера (вывод результата исполнения команды в консоль).
 * Команду save из клиентского приложения необходимо убрать.
 * Команда exit завершает работу клиентского приложения.
 * Важно! Команды и их аргументы должны представлять из себя объекты классов.
 * Недопустим обмен "простыми" строками. Так, для команды add или её аналога
 * необходимо сформировать объект, содержащий тип команды и объект, который
 * должен храниться в вашей коллекции.
 * Дополнительное задание:
 * Реализовать логирование различных этапов работы сервера (начало работы,
 * получение нового подключения, получение нового запроса, отправка ответа и
 * т.п.) с помощью Logback
 */

