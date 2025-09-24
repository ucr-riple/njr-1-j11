package pl.cc.core.cmd;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.cc.core.ExtraFieldData;
import pl.cc.core.GroupData;
import pl.cc.real.RealAgent;
import pl.cc.real.RealCall;
import pl.cc.real.RealQueue;

/**
 * Zdarzenie CallTag
 *
 * @since 2012-08-17
 */
public class EventCallTag extends Command {
			RealQueue queue;
			RealAgent agent;
			RealCall call;
			String callID;
			ArrayList<GroupData> topiclist;
			ArrayList<ExtraFieldData> fieldlist=null;


			// Event [CallTag], Callid [1345477640.83], Topiclist [{Sprzedaż{Autokar|Wycieczka|Samolot}}{Zmiana{Danych|Ubezpieczenia}}{Zapytanie{Oferta|Biuro}}]
			private EventCallTag(String orginalLine,ArrayList<GroupData> topiclist, ArrayList<ExtraFieldData> fieldlist, String callID ) {
				super(orginalLine);	
				this.topiclist = topiclist;
				this.fieldlist = fieldlist;
				this.callID = callID;
			}

			public static Command factoryInt(String s){
				String [] names = {"event","callid","topiclist", "extrafields"};
	
				String [] v = getValues(s, names,3); 
				if (v==null) return null;
				if (!v[0].toLowerCase().equals("calltag")) return null;
				ArrayList<GroupData> topiclist=new ArrayList<GroupData>();
				ArrayList<ExtraFieldData> fieldlist=new ArrayList<ExtraFieldData>();
				
				synchronized (p) {
					Matcher m = Pattern.compile("\\{([^\\{]*)\\{([^\\}]*)\\}\\}", Pattern.CASE_INSENSITIVE).matcher(v[2]);
					while ((m.find())&&m.groupCount()==2){
						GroupData gd=new GroupData(m.group(1), new ArrayList<String>(Arrays.asList(m.group(2).split("\\|"))));
						topiclist.add(gd);
					}
					}
				
				if (v[3]!=null) {
				synchronized (p) {
					Matcher m = Pattern.compile("\\{([^\\}\\|]*)\\|([^\\}\\|]*)\\}", Pattern.CASE_INSENSITIVE).matcher(v[3]);
					while ((m.find())&&m.groupCount()==2){
						ExtraFieldData fd=new ExtraFieldData(m.group(1),m.group(2));
						fieldlist.add(fd);
					}
					}
				}
				return new EventCallTag(
						s,
						topiclist, fieldlist, v[1]
						);
			}

			public static void main(String[] args) {
				EventCallTag ec = (EventCallTag)Command.factory("Event [CallTag], Callid [1345477640.83], Topiclist [{Sprzedaż{Autokar|Wycieczka|Samolot}}{Zmiana{Danych|Ubezpieczenia}}{Zapytanie{Oferta|Biuro}}], Extrafields [{NIP|}]", "1");
				//EventCallTag ec = (EventCallTag)Command.factory("Event [CallTag], Callid [1345477640.83], Topiclist [{Sprzedaż{Autokar|Wycieczka|Samolot}}{Zmiana{Danych|Ubezpieczenia}}{Zapytanie{Oferta|Biuro}}] ", "1");
				//EventCallTag ec = (EventCallTag)Command.factory("Event [CallTag], Callid [1346925786.111], Topiclist [{Destynacja{Powiadomienie o sytuacji problemowej|Poźniejsze przybycie Klienta|Przygotowanie biletów}}{Godziny przelotu{Potwierdzenie godzin przelotu agent|Potwierdzenie godzin przelotu klient}}{Informacja{Ilośc miejsc w samolocie|Opcja na przelot lub hotel|Wycieczka objazdowa ilość miejsc|Zapytanie produktowe}}{Manual{Manual - anulacja|Manual - potwierdzenie|Manual - przedłużenie|Manual - założenie}}{System rezerwacyjny{Błędne koszty anulacji|Problem z ceną|Problem z obsługa systemu|Problem z systemem|sadjklsahsakjkjdkjsa skadj kajdk jakdj kajd kajdkjdlsajd lkajd lakdj kladj klajdf kasfh}}{Założenie rezerwacji{Dojazd własny|Kombinacja|Pakiet|Przelot|Tui cars|Udostępnienie oferty OW lub hotel}}{Zmiany na rezerwacji{Dokumenty odblokowanie upoważnienia|Dokumenty status wysyłki|Dokumenty zmiana adresu wysyłki|Dopisanie prośby|Rezerwacja miesjca w samolocie|Świadczenia dodatkowe extras|Ubezpieczenie dodanie|Ubezpieczenie usunięcie}}], Extrafields [{Nazwa,}{NIP,99999}]","1");
				for (GroupData gd:ec.getTopicList()){
					System.out.println(gd);
					System.out.println(gd.getTopicList());
				}
				System.out.println(ec.getFieldList().get(0).getName());
				System.out.println(ec.getFieldList().get(0).getMask());
				//System.out.println(ec.getFieldList().get(1));
			}
			
			@Override
			public int getType() {
				return CMD_EVENT_CALL_TAG;
			}

			public RealQueue getQueue() {
				return queue;
			}

			public RealAgent getAgent() {
				return agent;
			}

			public RealCall getCall() {
				return call;
			}

			public String getCallID() {
				return callID;
			}
	
			public ArrayList<GroupData> getTopicList() {
				return topiclist;
			}

			public ArrayList<ExtraFieldData> getFieldList() {
				return fieldlist;
			}

			

		}