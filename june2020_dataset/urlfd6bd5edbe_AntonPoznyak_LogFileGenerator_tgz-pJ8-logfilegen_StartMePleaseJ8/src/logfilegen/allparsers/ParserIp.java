package logfilegen.allparsers;

import logfilegen.allmodels.record.Ip;

public class ParserIp {
	public Ip parser(String ipStr){
        
        String[] split = ipStr.split("\\.");
        short[] ip = new short[split.length];
        
        for(int i=0;i<split.length;i++){
                ip[i] = Short.parseShort(split[i]);
        }
        
        return new Ip(ip);
        
}
}
