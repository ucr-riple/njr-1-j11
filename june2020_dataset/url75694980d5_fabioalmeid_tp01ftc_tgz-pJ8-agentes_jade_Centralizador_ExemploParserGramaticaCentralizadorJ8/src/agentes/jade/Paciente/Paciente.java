package agentes.jade.Paciente;

public class Paciente {
	private final static int MIN_TEMP = 35;
	private final static int MAX_TEMP = 40;
	
	private final static int MIN_HEMO = 9;
	private final static int MAX_HEMO = 23;
	
	private final static int MIN_BILI = 0;
	private final static int MAX_BILI = 14;
	
	private static Integer temperatura = 39;
	private static Boolean remedioTemp = false;
	
	private static Integer hemoglobina = 8;
	private static Boolean remedioHemoglobina = false;
	
	private static Integer bilirrubina = 9;
	private static Boolean remedioBilirrubuna = false;
	
	private static Boolean remedioPressao = false;

	public static void setPressao(Integer diast, Integer sist) {
		PressaoArterial.setSist(sist);
		PressaoArterial.setDiast(diast);
	}
	
	public static Integer getPressaoDiast() {
		return PressaoArterial.getDiast();
	}
	
	public static Integer getPressaoSist() {
		return PressaoArterial.getSist();
	}
	
	public static Integer getHemoglobina() {
		return hemoglobina;
	}
	
	public static Integer getHemoglobinaBoa() {
		// 13,5 - 18 bom
		// abaixo 12.5 anemia, acima de 18 doente
		int Min = 14, Max = 18;
		hemoglobina = Min + (int) (Math.random() * ((Max - Min) + 1));
		return hemoglobina;
	}

	public static void setHemoglobina(Integer _hemoglobina) {
		if ( (_hemoglobina >= MIN_HEMO) && (_hemoglobina <= MAX_HEMO)) 
			hemoglobina = _hemoglobina;
		else if (hemoglobina < MIN_HEMO)
			hemoglobina = MIN_HEMO;
		else if (hemoglobina > MAX_HEMO)
			hemoglobina = MAX_HEMO;
	}
	
	public static Integer getBilirrubina() {
		return bilirrubina;
	}

	public static void setBilirrubina(Integer _bilirrubina) {
		if ( (_bilirrubina >= MIN_BILI) && (_bilirrubina <= MAX_BILI)) 
			bilirrubina = _bilirrubina;
		else if (bilirrubina < MIN_BILI)
			bilirrubina = MIN_BILI;
		else if (bilirrubina > MAX_BILI)
			bilirrubina = MAX_BILI;
	}
	
	public static Integer getTemperatura() {
		return temperatura;
	}
	
	public static void setTemperatura(Integer _temperatura) {
		if ( (_temperatura >= MIN_TEMP) && (_temperatura <= MAX_TEMP)) 
			temperatura = _temperatura;
		else if (temperatura < MIN_TEMP)
			temperatura = MIN_TEMP;
		else if (temperatura > MAX_TEMP)
			temperatura = MAX_TEMP;
	}

	public static Boolean getRemedioTemp() {
		return remedioTemp;
	}

	public static void setRemedioTemp(Boolean remedioTemp) {
		Paciente.remedioTemp = remedioTemp;
	}

	public static Boolean getRemedioHemoglobina() {
		return remedioHemoglobina;
	}

	public static void setRemedioHemoglobina(Boolean remedioHemoglobina) {
		int Min = 9, Max = 23;
		Paciente.remedioHemoglobina = remedioHemoglobina;
	}

	public static Boolean getRemedioBilirrubuna() {
		return remedioBilirrubuna;
	}

	public static void setRemedioBilirrubuna(Boolean remedioBilirrubuna) {
		
		Paciente.remedioBilirrubuna = remedioBilirrubuna;
	}

	public static Boolean getRemedioPressao() {
		return remedioPressao;
	}

	public static void setRemedioPressao(Boolean remedioPressao) {
		Paciente.remedioPressao = remedioPressao;
	}
}