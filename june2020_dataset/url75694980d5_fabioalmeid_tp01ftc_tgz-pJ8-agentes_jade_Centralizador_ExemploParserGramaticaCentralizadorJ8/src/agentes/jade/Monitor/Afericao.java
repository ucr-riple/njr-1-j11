package agentes.jade.Monitor;

import gramatica.Monitor.Absyn.EDados;
import gramatica.Monitor.Absyn.EDados1;
import gramatica.Monitor.Absyn.EDados2;
import gramatica.Monitor.Absyn.EDados3;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Afericao {
	private Object dado;
	/*Possiveis valores
	 * EDados. Dados ::= "Temperatura de" Quantidade "C" ;
	 * EDados1. Dados ::= "Bilirrubina" Quantidade "g/dL" ;
	 * EDados2. Dados ::= "Hemoglobina" Quantidade "mg/dL";
	 * EDados3. Dados ::= "Pressao Arterial" Quantidade ":" Quantidade "mmHg";
	 * */
	private Integer quantidade1 = null;
	private Integer quantidade2 = null;
	
	private Integer hora1 = null;
	private Integer hora2 = null;
	
	public Object getDado() {
		return dado;
	}
	public void setDado(Object dado) {
		this.dado = dado;
	}
	public Integer getQuantidade1() {
		return quantidade1;
	}
	public void setQuantidade1(Integer quantidade) {
		this.quantidade1 = quantidade;
	}
	public Integer getQuantidade2() {
		return quantidade2;
	}
	public void setQuantidade2(Integer quantidade2) {
		this.quantidade2 = quantidade2;
	}
	public Integer getHora2() {
		return hora2;
	}
	public void setHora2(Integer hora2) {
		this.hora2 = hora2;
	}
	public Integer getHora1() {
		return hora1;
	}
	public void setHora1(Integer hora1) {
		this.hora1 = hora1;
	}
	
	public void setCurrentHora() {
		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		hora1 = calendar.get(Calendar.HOUR_OF_DAY); // formato 24h
		hora2  = calendar.get(Calendar.MINUTE);
	}
	
	public String getHora() {
		return ("as " + String.format("%02d", this.hora1) + " h: " + String.format("%02d",this.hora2) + " m");
	}
	
	public String prettyPrinter() {
		StringBuilder pretty = new StringBuilder();
	    //builder.append("estou ");  
		if (getDado() instanceof EDados) {
			pretty.append("Temperatura de ");
			pretty.append(String.valueOf(getQuantidade1()));
			pretty.append(" C ");
		} else if (getDado() instanceof EDados1) {
			pretty.append("Bilirrubina ");
			pretty.append(String.valueOf(getQuantidade1()));
			pretty.append(" g/dL ");
		} else if (getDado() instanceof EDados2) {
			pretty.append("Hemoglobina ");
			pretty.append(String.valueOf(getQuantidade1()));
			pretty.append(" mg/dL ");
		} else if (getDado() instanceof EDados3) {
			pretty.append("Pressao Arterial ");
			pretty.append(String.valueOf(getQuantidade1()));
			pretty.append(" : ");
			pretty.append(String.valueOf(getQuantidade2()));
			pretty.append(" mmHg ");			
		}
		pretty.append(util.Hora.getPrettyHour());
		return pretty.toString();
	}
	
	

}
