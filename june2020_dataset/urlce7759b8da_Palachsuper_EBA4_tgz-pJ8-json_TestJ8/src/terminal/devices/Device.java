package terminal.devices;

import gnu.io.*;
import json.JSONObject;
import terminal.service.Service;
import terminal.tools.HexTools;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Device implements SerialPortEventListener, Runnable {
     boolean flag = false;
    String answer_string;
    class MyRunnable implements Runnable {

        @Override
        public void run() {
            byte[] b = new byte [1024];
            try {
                if (portInputStream.read(b,0,30)!=-1)
                {
                    answer_string = HexTools.getHexString(b, 12);
                    //Service.WritePaymentLog(answer_string,cashe_machine.id_payment);
                    parse_bill(answer_string);
                    flag = true;
                }
                    else flag=false;
            } catch (IOException e) {
                flag = false;
            }
        }
    }


	public static final int DEVICE_NOT_INIT=0;
	public static final int DEVICE_READY=1;
	public static final int DEVICE_PORT_BUSY=2;
	public static final int DEVICE_PORT_NOT_FOUND=3;
	public static final int DEVICE_WORKING=10;
	protected String port     = "";
	private   int    parity   = 0; // PARITY_NON
	private   int    portRate = 9600;
	private   boolean    rts = true;
	private String os = "Linux";
    public CommPortIdentifier portIdentifier;
	public int status = 0;
	protected Boolean WinOS = null;
	protected int bufferByte = 0;
	protected String deviceName = "";
    public SerialPort serialPort;
	private InputStream portInputStream = null;
	private OutputStream portOutputStream = null;
	public static boolean isBill = false;

	protected Service service = null;
	protected String error = "";
	protected String debug = "";
	protected String method = "";
	protected CommPort commPort = null;
	protected int timeout = 0;
    enum MyBill {
        p020307810246090000000000, // 10 рублевая купюра
        p0203078103cf180000000000, // 50 рублевая купюра
        p0203078104706c0000000000, // 100 рублевая купюра
        p0203078105f97d0000000000, // 500 рублевая купюра
        p0203078106624f0000000000, // 1000 рублевая купюра
        p0203078107eb5e0000000000, // 5000 рублевая купюра
        p0203071c6077e00000000000, // купюра не прочиталась
        p0203061467d4000000000000  // готов к приему
    }

	public Device()
	{

	}

    public void parse_bill(String data_bill){
        data_bill="p"+data_bill;
        isBill=false;
        //Service.WriteLog(data_bill);
        try {
            MyBill b = MyBill.valueOf(data_bill);
            switch (b) {
                case p020307810246090000000000:
                    isBill=true;
                    cashe_machine.isBillGetMoney=false;
                    Service.WritePaymentLog("Вставлено 10 рублей",cashe_machine.id_payment);
                    cashe_machine.GenerateJSONAnswer("10");
                    break;
                case p0203078103cf180000000000:
                    isBill=true;
                    cashe_machine.isBillGetMoney=false;
                    Service.WritePaymentLog("Вставлено 50 рублей",cashe_machine.id_payment);
                    cashe_machine.GenerateJSONAnswer("50");
                    break;
                case p0203078104706c0000000000:
                    isBill=true;
                    cashe_machine.isBillGetMoney=false;
                    Service.WritePaymentLog("Вставлено 100 рублей",cashe_machine.id_payment);
                    cashe_machine.GenerateJSONAnswer("100");
                    break;
                case p0203078105f97d0000000000:
                    isBill=true;
                    cashe_machine.isBillGetMoney=false;
                    Service.WritePaymentLog("Вставлено 500 рублей",cashe_machine.id_payment);
                    cashe_machine.GenerateJSONAnswer("500");
                    break;
                case p0203078106624f0000000000:
                    isBill=true;
                    cashe_machine.isBillGetMoney=false;
                    Service.WritePaymentLog("Вставлена 1000 рублей",cashe_machine.id_payment);
                    cashe_machine.GenerateJSONAnswer("1000");
                    break;
                case p0203078107eb5e0000000000:
                    isBill=true;
                    cashe_machine.isBillGetMoney=false;
                    Service.WritePaymentLog("Вставлены 5000 рублей",cashe_machine.id_payment);
                    cashe_machine.GenerateJSONAnswer("5000");
                    break;
                case p0203071c6077e00000000000:
                    //Service.WritePaymentLog("прочитали плохую купюру",cashe_machine.id_payment);
                    cashe_machine.Bad_cashe();
                    cashe_machine.col_bad=200;
                    break;
                case p0203061467d4000000000000:
                    cashe_machine.col_bad=0;
                    break;
            }
        } catch (IllegalArgumentException iae) {
            /// опа, мы получили неизвестное значение. Можно присвоить
            // некое default и продолжить обрабатывать его в case, а можно бросить
            // наверх
        }
    }

	// запуск потока устройства
	public void run()
	{
		try {
			this.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// инициализация устройства
	public void init(JSONObject data)
	{
		String msg = "";
		
		try 
		{
			if (this.connect())
			{
				msg += "Connected (port="+this.port+"; rate="+this.portRate+"; parity="+this.parity+"; rts="+this.rts+"). ";
				msg += this.initDetails(data);
			}
			else
			{
				this.error = "Not connected (port="+this.port+"; rate="+this.portRate+"; parity="+this.parity+"; rts="+this.rts+")";
			}
		} 
		catch (Exception e)
		{
			this.error = "Not connected (port="+this.port+"; rate="+this.portRate+"; parity="+this.parity+"; rts="+this.rts+")";
		}
		
		this.method = "onInit"; // callback в javascript->DeviceConnector
	}
	
	// дополнительная реализация инициализации устройства (см. в потомках)
	public String initDetails(JSONObject data)
	{
		return "";
	}
	
	public void setPort(String port)
	{
		this.port = port;
	}
	
	public void setPortRate(int portRate)
	{
		this.portRate = portRate;
	}

    public static String getPropPort()
    {
        String dat_time_path="";
        StringBuilder sb = new StringBuilder();
        try
        {

            try
            {    dat_time_path= "/home/terminal/lib/prop_port.txt";
                BufferedReader in = new BufferedReader(new FileReader( new File(dat_time_path).getAbsoluteFile()));
                String read_log;
                while ((read_log = in.readLine()) != null)
                {
                    sb.append(read_log);
                }
                in.close();
            }
            catch(IOException e)
            {
                throw new RuntimeException(e);
            }
            String str="";
            str =sb+"";
            return str;
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        return "";
    }

    public boolean disConnect(){
        serialPort.close();
        return true;
    }


	public  boolean connect() throws Exception
    {
        port = getPropPort();
        portRate = 9600;
        //Service.WriteLog("Find port: "+port);
        parity = 0;
        rts = true;
		if (this.port.length()<1)
        {

            return false;
        }
        if (status == Device.DEVICE_READY || status == Device.DEVICE_WORKING)
        {

            return true;
        }
		portIdentifier = CommPortIdentifier.getPortIdentifier(this.port);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            status = Device.DEVICE_PORT_BUSY;
        }
        else
        {
            commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort)
            {
            	serialPort = (SerialPort) commPort;
            	serialPort.setSerialPortParams(this.portRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,this.parity);
            	serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            	serialPort.setDTR(true);
            	serialPort.setRTS(this.rts);
                serialPort.notifyOnDataAvailable(true);
                if (this.timeout>0)
                	serialPort.enableReceiveTimeout(this.timeout);
                //serialPort.notifyOnOutputEmpty(true);
                //serialPort.setOutputBufferSize(this.bufferByte);
                
                portInputStream = serialPort.getInputStream();
                portOutputStream = serialPort.getOutputStream();
                               
                serialPort.addEventListener(this);
               status = Device.DEVICE_READY;
               return true;
            }
            else
            {
            	status = Device.DEVICE_PORT_NOT_FOUND;
            }
        }     
        
        return false;
    }

	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
	      case SerialPortEvent.OE:
	      case SerialPortEvent.FE:
	      case SerialPortEvent.PE:
	      case SerialPortEvent.CD:
	      case SerialPortEvent.CTS:
	      case SerialPortEvent.DSR:
	      case SerialPortEvent.RI:
	      case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
	    	 break;
	      case SerialPortEvent.DATA_AVAILABLE:
	         byte[] readBuffer = new byte[this.bufferByte];
	         int numBytes = 0;
	         try {
	            // read data
	     			if (this.WinOS)
	     			{
	     				int data;
	     				while ( ( data = portInputStream.read()) > -1 )
	     				{
	     					if ( data == '\n' ) {
	     						break;
	     					}
	     					readBuffer[numBytes++] = (byte) data;
	     				}
	    			}
	     			else
	     			{
	     				while (portInputStream.available() > 0) {
	     					numBytes = portInputStream.read(readBuffer);
	     				} 
	     			}
	     			
	     			this.proceedPortBuffer(readBuffer, numBytes);
	            
	         } catch (IOException e) {
	         }
	   
	         break;
	      }
	}
	
	public void proceedPortBuffer(byte[] buffer, int len) throws UnsupportedEncodingException
	{
		String answer = "{"+this.deviceName+":\""+new String(buffer,0,len,"UTF8").trim()+"\"}";
	}
	
	public boolean writeToPort(String command)
	{
		 byte[] b = HexTools.fromHexString(command);
		 try {
			portOutputStream.write(b);
			return true;
		} catch (IOException e) {
			//System.exit(-1);
			return false;
		}  
	}


    public boolean readToPort()
    {
        flag=false;
        MyRunnable r = new MyRunnable();
        Thread t = new Thread(r);
        t.start();
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (flag)
        {
            t.stop();
            return true;
        }
        else
        {
            Service.WriteLog("Не удалось ничего прочитать из порта");
            t.stop();
            return false;
        }
    }

	
	public void closePort()
	{
		commPort.close();
	}
	
	public String sign(String str)
	{
		return HexTools.tool(str + Service.rate + this.os);
	}
}
