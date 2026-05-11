package server.src.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.ObjectInputStream;
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

import server.src.managers.CollectionManager;

public class Server {
    private String host;
    private int port;
    private ServerSocketChannel serverSocketChannel;
    private boolean work = false;
    private boolean wait = false;
    private CollectionManager collectionManager;
    private Selector selector;
    private Map<SocketChannel, ByteArrayOutputStream> arrByteMapForClients = new HashMap<>(); //Данные для каждого клиента

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void openServer(){
        try {
            serverSocketChannel = ServerSocketChannel.open();
            work = true;
            serverSocketChannel.bind(new InetSocketAddress(host, port));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Сервер запущен");
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
                        System.out.println("...");
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            dotsThread.start();
            System.out.println("Ожидаем подключения хотя бы одного клиента ...");
            while (work) {
                if (selector.keys().size() == 1) wait = true;
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
                        clientChannel.register(selector, SelectionKey.OP_READ);
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
                            }
                        }
                        
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при попытке подключить клиента");
        } finally {
            closeServer();
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
                    System.out.println("Клиент отключился");
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
                    System.out.println("Клиент отключился");
                    throw new EOFException("Канал закрыт");
                }
            }
            
            buf.flip();
            byte[] bytes = new byte[buf.remaining()];
            buf.get(bytes);
            //buf.clear();
            //arrByte.write(bytes);
            Object obj = Serialize.tryDeserialize(bytes);
            
            if (obj != null) {
                System.out.println("Получен объект: " + obj);
            }
            if (obj instanceof String) {
                String message = (String) obj;
                if (message.equals("exit")) {
                    work = false;
                    return;
                } else if (message.equals("discon")) {
                    clientChannel.close();
                    throw new EOFException("Канал закрыт");
                } else {
                    //Answer
                    ByteBuffer outBuffer = ByteBuffer.wrap(Serialize.serializeObject("Длина строки: " + message.length()));
                    clientChannel.write(outBuffer);
                }
            }
            /*    //Очистка
                arrByte.reset();
            }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
        }
        byte[] dataToSend = baos.toByteArray();

        // Отправка данных
        ByteBuffer buffer = ByteBuffer.allocate(4 + dataToSend.length);
        buffer.putInt(dataToSend.length); // длина объекта
        buffer.put(dataToSend);
        buffer.flip();

        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }*/
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при попытке получить запрос");
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