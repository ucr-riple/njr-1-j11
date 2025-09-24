package pl.cc;

import java.util.Date;

import pl.cc.core.cmd.Command;
import pl.cc.core.cmd.ErrSameAgentConnection;
import pl.cc.core.cmd.InfoType;
import pl.cc.core.cmd.InfoVersion;
import pl.cc.core.cmd.OkWelcome;

/**
 * Zestaw zdarzeń, które są generowane przez CCProxy
 * @since Oct 26, 2008
 */
public interface ProxyEventListener {

	void onProblemWithPing();
	
	/**
	 * Próba nawiązania połączenia (TCP) do CCProxy
	 * @param successfully jeśli udało się nawiązać połączenie
	 */
	void onTryConnect(boolean successfully, final String host);
	
	/**
	 * Disconnect TCP
	 */
	void onDisconnect();
	
	/**
	 * Otrzymanie informacji o wystąpieniu zdarzenia/komenda z CCProxy
	 * @param command Instancja komendy
	 * @return informacjia dla proxy (połączenia) czy ma poddać komende dalszemu przetwarzaniu - 
	 * 				zazwycznaj false, konieczne dla kompatybilności z starymi komponentami
	 */
	void onEvent(Command command);
	
	/**
	 * Odpowiedź na próbę autoryzacji
	 * @param authorized true jeśli autoryzowano
	 */
	void onAuthResponse(boolean authorized);

	/**
	 * Informacja o minimalnej wersji klienta
	 * @param infoVersion informacja o minimalnej wersji
	 */
	void onInfoVersion(InfoVersion infoVersion);
	
	/**
	 * Zostaliśmy autoryzowani - dostajemy informacje na swój temat 
	 * @param selfInfo  - nazwa, exten, typ :supervisor/agent
	 */
	void onAuthorized(OkWelcome selfInfo);
	
	/**
	 * Odebrano ponga
	 * @param now - czas gdy odebrano ponga
	 */
	void onPong(Date now);

	/**
	 * Odebraliśmy liinię z zachętą
	 */
	void onWelcome();
	
	/**
	 * Zła wersja aplikacji
	 * @param applicationVersion wersja aplikacji
	 * @param infoVersion informacja nt wymaganej wersji aplikacji
	 */
	void onInvalidVersion(String applicationVersion, InfoVersion infoVersion);
	/**
	 * Będziemy próbować automatycznie wznowić połączenie
	 * @param sleepBeforeReconnect - po tylu sekundach
	 */
	void onStartReconnectProcedure(int sleepBeforeReconnect);
	
	/**
	 * Ktoś zalogował się "tym agentem" z innej aplikacji 
	 * @param connectionReplaced - szczegółowe dane, np: ip
	 */
	void onConnectionReplaced(ErrSameAgentConnection connectionReplaced);

	void onInfoType(InfoType cmd);


}
