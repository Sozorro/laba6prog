package server.src.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    private String host;
    private int port;
    private ServerSocketChannel serverSocketChannel;
    private boolean work = false;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void openServer(){
        try {
            work = true;
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(host, port));
            serverSocketChannel.configureBlocking(false);
            System.out.println("Сервер запущен");
        } catch (Exception e) {
            System.out.println("Ошибка при попытке создать сервер");
        }
    }

    public void closeServer(){
        try {
            serverSocketChannel.close();
            System.out.println("Сервер закрыт");
        } catch (Exception e) {
            System.out.println("Ошибка при попытке закрыть сервер");
        }
    }

    public void run() {
        try {
            openServer();
            SocketChannel socketChannel;
            System.out.println("Ожидаем подключения клиента ...");
            while (work == true) {
                socketChannel = serverSocketChannel.accept();
                if(socketChannel != null) {
                    System.out.println("Клиент " + socketChannel.getRemoteAddress() + " подключен");
                    clientRequest(socketChannel);
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка при попытке подключить клиента");
        } finally {
            closeServer();
        }
    }

    public void clientRequest(SocketChannel socketChannel) {
        try(
            ObjectOutputStream out = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socketChannel.socket().getInputStream());
        ) {
            while (true) {
                Object object = null;
                while (object == null) {
                    object = in.readObject();
                }
                System.out.println("Клиент передал строку: " + object.toString());
                out.writeObject("Длина строки: " + object.toString().length());
                out.flush();
                if (object != null && object.toString().equals("exit")) {
                    work = false;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка при попытке получить запрос");
        }
    }
}










    















/*    
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

    private int port;
    private String host;
    private ServerSocketChannel serverSocket;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void openServer() {
        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(host, port));
            serverSocket.configureBlocking(false);
            System.out.println("Сервер открыт");
        } catch (IOException e) {
            System.out.println("Ошибка при попытке создать сервер");
        }

    }
    public void closeServer() {
        try {
            serverSocket.close();
            System.out.println("Сервер закрыт");
        } catch (IOException e) {
            System.out.println("Ошибка при попытке закрыть сервер");
        }

    }
    public void run() {
        try {
            openServer();
            while (true) {
                SocketChannel clientSocket = serverSocket.accept(); 
                if (clientSocket != null) { //ждём, пока кто-то подключится
                    System.out.println("Клиент подключен: " + clientSocket.getRemoteAddress());
                    clientRequest(clientSocket);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка");
        } finally {
            closeServer();
        }
        
    }
    public void clientRequest(SocketChannel clientSocket) {
        ObjectOutputStream out;
        ObjectInputStream in;
        try {
            out = new ObjectOutputStream(clientSocket.socket().getOutputStream());
            in = new ObjectInputStream(clientSocket.socket().getInputStream());
            Object s = in.readObject(); // ждём пока клиент что-нибудь нам напишет
            out.writeObject("Сервер: Вы написали : " + s.toString() + "\n");
            out.flush();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка");
        } finally {
            //out.close();
            //in.close();
        }

    }
}*/

/*
 * Необходимо выполнить следующие требования:

 * Объекты между клиентом и сервером должны передаваться в сериализованном виде.
 * Обмен данными между клиентом и сервером должен осуществляться по протоколу TCP
 * Для обмена данными на сервере необходимо использовать сетевой канал
 * Сетевые каналы должны использоваться в неблокирующем режиме.
 * 
 * Серверное приложение должно состоять из следующих модулей (реализованных в
 * виде одного или нескольких классов):
 * Модуль приёма подключений.
 * Модуль чтения запроса.
 * Модуль обработки полученных команд.
 * Модуль отправки ответов клиенту.
 * Сервер должен работать в однопоточном режиме.
 * 
 * Обязанности серверного приложения:
 * 
 * Работа с файлом, хранящим коллекцию.
 * Управление коллекцией объектов.
 * Назначение автоматически генерируемых полей объектов в коллекции.
 * Ожидание подключений и запросов от клиента.
 * Обработка полученных запросов (команд).
 * Сохранение коллекции в файл при завершении работы приложения.
 * Сохранение коллекции в файл при исполнении специальной команды, доступной
 * только серверу (клиент такую команду отправить не может).
 * 
 * Объекты в коллекции, передаваемой клиенту, должны быть отсортированы по
 * названию
 * 
 */