package agentes.jade.Paciente;

public class PressaoArterial {
	private static final int MIN_PRESSAO_DIAS = 50;
	private static final int MIN_PRESSAO_SIST = 90;
	
	private static final int MAX_PRESSAO_DIAS = 130;
	private static final int MAX_PRESSAO_SIST = 200;
	
	
	private static int Diast = 60;
	private static int Sist = 100;
	
	public static int getDiast() {
		return Diast;
	}
	public static int getRandomDiast() {
		int Min = MIN_PRESSAO_DIAS, Max = MAX_PRESSAO_DIAS;
		Diast = Min + (int)(Math.random() * ((Max - Min) + 1));
		return Diast;
	}
	public static int getDiastBoa() {
		int Min = 60, Max = 90;
		Diast = Min + (int)(Math.random() * ((Max - Min) + 1));
		return Diast;
	}
	public static void setDiast(int diast) {
		Diast = diast;
	}
	public static int getSist() {
		return Sist;
	}
	public static int getRandomSist() {
		int Min = MIN_PRESSAO_SIST, Max = MAX_PRESSAO_SIST;
		Sist = Min + (int)(Math.random() * ((Max - Min) + 1));
		return Sist;
	}
	public static int getSistBoa() {
		int Min = 100, Max = 140;
		Sist = Min + (int)(Math.random() * ((Max - Min) + 1));
		return Sist;
	}
	public static void setSist(int sist) {
		Sist = sist;
	}
	
	public static String getPressao() {
		String pressaoSTR = String.valueOf(Sist) + " : " + String.valueOf(Diast);
		return pressaoSTR;
	}
	
	public static void setPressao(int sist, int diast) {
		Sist = sist;
		Diast = diast;
	}
	
	public static void setPressaoBoa() {
		Sist = getSistBoa();
		Diast = getDiastBoa();
	}
	
	public static void setPressaoRandom() {
		Sist = getRandomSist();
		Diast = getRandomDiast();
	}
	
	

}
