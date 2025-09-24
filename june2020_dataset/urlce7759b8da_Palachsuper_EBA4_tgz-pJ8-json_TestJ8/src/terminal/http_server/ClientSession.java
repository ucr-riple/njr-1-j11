package terminal.http_server;

/**
 * Created with IntelliJ IDEA.
 * User: Palachsuper
 * Date: 26.05.14
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import json.JSONObject;
import json.JSONException;
import java.util.Date;
import terminal.service.Service;
import terminal.devices.cashe_machine;
/** * Обрабатывает запрос клиента. */
public class ClientSession implements Runnable {
    @Override public void run() {
        try {
        /* Получаем заголовок сообщения от клиента */

            StringBuilder sb =  readHeader();
            // такие махинации для удобства распарса из StringBuilder
            String header = sb.toString();
            //Service.WriteLog(header);
            Scanner scan = new Scanner(header);
            while (scan.hasNextLine() ){
                String oneLine = scan.nextLine();
                //Service.WriteLog("Вот эту строку разбираем в массив  "+oneLine);
                Service.Parser_comand(oneLine);
            }
            /* Получаем из заголовка указатель на интересующий ресурс */
            //String url = getURIFromHeader(sb);
            //Service.WriteLog("Resource: " + url);
            /* Отправляем содержимое ресурса клиенту */
            //JSONObject ss = ;
            String string_answer=cashe_machine.send_json_cashe_answ;

            int code = send(string_answer);
            HttpServer.a=true;
            //Service.WriteLog("Result code: " + code);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ClientSession(Socket socket) throws IOException {
        this.socket = socket;
        initialize();
    }

    private void initialize() throws IOException {
    /* Получаем поток ввода, в который помещаются сообщения от клиента */
        in = socket.getInputStream();
        /* Получаем поток вывода, для отправки сообщений клиенту */
        out = socket.getOutputStream();
    }
    /** * Считывает заголовок сообщения от клиента.
     * * @return строка с заголовком сообщения от клиента.
     * * @throws IOException */

    private StringBuilder readHeader() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();
        String ln = null;
        while (true) {
            ln = reader.readLine();
            if (ln == null || ln.isEmpty()) {
                break;
            }
            builder.append(ln + System.getProperty("line.separator"));
            //StringBuilder = builder.toString();
        }
        return builder;}   //toString(); }
    /** * Вытаскивает идентификатор запрашиваемого ресурса из заголовка сообщения от * клиента. *
     * * @param header * заголовок сообщения от клиента.
     * * @return идентификатор ресурса. */

    private String getURIFromHeader(StringBuilder header) {

        //header.
        int from = header.indexOf(" ") + 1;
        int to = header.indexOf(" ", from);
        String uri = header.substring(from, to);
        int paramIndex = uri.indexOf("?");
        if (paramIndex != -1) {
            uri = uri.substring(0, paramIndex);
        }
        return DEFAULT_FILES_DIR + uri; }
    /** * Отправляет ответ клиенту. В качестве ответа отправляется http заголовок и
     * * содержимое указанного ресурса. Если ресурс не указан, отправляется
     * * перечень доступных ресурсов. *
     * * @param url * идентификатор запрашиваемого ресурса.
     * * @return код ответа. 200 - если ресурс был найден, 404 - если нет.
     * * @throws IOException */

    private int send(String url) throws IOException {
        int code = 200;
        String header = getHeader(code);
        PrintStream answer = new PrintStream(out, true, "UTF-8");
        //Service.WriteLog("Записываем ответ:" + header);
        answer.print(header);
        return code;
    }
    /** * Возвращает http заголовок ответа. *
     * * @param code * код результата отправки.
     * * @return http заголовок ответа. */

    private String getHeader(int code) {
        String s = cashe_machine.send_json_cashe_answ;
        StringBuffer buffer = new StringBuffer();
        buffer.append("HTTP/1.1 " + code + " " + getAnswer(code) + "\n");
        //buffer.append("Date: " + new Date().toGMTString() + "\n");
        buffer.append("Access-Control-Allow-Origin:*"+"\n");
        buffer.append("Accept-Ranges: none\n");
        buffer.append("Status: 200 OK");
        buffer.append("Transfer-Encoding: chunked");
        //buffer.append("JSONData:{ command: \"print\", error: \"0\" }" + "\n");
        buffer.append("Content-Type: application/json; charset=UTF-8\n");
        int len = s.length();
        buffer.append("Content-Length: "+len +"\n");
        buffer.append("Connection: close\n\n");
        buffer.append(s);
        return buffer.toString();
    }
    /** * Возвращает комментарий к коду результата отправки. *
     * * @param code * код результата отправки.
     * * @return комментарий к коду результата отправки. */

    private String getAnswer(int code) {
        switch (code) {
            case 200: return "OK";
            case 404: return "Not Found";
            default: return "Internal Server Error";
        }
    }

    private Socket socket;
    private InputStream in = null;
    private OutputStream out = null;
    private static final String DEFAULT_FILES_DIR = "/www";
}