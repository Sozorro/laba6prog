package ru.kessi.server.network;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ru.kessi.common.Request;
import ru.kessi.server.managers.CollectionManager;
import ru.kessi.server.managers.ComParser;

public class Server {
    private String host;
    private int port;
    private ServerSocketChannel serverSocketChannel;
    private boolean work = false;
    private boolean wait = false;
    private CollectionManager collectionManager;
    private Selector selector;
    private Map<SocketChannel, ByteArrayOutputStream> arrByteMapForClients = new HashMap<>(); //Данные для каждого клиента
    private boolean isPaused = false; 
    private Thread serverThread;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        if (work) {
            System.out.println("Сервер уже запущен");
            return;
        }
        work = true;
        serverThread = new Thread(() -> run());
        serverThread.start();
    }
    public void stop() {
        work = false;
        try {
            closeServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void openServer(){
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(host, port));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Сервер запущен");
            collectionManager = new CollectionManager();
        } catch (Exception e) {
            System.out.println("Ошибка при попытке создать сервер");
        }
    }

    public void closeServer(){
        try {
            if (selector != null) selector.close();
            if (serverSocketChannel != null) serverSocketChannel.close();
            System.out.println("Сервер закрыт");
        } catch (Exception e) {
            System.out.println("Ошибка при попытке закрыть сервер");
        }
    }

    public void run() {
        try {
            openServer();
            Thread dotsThread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    if (wait) {
                        System.out.println("Ожидаем подключения хотя бы одного клиента ...");
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            dotsThread.start();
            //System.out.println("Ожидаем подключения хотя бы одного клиента ...");
            while (work) {
                if (arrByteMapForClients.size() == 0) wait = true;
                else wait = false;
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        //serverSocketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = serverSocketChannel.accept();
                        //System.out.println("Подключился клиент: " + clientChannel + "\n" + "К серверу: " + key.channel());
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        arrByteMapForClients.put(clientChannel, new ByteArrayOutputStream());
                        System.out.println("Клиент " + clientChannel.getRemoteAddress() + " подключен");
                    } else if (key.isReadable()) {
                        SocketChannel clientChannel = (SocketChannel) key.channel();
                        System.out.println("Есть данные для чтения от: " + clientChannel);
                        try {
                            clientRequest(clientChannel);
                        } catch (EOFException e) {
                            if (key != null) {
                                key.cancel();
                                clientChannel.close();
                                arrByteMapForClients.remove(clientChannel);
                                System.out.println("Клиент отключился");
                            }
                        }
                        
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при попытке подключить клиента");
        } finally {
            if(!work) stop();
        }
    }

    public void clientRequest(SocketChannel clientChannel) throws EOFException {
        try {
            ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
            while (lengthBuffer.hasRemaining()) {
                int c = clientChannel.read(lengthBuffer);
                if (c == -1) {
                    clientChannel.close();
                    arrByteMapForClients.remove(clientChannel);
                    //System.out.println("Клиент отключился");
                    throw new EOFException("Канал закрыт");
                } else if (c == 0) {
                    // Нет данных, ждём следующего события
                    System.out.println("Нет данных, ждём следующего события");
                    return;
                }   
            }
            lengthBuffer.flip();
            int size = lengthBuffer.getInt();
            //System.out.println("][" + size);
            //lengthBuffer.clear();

            if(size <= 0) {
                System.out.println("Неверный размер");
                return;
            }

            ByteBuffer buf = ByteBuffer.allocate(size);
            ByteArrayOutputStream arrByte = arrByteMapForClients.get(clientChannel);

            while (buf.hasRemaining()) {
                int c = clientChannel.read(buf);
                if (c == -1) {
                    clientChannel.close();
                    arrByteMapForClients.remove(clientChannel);
                    //System.out.println("Клиент отключился");
                    throw new EOFException("Канал закрыт");
                }
            }
            
            buf.flip();
            byte[] bytes = new byte[buf.remaining()];
            buf.get(bytes);
            //buf.clear();
            //arrByte.write(bytes);
            Request req = Serialize.tryDeserialize(bytes);
            
            if (req == null) {
                return;
            }
            if (req != null) {
                System.out.println("Получен объект: " + req);
            } 
            if (req.getCommand().toString().equals("command 'exit'")) {
                clientChannel.close();
                throw new EOFException("Канал закрыт");
            }
            if (req.getCommand().toString().equals("command 'stop'")) {
                work = false;
                return;
            }

            buf.clear();

            ComParser comParser = new ComParser(collectionManager);
            String res;
            if(req.getArgs() != null) {
                res = comParser.interpret(req.getCommand(), req.getArgs());
            } else if(req.getLabWork() != null) {
                res = comParser.interpret(req.getCommand(), req.getLabWork());
            } else {
                res = comParser.interpret(req.getCommand(), null);
            }
            answerServer(clientChannel, res);

        } catch (EOFException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при попытке получить запрос");
        }
    }
    public void answerServer(SocketChannel clientChannel, String res) {
        try {
            byte[] serializedObject = Serialize.serializeObject(res);
            ByteBuffer buffer = ByteBuffer.allocate(4 + serializedObject.length);
            buffer.putInt(serializedObject.length);
            buffer.put(serializedObject);
            buffer.flip();
            while (buffer.hasRemaining()) {
                clientChannel.write(buffer);
            }
        } catch (Exception e) {
            System.out.println("[p");
        }
    }
}



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