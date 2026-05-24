package ru.kessi.client.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import ru.kessi.client.io.Input;
import ru.kessi.common.Request;

public class Client {
    //private String host;
    //private int port;
    private SocketChannel socketChannel;
    private String host;
    private int port;

    public Client() { //String host, int port) {
        //this.host = host;
        //this.port = port;
    }

    public void createClient(){
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            System.out.println("Клиент создан");
        } catch (Exception e) {
            System.out.println("Ошибка при попытке создать клиента");
        }
    }

    public void closeClient(){
        try {
            if (socketChannel != null && socketChannel.isOpen()) {
                socketChannel.close();
            }
            System.out.println("Клиент удалён");
        } catch (Exception e) {
            System.out.println("Ошибка при попытке удалить клиента");
        }
    }

    public void run(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            createClient();
            System.out.println("Ожидаем подключения сервера ...");
            if (connectRetry()) {
                System.out.println("Сервер " + socketChannel.getRemoteAddress() + " подключен");
                request();
            }
        } catch (Exception e) {
            System.out.println("Ошибка при попытке подключить сервер");
        } finally {
            closeClient();
        }

    }
    private boolean connectRetry() {
        int maxAttempt = 100;
        int attempt = 0;
                
        System.out.println("Попытка установить связь с сервером...");

        if (socketChannel != null && socketChannel.isOpen()) {
            //try {
                //socketChannel.close();
                //socketChannel = SocketChannel.open();
                closeClient();
                createClient();
            //} catch (IOException e) {
            //    System.out.println("Ошибка при попытке переподключить сервер");
            //}
            

        }

        while (attempt < maxAttempt) {
            attempt++;
            try {
                if (socketChannel.connect(new InetSocketAddress(host, port))) {
                    return true;
                } else {
                    // Неблокирующее подключение не завершено сразу
                    System.out.println("Попытка " + attempt + ": подключение ещё не завершено, ждём...");
                    Thread.sleep(1000); // Ждём 1 секунду перед проверкой
                    if (socketChannel.finishConnect()) {
                        return true;
                    }
                }
            } catch (ConnectException e) {
            System.out.println("Попытка " + attempt + " не удалась: сервер недоступен (" + e.getMessage() + ")");
        } catch (NoRouteToHostException e) {
            System.out.println("Попытка " + attempt + " не удалась: хост недоступен (" + e.getMessage() + ")");
        } catch (IOException e) {
            System.out.println("Попытка " + attempt + " не удалась: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Попытка " + attempt + " не удалась (неизвестная ошибка): " + e.getMessage());
        }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.getStackTrace();
                break;
            }
        }

        System.out.println("Достигнут лимит попыток подключения. Проверьте доступность сервера.");
        return false;
    }


    public void request() {
        try(Scanner scanner = new Scanner(System.in)) {
            while (true) {
                Request req = Input.start(scanner);
                if (req.getCommand() != null) {
                    System.out.println("##################");
                    System.out.println("Передаём данные:");
                    System.out.println(req.getCommand().toString());
                    if(req.getLabWork() != null) System.out.println(req.getLabWork().toString());
                    if(req.getArgs() != null) System.out.println(req.getArgs().toString());
                    System.out.println("##################");
                }
                if (req.getCommand().getName().equals("help")) {
                   continue;
                }
                //line = scanner.nextLine();
                byte[] serializedObject = Serialize.serializeObject(req);
                ByteBuffer buffer = ByteBuffer.allocate(4 + serializedObject.length);
                buffer.putInt(serializedObject.length);
                buffer.put(serializedObject);
                buffer.flip();
                while (buffer.hasRemaining()) {
                    socketChannel.write(buffer);
                }
                if (req.getCommand().toString().equals("command 'exit'") || req.getCommand().toString().equals("command 'stop'")) {
                   break;
                }
                buffer.clear();
                answerServer();

                /*Object response = in.readObject();
                System.out.println("Ответ сервера: " + response);*/
            }

        } catch (IOException e) {
            if (connectRetry()) {
                request();
            }
            e.printStackTrace();
            System.out.println("Ошибка при попытке передать запрос");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка при попытке передать запрос");
        }
    }
    public void answerServer() {
        try {
            ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
            while (lengthBuffer.hasRemaining()) {
                int c = socketChannel.read(lengthBuffer);
                if (c == -1) {
                    System.out.println("нет ответа:(");
                    return;
                } else if (c == 0) {
                    // Нет данных, ждём следующего события
                    System.out.println("Нет данных, ждём следующего события");
                    return;
                }   
            }
            lengthBuffer.flip();
            int size = lengthBuffer.getInt();

            if(size <= 0) {
                System.out.println("Неверный размер");
                return;
            }

            ByteBuffer buf = ByteBuffer.allocate(size);
            ByteArrayOutputStream arrByte = new ByteArrayOutputStream();
            while (buf.hasRemaining()) {
                int c = socketChannel.read(buf);
                if (c == -1) {
                    System.out.println("не работает?:(");
                    return;
                }
            }
            buf.flip();
            byte[] bytes = new byte[buf.remaining()];
            buf.get(bytes);
            String req = Serialize.tryDeserialize(bytes);
            if (req == null) {
                return;
            }
            if (req != null) {
                System.out.println("Получен объект: " + req);
            } 
        } catch (Exception e) {
            // TODO: handle exception
        }
        

    }
}
/*ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
while (lengthBuffer.hasRemaining()) {
    int c = clientChannel.read(lengthBuffer);
    if (c == -1) {
        clientChannel.close();
        arrByteMapForClients.remove(clientChannel);
        System.out.println("Клиент отключился");
        return;
    } else if (c == 0) {
        // Нет данных, ждём следующего события
        System.out.println("Нет данных, ждём следующего события");
        return;
    }   
}
lengthBuffer.flip();
int size = lengthBuffer.getInt();
System.out.println("][" + size);
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
        return;
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
 */

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

