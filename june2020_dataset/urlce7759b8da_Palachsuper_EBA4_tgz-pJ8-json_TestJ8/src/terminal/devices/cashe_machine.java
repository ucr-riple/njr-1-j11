package terminal.devices;

/**
 * Created with IntelliJ IDEA.
 * User: Palachsuper
 * Date: 23.04.14
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import json.JSONObject;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import terminal.service.Service;
import terminal.tools.HexTools;
import terminal.tools.MCrypt;

import java.io.*;
import java.util.HashMap;
import terminal.devices.Device;
import java.util.concurrent.TimeUnit;

public class cashe_machine
{

    static Device d;
    static Service s;
    static String port     = "";
    static int    parity   = 0; // PARITY_NON
    static int    portRate = 9600;
    static  boolean    rts = true;
    static int status;
    static InputStream portInputStream = null;
    static OutputStream portOutputStream = null;
    static CommPort commPort = null;
    static int timeout = 0;
    private boolean FLAG_TRANSACTION = false; // флаг сеанса запрос-ответ
    private boolean FLAG_ACCEPT_ACK = false; // ACK - флаг успешного получения запроса (начало отправки ответа)
    private boolean FLAG_ACCEPT_SYNC = false; // флаг получения начала ответа
    private boolean FLAG_ACCEPT_ADR = false; // флаг получения кода абонента
    private boolean FLAG_ACCEPT_LNG = false; // флаг получения длины ответа
    private boolean FLAG_RESPONSE_COMPLETE = false; // флаг успешного получения ответа
    public static String send_json_cashe_answ;
    private static boolean FLAG_INITIALIZED = false; // флаг успешной инициализации
    public static boolean isConnect = false;
    public static boolean isBillGetMoney = false;
    private String ENABLE_BILL_TYPES_CURRENCY = "";
    private int ENABLE_BILL_TYPES_NOMINAL_LIMIT = 0;
    private boolean ENABLE_BILL_TYPES_ESCROW_MODE = false;
    private String ENABLE_BILL_TYPES_CALLBACK = "";
    public static boolean isFirstBill=true;
    public static boolean FLAG_ENABLE_BILL_TYPES = false; // флаг ожидания вставки купюры
    public static boolean FLAG_ENABLE_BILL_TYPES_ALARM_DISABLE = false; // флаг аварийного завершения потока-монтора
    private static Thread EnableBillTypesMonitorThread = null; // поток мониторинга вставки купюры
    private boolean FLAG_ACCEPTED_BILL = false; // принята купюра
    private int ACCEPTED_BILL_CODE = 0; // код принятой купюры
    private String ACCEPTED_BILL_NOMINAL = null; // номинал принятой купюры
    private String STOP_ENTER_POINT = ""; // точка вызова останова купюрника
    private String STOP_GET_MONEY_CALLBACK = ""; // callback вызова останова купюрника

    private int deviceAnswerLNG = 0;
    private byte[] deviceAnswerDATA;
    private int deviceAnswerDATALength = 0;
    private String deviceAnswerSTATECode;
    private String deviceAnswerSTATE;
    public static int col_bad=0;
    //private MCrypt mcrypt;
    public int total = 0;

    private HashMap<String,String> commandMap;
    private HashMap<String,String> stateMap;
    private HashMap<String,String> sDescMap;
    enum MyEnum {
        start,
        stop
    }
    /* currencyCode - ASCII-коды валют в прошивках
        multi: 555341 - usd, 455552 - eur, 525553 - rus
        rurer: 525553 - rus
        bulk:  525542 - rub
    */
    private String[] currencyCode = { "810", "810", "810", "840", "840", "978" };
    private String[] currencyName = { "RUR", "RUS", "RUB", "USD", "USA", "EUR" };
    private HashMap<Integer, String[]> denominationMap = new HashMap<Integer,String[]>();
    private HashMap<String, String> allowCurrencyName = new HashMap<String,String>();
   public static JSONObject cashe_JSON;
    public static String id_payment="";

    public cashe_machine(Service service)
    {
        this.commandMap = new HashMap<String,String>();
        this.commandMap.put("reset","30"); // обнуление
        this.commandMap.put("get_bill_table","41"); // таблица валют/номиналов (прошивка)
        this.commandMap.put("set_security","32");	// установка уровня защиты
        this.commandMap.put("get_status","31"); // проверка текущих установок
        this.commandMap.put("cassete_status","3b"); // проверка кассет
        this.commandMap.put("poll","33"); // опрос состояния
        this.commandMap.put("enable_bill_types","34"); // прием купюр
        this.commandMap.put("unload","3d"); // удержать на 10 сек.
        this.commandMap.put("hold","38"); // удержать на 10 сек.
        this.commandMap.put("stack","35"); // проглотить (после удержания)
        this.commandMap.put("return","36");	// вернуть (после удержания)

        this.stateMap = new HashMap<String,String>();
        this.sDescMap = new HashMap<String,String>();
        this.stateMap.put("00","None");
        this.sDescMap.put("00","None");
        this.stateMap.put("10","Power Up");
        this.sDescMap.put("10","The state of Bill-to-Bill unit after power up");
        this.stateMap.put("11","Power Bill");
        this.sDescMap.put("11","Power up with bill in validating head. After a RESET command from the Controller, the Bill-to-Bill unit returns the bill and continues initializing.");
        this.stateMap.put("1200","Power w/Bill in Transport Path");
        this.sDescMap.put("1200","Power up with bill in transport section. After a RESET command from the Controller, the Bill-to-Bill unit stacks the bill to the drop cassette and continues initializing.");
        this.stateMap.put("1201","Power w/Bill in Dispenser");
        this.sDescMap.put("1201","Power up with bills in dispenser. After a RESET command from the Controller, the Bill-to-Bill unit returns the bills to exit bezel and continues initializing.");
        this.stateMap.put("13","Initialize");
        this.sDescMap.put("13","The state, in which Bill-to-Bill unit executes initialization after RESET command from the Controller.");
        this.stateMap.put("14","Idling");
        this.sDescMap.put("14","In this state Bill-to-Bill unit waits for bill insertion.");
        this.stateMap.put("15","Accepting");
        this.sDescMap.put("15","In this state Bill-to-Bill unit executes scanning of a bill and determines its denomination.");
        this.stateMap.put("17","Stacking");
        this.sDescMap.put("17","In this state, the Bill-to-Bill unit transports a bill from Escrow position to the recycling cassette or to the drop box and remains in this state until the bill is stacked or returned if jammed.");
        this.stateMap.put("18","Returning");
        this.sDescMap.put("18","In this state Bill-to-Bill unit transports a bill from Escrow position to entry bezel and remains in this state until the bill is removed by customer.");
        this.stateMap.put("19","Unit Disabled");
        this.sDescMap.put("19","The Bill-to-Bill unit has been disabled by the Controller and also the state in which Bill-to-Bill unit is after initialization");
        this.stateMap.put("1a","Holding");
        this.sDescMap.put("1a","The state, in which the bill is held in Escrow position after the HOLD command from the Controller.");
        this.stateMap.put("1b","Device Busy");
        this.sDescMap.put("1b","The state, in which Bill-to-Bill unit cannot answer a detailed command right now. On expiration of time YH, peripheral is accessible for polling. YH is expressed as multiple of 100 milliseconds.");
        this.stateMap.put("1c60","Rejecting due to Insertion");
        this.sDescMap.put("1c60","Insertion error");
        this.stateMap.put("1c61","Rejecting due to Magnetic");
        this.sDescMap.put("1c61","Dielectric error");
        this.stateMap.put("1c62","Rejecting due to Remained bill in head");
        this.sDescMap.put("1c62","Previously inserted bill remains in head");
        this.stateMap.put("1c63","Rejecting due to Multiplying");
        this.sDescMap.put("1c63","Compensation error/multiplying factor error");
        this.stateMap.put("1c64","Rejecting due to Conveying");
        this.sDescMap.put("1c64","Bill transport error");
        this.stateMap.put("1c65","Rejecting due to Identification1");
        this.sDescMap.put("1c65","Identification error");
        this.stateMap.put("1c66","Rejecting due to Verification");
        this.sDescMap.put("1c66","Verification error");
        this.stateMap.put("1c67","Rejecting due to Optic");
        this.sDescMap.put("1c67","Optic Sensor error");
        this.stateMap.put("1c68","Rejecting due to Inhibit");
        this.sDescMap.put("1c68","Return by -inhibit denomination- error");
        this.stateMap.put("1c69","Rejecting due to Capacity");
        this.sDescMap.put("1c69","Capacitance error");
        this.stateMap.put("1c6a","Rejecting due to Operation");
        this.sDescMap.put("1c6a","Operation error");
        this.stateMap.put("1c6c","Rejecting due to Length");
        this.sDescMap.put("1c6c","Length error");
        this.stateMap.put("1d","Dispensing");
        this.sDescMap.put("1d","Bill-to-Bill unit enters this state after DISPENSE command. In this state Bill-to-Bill unit transports a bill from recycling cassette(s) to customer through dispenser and remains in this state until the bill(s) are removed by customer or jammed.");
        this.stateMap.put("1e","Unloading");
        this.sDescMap.put("1e","Transporting bills to drop cassette.");
        this.stateMap.put("1f","Custom returning");
        this.sDescMap.put("1f","Bill-to-Bill unit pass to this state after RECYCLING CASSETTE UNLOAD command if high bit of data byte is set. In this state Bill-to-Bill unit transports bill(s) from recycling cassette to customer through dispenser and remains in this state until the customer removes the bill(s).");
        this.stateMap.put("20","Recycling unloading");
        this.sDescMap.put("20","Bill-to-Bill unit passes to this state after RECYCLING CASSETTE UNLOAD command if high bit of data byte is dropped. In this state Bill-to-Bill unit transports bill from recycling cassette to recycle cassette  of appropriate bill type or to drop cassette.");
        this.stateMap.put("21","Setting cassette type");
        this.sDescMap.put("21","Unloading of the recycling cassette is carried out and cassette is reassigned to new bill type");
        this.stateMap.put("25","Dispensed");
        this.sDescMap.put("25","Dispensing is completed.");
        this.stateMap.put("26","Unloaded");
        this.sDescMap.put("26","Unloading is completed");
        this.stateMap.put("27","Custom bills returned");
        this.sDescMap.put("27","Bills are returned to customer.");
        this.stateMap.put("28","Recycling cassette unloaded");
        this.sDescMap.put("28","Recycling Cassette unloading is completed.");
        this.stateMap.put("29","Set cassette type");
        this.sDescMap.put("29","Setting recycling cassette type is completed.");
        this.stateMap.put("30","Invalid command");
        this.sDescMap.put("30","Command from the Controller is not valid.");
        this.stateMap.put("41","Drop Cassette Full");
        this.sDescMap.put("41","Drop Cassette full condition");
        this.stateMap.put("42","Drop Cassette out of position");
        this.sDescMap.put("42","The Bill Validator has detected the drop cassette to be open or removed.");
        this.stateMap.put("43","Validator Jammed");
        this.sDescMap.put("43","A bill(s) has jammed in the acceptance path.");
        this.stateMap.put("44","Drop Cassette Jammed");
        this.sDescMap.put("44","A bill has jammed in drop cassette.");
        this.stateMap.put("45","Cheated");
        this.sDescMap.put("45","The Bill-to-Bill unit sends this event if the intentions of the user to deceive the Bill-to-Bill unit are detected.");
        this.stateMap.put("46","Pause");
        this.sDescMap.put("46","The Bill-to-Bill unit reaches this state when the user tries to insert a bill before the previous bill is stacked. Bill-to-Bill unit stops motion of the bill until the entry channel is cleared.");
        this.stateMap.put("4750","Stack Motor Failure");
        this.sDescMap.put("4750","Drop Cassette Motor failure");
        this.stateMap.put("4751","Transport Motor Speed Failure");
        this.sDescMap.put("4751","Transport Motor Speed failure");
        this.stateMap.put("4752","Transport Motor Failure");
        this.sDescMap.put("4752","Transport Motor failure");
        this.stateMap.put("4753","Aligning Motor Failure");
        this.sDescMap.put("4753","Aligning Motor failure");
        this.stateMap.put("4754","Initial Cassette Status Failure");
        this.sDescMap.put("4754","Initial Cassette Status failure");
        this.stateMap.put("4755","Optic Canal Failure");
        this.sDescMap.put("4755","One of the optic sensors has failed to provide its response.");
        this.stateMap.put("4756","Magnetic Canal Failure");
        this.sDescMap.put("4756","Inductive sensor failed to respond");
        this.stateMap.put("4757","Cassette 1 Motor Failure");
        this.sDescMap.put("4757","Recycling Cassette 1 Motor Failure");
        this.stateMap.put("4758","Cassette 2 Motor Failure");
        this.sDescMap.put("4758","Recycling Cassette 2 Motor Failure");
        this.stateMap.put("4759","Cassette 3 Motor Failure");
        this.sDescMap.put("4759","Recycling Cassette 3 Motor Failure");
        this.stateMap.put("475a","Bill-to-Bill unit Transport Motor Failure");
        this.sDescMap.put("475a","One of the Bill-to-Bill unit Transport Motors failure");
        this.stateMap.put("475b","Switch Motor 1 Failure");
        this.sDescMap.put("475b","Switch Motor 1 Failure");
        this.stateMap.put("475c","Switch Motor 2 Failure");
        this.sDescMap.put("475c","Switch Motor 2 Failure");
        this.stateMap.put("475d","Dispenser Motor 1 Failure");
        this.sDescMap.put("475d","Dispenser Motor 1 Failure");
        this.stateMap.put("475e","Dispenser Motor 2 Failure");
        this.sDescMap.put("475e","Dispenser Motor 2 Failure");
        this.stateMap.put("475f","Capacitance Canal Failure");
        this.sDescMap.put("475f","Capacitance sensor failed to respond");
        this.stateMap.put("4870","Bill Jammed in Cassette 1");
        this.sDescMap.put("4870","A bill is jammed in Recycling Cassette 1");
        this.stateMap.put("4871","Bill Jammed in Cassette 2");
        this.sDescMap.put("4871","A bill is jammed in Recycling Cassette 2");
        this.stateMap.put("4872","Bill Jammed in Cassette 3");
        this.sDescMap.put("4872","A bill is jammed in Recycling Cassette 3");
        this.stateMap.put("4873","Bill Jammed in Transport Path");
        this.sDescMap.put("4873","A bill is jammed in Transport Path");
        this.stateMap.put("4874","Bill Jammed in Switch");
        this.sDescMap.put("4874","A bill is jammed in Switch");
        this.stateMap.put("4875","Bill Jammed in Dispenser");
        this.sDescMap.put("4875","A bill is jammed in Dispenser");
        this.stateMap.put("80","Escrow position");
        this.sDescMap.put("80","Y = bill type (0 to 23).");
        this.stateMap.put("81","Bill stacked");
        this.sDescMap.put("81","Y = bill type (0 to 23)");
        this.stateMap.put("82","Bill returned");
        this.sDescMap.put("82","Y = bill type (0 to 23)");

        //mcrypt = new MCrypt(this.deviceName+10249);
    }

    public static void GenerateJSONAnswer(String answer){
        //Service.WritePaymentLog("Зашли и сгенерировали ответ вася!!!",id_payment);
         send_json_cashe_answ = "{\"command\":\"cashe\",\"params\":{\"error\":\"0\",\"amount\":\""+answer+"\",\"type\":\"RUR\"}}";
    }

    public static void main(JSONObject data){
        String pr_string="";
        cashe_JSON = data;
        try {
            pr_string = cashe_JSON.getString("params");
            id_payment = cashe_JSON.getString("id_payment");
            JSONObject param = new JSONObject(pr_string);
            String action_cmd = param.getString("action");
            try {
                MyEnum e = MyEnum.valueOf(action_cmd);
                switch (e) {
                    case start:
                        Service.WritePaymentLog("стартанули",id_payment);
                        BillToBill();  // запускаем функцию приема денег
                        break;
                    case stop:
                        Service.WritePaymentLog("остановили",id_payment);
                        StopBillToBill();     // останавливаем функцию приема денег
                        break;
                }
            } catch (IllegalArgumentException iae) {
                /// опа, мы получили неизвестное значение. Можно присвоить
                // некое default и продолжить обрабатывать его в case, а можно бросить
                // наверх
            }
        }
        catch (Exception e){

        }
    }


    public static void Bad_cashe(){
           if (col_bad==0) {Service.WritePaymentLog("Ошибка приема купюры",id_payment);}
    }

    public static void BillToBill()
    {
        Service.WritePaymentLog("прием денег!",id_payment);
        isBillGetMoney=true;
        // Проверка подключенного устройства
        if (!isConnect){
            d = new Device();
            try{
                if (d.connect())
                    isConnect=true;
            }
            catch (Exception e){

            }
        }
        // Инициализируем класс и погнали бабки принимать
        cashe_machine cm = new cashe_machine(s);
        cm.enableBillTypes();
    }

    public static void StopBillToBill(){
        Service.WritePaymentLog("Функция стоп мани нажата",id_payment);
        d.disConnect();
        isConnect=false;
        isBillGetMoney=false;
        isFirstBill=true;
        send_json_cashe_answ="{\"command\":\"cashe\",\"error\":\"0\"}";
    }

    // запрос на прием купюр
    //public void enableBillTypes(JSONObject cmd, String callback)
    public void enableBillTypes()
    {
        if (isFirstBill){
            isFirstBill=false;
            reset();
            ack();
            d.readToPort();
            try {Thread.sleep(900L);} catch (InterruptedException e) {e.printStackTrace();}
            d.writeToPort("02030C34FFFFFF000000B5C1");
            try {
                TimeUnit.MILLISECONDS.sleep(30);
            }
            catch (Exception e){

            }
            d.readToPort();
            d.readToPort();
            poll(true);
            d.readToPort();
            ack();
            d.readToPort();
        }
        for (int i=1;i<30000;i++)
        {
            try {Thread.sleep(100L);} catch (InterruptedException e) {e.printStackTrace();}
            d.writeToPort("02030600C282");
            d.writeToPort("02030633DA81");
            d.readToPort();
            if (!isBillGetMoney)
            {
                i=30000;
            }
        }

    }

    public boolean poll(boolean logging)
    {
        if (this.executeDeviceCommand("poll",null,true))
        {
            return true;
        }
        return false;
    }

    public boolean enableBillTypesProcedure(boolean isValid, boolean isExternalCall)
    {
       // Service.WriteLog("в enableBillProcedure зашел");
        // проверка при внешнем запуске: если уже запущен поток-монитор, убиваем его
        this.checkMonitor();
        if (this.powerUpAndReset(true))	// если не встал в режим приема купюр, делаем обнуление
        {
            // снова пробуем режим приема купюр
        }
        if (this.poll(true))
        {
           /* if (this.deviceAnswerSTATE.equals("Drop Cassette out of position"))
            {
                if (this.powerUpAndReset(true))	// если не встал в режим приема купюр, делаем обнуление
                {
                    return this.enableBillTypesProcedure(isValid, isExternalCall);
                }
            }    */
            // создаем поток для мониторинга вставки купюр
            EnableBillTypesMonitorThread = new Thread(new EnableBillTypesMonitor(this));
            // запуск потока мониторинга вставки купюр
            EnableBillTypesMonitorThread.start();
            return true;
        }

        return false;
    }


    public boolean enableBillTypesMonitor()
    {
        if (FLAG_ENABLE_BILL_TYPES)
        {
            if (this.poll(true))
            {
                if (this.deviceAnswerSTATE.equals("Unit Disabled")) // купюрник отключился после принятия купюры, включаем снова
                {
                    // убиваем поток мониторинга вставки купюр
                    FLAG_ENABLE_BILL_TYPES = false;
                    ack();
                    return this.enableBillTypesProcedure(true, false);
                }

                if (this.FLAG_ACCEPTED_BILL)
                {
                    this.total += Integer.parseInt(this.ACCEPTED_BILL_NOMINAL);

                    if (this.disableBillTypes("0"))
                    {
                        // убиваем поток мониторинга вставки купюр
                        FLAG_ENABLE_BILL_TYPES = false;
                        ack();
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean disableBillTypes(String enterPoint)
    {
        if (this.executeDeviceCommand("enable_bill_types","000000000000",true))
        {

        }
        return true; // всегда отправляем true, чтобы платеж успешно выполнился
    }

    // остановка приема купюр
    public void stopMonitor()
    {
        if (this.disableBillTypes(this.STOP_ENTER_POINT))
        {
            for (int i=0; i<30; i++)
            {
                if (this.poll(true))
                {
                    if (this.deviceAnswerSTATE.equals("Unit Disabled"))
                    {
                        if (!this.STOP_GET_MONEY_CALLBACK.equals(""))
                            return;
                    }
                }
            }
        }
        this.STOP_GET_MONEY_CALLBACK = "";
        this.powerUpAndReset(true);	// если не остановил прием купюр, делаем обнуление
    }

    private boolean setSecurity()
    {
        String data = "000000"; // низкий уровень защиты
        if (this.executeDeviceCommand("set_security",data,true))
        {
           return true;
           // return (this.getDeviceAnswerDATA().equals("00"));
        }
        return false;
    }

    private String getBillTable()
    {
        if (this.executeDeviceCommand("get_bill_table",null,true))
        {
            return this.getDeviceAnswerDATA();
        }
        return null;
    }

    private String getCurrencyCodeByName(String curName)
    {
        for (int i=0; i<this.currencyName.length; i++)
        {
            if (this.currencyName[i].equals(curName))
            {
                return this.currencyCode[i];
            }
        }
        return null;
    }

    private String getCurrencyNameByCode(String curCode)
    {
        for (int i=0; i<this.currencyCode.length; i++)
        {
            if (this.currencyCode[i].equals(curCode))
            {
                return this.currencyName[i];
            }
        }
        return null;
    }

    private boolean detectCasherType()
    {
        String table = this.getBillTable();
        int n = 0;

        if (table.length() == 240)
        {
            int k = 0;
            String nom = "";
            String curNameReal = "";
            String curName = "";
            String curCode = "";

            for (int i=0; i<240; i=i+10)
            {
                nom = "";
                curNameReal = "";
                curName = "";
                curCode = null;
                String name = table.substring(i+2,i+8);
                if (!name.equals("000000"))
                {
                    curNameReal = HexTools.hexToAscii(name);
                    curCode = this.getCurrencyCodeByName(curNameReal);
                }

                if (curCode != null) // валидная валюта
                {
                    curName = this.getCurrencyNameByCode(curCode);
                    //log("curName:"+curName);
                    this.allowCurrencyName.put(curName,curCode);
                    int dig = Integer.parseInt(table.substring(i+9,i+10));
                    String zeroes = (dig > 0) ? String.format("%0" + dig + "d", 0) : "";
                    //log("sub0:"+table.substring(i,i+2)+" | sub1:"+table.substring(i+9,i+10));
                    nom = Integer.parseInt(table.substring(i,i+2)) + zeroes; // вещ. + нолики-десятки
                    //log("nom:"+nom);
                    this.denominationMap.put(k, new String[]{curNameReal, curName, curCode, nom});
                    n++;
                }
                else
                {
                    if (curNameReal.length()==0)
                        curNameReal = "   ";
                    curName = "   ";
                    curCode = "   ";
                    this.denominationMap.put(k, new String[]{"", "", "", ""});
                }
                k++;
            }
        }

        return (n>0) ? true : false;
    }

    private boolean powerUpAndReset(boolean isRepeat)
    {
        //Service.WriteLog("Зашла в powerUpAndReset");
        if (this.poll(true))
        {
            this.ack();
            if (this.reset())
            {
                //Service.WriteLog("крути верти 200 раз");
                for (int i=0; i<200; i++)
                {
                    if (this.poll(true))
                    {
                       // if (this.deviceAnswerSTATE.equals("Unit Disabled"))
                      //  {
                            if (this.setSecurity())
                            {
                                if (this.detectCasherType())
                                {
                                    FLAG_INITIALIZED = true;
                                    //return true;
                                }
                            }
                        }
                    //}
                }
            }
        }
        return true;
        //return (isRepeat) ? this.powerUpAndReset(false) : false;
    }

    private void ack()
    {
       // Service.WriteLog("Зашли в ACK");
        // пауза 50 мс между командами
        try {Thread.sleep(50L);} catch (InterruptedException e) {e.printStackTrace();}
        d.writeToPort("02030600c282");
    }

    private boolean reset()
    {
       // Service.WriteLog("Зашли в reset");
        if (this.executeDeviceCommand("reset",null,true))
        {
            return true; //(this.getDeviceAnswerDATA().equals("00"));
        }
        return false;
    }

    private boolean executeDeviceCommand(String requestCommand, String data, boolean isRepeat)
    {
       // Service.WriteLog("в execute зашел");
        String command = getCommand(getCommandCode(requestCommand), data);
        // пауза 100 мс между командами
        try {Thread.sleep(100L);} catch (InterruptedException e) {e.printStackTrace();}
        // сброс флагов
        this.deviceAnswerLNG = 0;
        this.deviceAnswerSTATE = null;
        this.deviceAnswerSTATECode = null;
        this.FLAG_TRANSACTION = false;
        this.FLAG_RESPONSE_COMPLETE = false;
        this.FLAG_ACCEPT_ACK = false;
        this.FLAG_ACCEPT_SYNC = false;
        this.FLAG_ACCEPT_ADR = false;
        this.FLAG_ACCEPT_LNG = false;
        if (d.writeToPort(command))
        {
           // Service.WriteLog("в execute записался в порт");
            return true;
            /*this.FLAG_TRANSACTION = true;
            int i = 0;

            for(i = 0; i < 200; i++)
            {
                // пауза 50 мс
                try {Thread.sleep(50L);} catch (InterruptedException e) {e.printStackTrace();}
                if (this.FLAG_RESPONSE_COMPLETE)
                {
                    return true;
                }
            }
            if (isRepeat)
            {
                // пауза 2 с
                try {Thread.sleep(2000L);} catch (InterruptedException e) {e.printStackTrace();}
                return this.executeDeviceCommand(requestCommand, data, false);
            }  */


        }

        return false;
    }

    private String getCommand(String command, String data)
    {
       // Service.WriteLog("сформировал команду");
        byte[] dataByte = null;

        if (data != null && data.length() > 0)
        {
            dataByte = HexTools.fromHexString(data);
        }

        byte[] comm = HexTools.fromHexString(command);
        int length = 6;

        if (dataByte != null)
            length += dataByte.length;
        //log("length:"+length);
        byte[] packet = new byte[length];

        packet[0]=0x2;
        packet[1]=0x3;
        packet[2]=(byte)length;
        packet[3]=comm[0];

        if (data!=null)
        {
            for (int i=0; i<dataByte.length; i++)
            {
                packet[(4+i)]=dataByte[i];
                //log("dataByte[i]"+dataByte[i]);
            }
        }

        byte[] crc = HexTools.getCRC16(packet, length-2, 0x8408);
        packet[length-2] = crc[1];
        packet[length-1] = crc[0];

        return HexTools.getHexString(packet, length);
    }

    private String getCommandCode(String command)
    {
      //  Service.WriteLog("вернул код команды");
        return this.commandMap.get(command);
    }


    private String getDeviceAnswerDATA()
    {
        // конвертация в строку и отрезание блока CRC (2 последних байта)
        return HexTools.getHexString(this.deviceAnswerDATA, this.deviceAnswerDATALength-2);
    }

    private String getState(String code)
    {
        if (code.substring(0,1).equals("8")) // вставлена купюра
        {
            this.FLAG_ACCEPTED_BILL = true;
            byte[] vcode = HexTools.fromHexString(code.substring(2));
            this.ACCEPTED_BILL_CODE = (int)vcode[0];
            this.ACCEPTED_BILL_NOMINAL = this.getDenomination(this.ACCEPTED_BILL_CODE);
            return this.stateMap.get(code.substring(0,2));
        }
        else if (code.substring(0,2).equals("1b")) // busy state
        {
            return this.stateMap.get(code.substring(0,2)) + " (" + code.substring(2,4) + " ms)";
        }
        else
            return (this.stateMap.containsKey(code)) ? this.stateMap.get(code) : "undefined (" + code + ")";
    }

    private String getDenomination(int billСode)
    {
        if (this.denominationMap.containsKey(billСode))
        {
            String[] item = this.denominationMap.get(billСode);
            return item[3];
        }
        return "";
    }

    private boolean checkMonitor()
    {
        FLAG_ENABLE_BILL_TYPES = false;

        if (EnableBillTypesMonitorThread == null)
            return true;

        Thread.State monStat = EnableBillTypesMonitorThread.getState();

        if (monStat.equals(Thread.State.RUNNABLE) || monStat.equals(Thread.State.TIMED_WAITING))	// запущен поток-монитор
        {
            FLAG_ENABLE_BILL_TYPES_ALARM_DISABLE = true;

            // ждем закрытия потока-монитора
            while(true)
            {
                monStat = EnableBillTypesMonitorThread.getState();
                if (!monStat.equals(Thread.State.RUNNABLE) && !monStat.equals(Thread.State.TIMED_WAITING))
                    break;
            }
        }

        return true;
    }
}
