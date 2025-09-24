package terminal.http_server;

/**
 * Created with IntelliJ IDEA.
 * User: Palachsuper
 * Date: 26.05.14
 * Time: 11:15
 * To change this template use File | Settings | File Templates.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import terminal.devices.cashe_machine;
import terminal.service.Service;
/**
 * * Обрабатывает запросы от клиентов, возвращая файлы, указанные
 * * в url-path или ответ с кодом 404, если такой файл не найден. *
 * */
public class HttpServer {
    /** * Первым аргументом может идти номер порта. */
    public static Thread t;
    public static boolean a;
    public static void main(String args) {
    /* Если аргументы отсутствуют, порт принимает значение поумолчанию */
        int port = 20143;
        /* Создаем серверный сокет на полученном порту */
        ServerSocket serverSocket = null;
        try { serverSocket = new ServerSocket(port);
            Service.WriteLog("Server started on port: " + serverSocket.getLocalPort());
        }
        catch (IOException e)
        {
            Service.WriteLog("Port " + port + " is blocked.");
            //System.exit(-1);
        }
        a=true;
            /* * Если порт был свободен и сокет был успешно создан, можно переходить к * следующему шагу - ожиданию клинтов */
        while (true)
        {
            try {
                Socket clientSocket = serverSocket.accept();
                if (!a){
                    Service.WriteLog("Service thread is stop");
                    t.stop();
                    a=false;
                    ClientSession session = new ClientSession(clientSocket);
                    t=new Thread(session);
                    if(cashe_machine.id_payment!="")
                    Service.WriteLog("Service thread is start");
                    t.start();
                }
                else{
                    a=false;
                    ClientSession session = new ClientSession(clientSocket);
                    t=new Thread(session);
                    if(cashe_machine.id_payment!="")
                    Service.WriteLog("Service thread is start");
                    t.start();

                }
                /* Для обработки запроса от каждого клиента создается
                * * отдельный объект и отдельный поток */
            }
            catch (IOException e){
                Service.WriteLog("Failed to establish connection.");
                Service.WriteLog(e.getMessage());
                //System.exit(-1);
            }
        }
    }
}
