/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ascensor;
import java.util.Random;
import ascensor.GNA;
import ascensor.Ascensor;
import java.util.Collections;
import java.util.ArrayList;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.distribution.WeibullDistribution;
import org.apache.commons.math3.stat.descriptive.moment.Variance;
/**
 * Javadoc de las distribuciones de la libreria Apache Math
 * https://commons.apache.org/proper/commons-math/apidocs/org/apache/commons/math3/distribution/package-summary.html
 * 
*/

/**
 *
 * @author eduardogasser
 */
public class Main {

    /* VARIABLES CONSTANTES */
    private static final int alturaPiso = 3; // altura de un piso en metros
    private static final int velAscensor = 1; // velocidad del ascensor metros/segundos
    private static final int Tviaje = alturaPiso/velAscensor; // tempo de viaje
    private static final int Tabrir = 1;  // tiempo abre puertas ascensor
    private static final int Tcerrar =  3; // tiempo cerrar puertas ascensor
    private static final int Tin_out =  2; // tiempo de espera en cada planta para que salga y entre gente  
    private static final int MAX_PASAJEROS = 10; // ocupación total del ascensor
    private static final int MAX_PISOS = 5; // número de pisos edificio
    private static final int INFINITO = Integer.MAX_VALUE;
    private static int K = 100; // número de clientes retardados
    private static final int TOTAL_TRAZAS = 6;
    private static final int SEMILLA = 15;
    
    /* VARIABLES DEL ASCENSOR */
    private ArrayList<Cola> cola_subida = new ArrayList<Cola>(); // cola de pasajeros que quieren subir
    private ArrayList<Cola> cola_bajada = new ArrayList<Cola>();// cola de pasajeros que quieren bajar 
    private ArrayList<Pasajero> pasajeros_reflexion = new ArrayList<Pasajero>();//lista de personas en reflexion
    private Ascensor ascensor = new Ascensor();    
    private Event_list event_list = new Event_list(); //eventos
    private int seleccion_piso[] = new int[6];
    
    /*DISTRIBUCIONES*/
    private GNA g = new GNA(); 
    private WeibullDistribution weibull;
    
    // VARIABLES DE ESTADO
    private int piso_actual; // piso donde se encuentra el ascensor
    private int piso_destino; // piso destino del ascensor
    private int internas[] = new int[MAX_PISOS]; // llamadas desde la botonera interna
    private int subidas[] = new int[MAX_PISOS]; // llamadas subiendo 
    private int bajadas[] = new int[MAX_PISOS]; // llamadas bajando
    private int clock;   
    
    // VARIABLES ESTADISTICAS
    private int[] total_delayed = new int[TOTAL_TRAZAS];
    private int[] number_delayed = new int[TOTAL_TRAZAS];
    private int[] mean_waiting_time = new int[TOTAL_TRAZAS];
 
    private static final int cota_transitorio = 21;
    private double lambda = 0.016; // 1/60
    private boolean aceptable;
    private int traza;
    public  Main(){
        seleccion_piso[0] = 1;
        seleccion_piso[1] = 1;
        seleccion_piso[2] = 2;
        seleccion_piso[3] = 2;
        seleccion_piso[4] = 3;
        seleccion_piso[5] = 3;

    }
    /* rutina de inicializacion */
    public void inicializar_traza()
    {
        clock = 0;
        ascensor.clear();
        event_list.setA(GA());
        event_list.setL(100000);
        event_list.setS(10000000);
        event_list.setR(100000000);
        pasajeros_reflexion.clear();
        piso_actual=0;
        piso_destino=0;
        number_delayed[traza]=0;
        total_delayed[traza]=0;
       
        mean_waiting_time[traza]=0;
        for (int i = 0; i<MAX_PISOS; i++){
            cola_subida.get(i).clear();
            cola_bajada.get(i).clear();
            internas[i] = 0;
            subidas[i]=0;
            bajadas[i]=0;
        }
    }
    public void inicializar_colas()
     { 
         for (int i = 0; i<MAX_PISOS; i++){
            cola_subida.add(new Cola("ASC"));
            cola_bajada.add(new Cola("DESC"));
        }
         
     }
     /* determinar piso de manera equiprobable desde la planta cero*/
     public int determinar_piso()
     {      
        int r = randomInterval(1,MAX_PISOS-1);
        while (0 == r){
         r = randomInterval(1,MAX_PISOS-1);  
        }
        
        return r;
     }
     
    /* distribucion Poisson con lambda = 1/lambda para la llegada de pasajeros al edificio */
    private int GA()
    {
        double d = randomIntervalDouble(0,1);
        int r = (int) (-Math.log(1-d) / lambda);
        return  r;
    }
    
    /* distribucion Weibull para el tiempo de reflexión de los pasajeros */
    private int GR()
    {
       weibull = new WeibullDistribution(2, 22);
       int r = (int) weibull.sample() * 60;
       return r;
    }
    private int randomInterval(int a, int b){
        
        // por transformada inversa
        double r = Math.ceil((b-a+1)*g.rand2(SEMILLA-4))+ a - 1;
        return (int)r;
    }
    private double randomIntervalDouble(int a, int b){
       
        // por transformada inversa
        double r =  ((b-a)*g.rand2(SEMILLA-4)+a);
        return r;
    }
    private int determinarPisoProbabilidad(int p){
        int resultado;
        
        double r = g.rand2(SEMILLA);
    
        // si 40% piso = 0
        if ( r > 0.5 ){
            resultado = 0;
        }else{
            int indice = (int) (r*10);
            if (seleccion_piso[indice] == p){
                resultado = 4;
            }else{
                resultado = seleccion_piso[indice];
            }
        }
       return resultado;
  }
    
 public void mostrar_botonera(int b[], String s)
  {
      ////System.out.println("  ----BOTONERA " + s + "--");
      for (int i = 0; i<MAX_PISOS; i++)
      {
          ////System.out.print("    " + b[i] +"|");
      }
     ////System.out.println("");
  }
 
  public Pasajero getPasajeroFinReflexion()
  {
      int i = 0;
      boolean salir = false;
      
      while (i < pasajeros_reflexion.size() && !salir)
      {
          if (pasajeros_reflexion.get(i).getTiempoReflexion() == event_list.getR())
          {
              salir = true;
          }
          i++;
      }
      return pasajeros_reflexion.get(i-1);
  }
  
  public void removePasajeroFinReflexion(){
      int i = 0;
      boolean salir = false;
      
      while (i < pasajeros_reflexion.size() && !salir)
      {
          if (pasajeros_reflexion.get(i).getTiempoReflexion() == event_list.getR())
          {
              pasajeros_reflexion.remove(i);
              salir = true;
          }
          i++;
      }
 
  }
  /* rutina de fin tiempo reflexión pasajero en la planta*/
  public void fin_reflexion()
     {    
    
         /* obtengo piso actual del pasajero */
         int piso_actual_pasajero = getPasajeroFinReflexion().getPisoActual();
         /* determino piso al que irá el pasajero */
         int piso_destino_pasajero = determinarPisoProbabilidad(piso_actual_pasajero);
         /* actualizamos piso destino en pasajero */
         getPasajeroFinReflexion().setPisoDestinoPasajero(piso_destino_pasajero);
         
         /* Direccion del pasajero */
         String direc;
         if (piso_actual_pasajero < piso_destino_pasajero){ direc = "ASC"; }else{ direc = "DESC";}
       
         /* si ascensor está en el piso actual, está en reposo y tiene misma direccion o no hay nadie dentro del ascensor */
         if ((piso_actual == piso_actual_pasajero) && (ascensor.getNumPasajeros() < MAX_PASAJEROS) && (!ascensor.getViajando()) &&  (ascensor.getDireccion().equals(direc))){
                               
                /* pasajero sube al ascensor */
                ascensor.getPasajeros().add(getPasajeroFinReflexion());
                /* sacamos al pasajero de la cola de reflexion */
                removePasajeroFinReflexion();
                /* se genera tiempo salida_ascensor */
                event_list.setS(clock + Tin_out);  
                internas[piso_destino_pasajero] = 1; // actualizamos botonera interna
          }else{
                if (direc.equals("ASC")){
                    cola_subida.get(piso_actual_pasajero).add(getPasajeroFinReflexion());
                    removePasajeroFinReflexion();
                    subidas[piso_actual_pasajero] = 1; // actualizamos botonera subida en planta baja
                }else{
                    cola_bajada.get(piso_actual_pasajero).add(getPasajeroFinReflexion());
                    removePasajeroFinReflexion();
                    bajadas[piso_actual_pasajero] = 1; // actualizamos botonera subida en planta baja
                 }
         }
         mostrar_pasajeros_actuales(); 
         event_list.setR(INFINITO);
     }
     /* rutina de llegada pasajero al edificio */
     public void llegada_pasajero()
     {    
        
         /* determino piso al que irá el pasajero */
         int piso_destino_pasajero = determinar_piso();
         /* si ascensor está en el piso actual, está en reposo y hay menos de 10 personas en el */
         if (piso_actual == 0 && !ascensor.getViajando() && ascensor.getNumPasajeros() < MAX_PASAJEROS){   
            /* pasajero sube al ascensor */
            ascensor.setPasajeroSube(new Pasajero("ASC", piso_destino_pasajero, clock));
            number_delayed[traza]++;
            event_list.setS(clock + GA());  
            internas[piso_destino_pasajero] = 1;  // actualizamos botonera interna 
         }else{ // el ascensor está en otro piso o se acaba de ir 
            /* ponemos al pasajero en la cola : number_in_queue++ */
            cola_subida.get(0).add( new Pasajero("ASC", piso_destino_pasajero,clock) );
            subidas[0] = 1; // actualizamos botonera subida en planta baja  
         }

         /* se genera el tiempo de llegada del pasajero al edificio */
         event_list.setA(clock + GA());    
     }
     /* rutina de llegada ascensor a la planta */
     public void llegada_ascensor()
     {
//System.out.println("LLEGADA_ASCENSOR: " + clock);
         /* actualizamos el piso y el estado del ascensor */
         actualizarPisoAscensor();   
         ascensor.setViajando(false);
         /* actualizamos botonera interna */
         internas[piso_actual] = 0;
         mostrar_botonera(internas, "INTERNA");
         mostrar_botonera(subidas, "SUBIDA");
         mostrar_botonera(bajadas, "BAJADAS"); 
         /* bajamos a las personas del ascensor */
         if (existe_pasajero_irse()) 
             bajar_pasajeros_ascensor();  
        /* se genera el tiempo de salida del ascensor */
        event_list.setS(clock + Tin_out);  
        mostrar_pasajeros_actuales();
        actualizarDireccion();
        event_list.setL(INFINITO);

     }
     
     public void bajar_pasajeros_ascensor()
     {
         int i = 0;
         while (i < ascensor.getPasajeros().size())
         {     
            if (ascensor.getPasajeros().get(i).getPisoDestino() == piso_actual)
             {
                 if (piso_actual == 0){
                    ascensor.getPasajeros().remove(i);
                    i--;
                 }else{           
                    /* se genera el tiempo de reflexión del pasajero en la planta */
                    event_list.setR(clock + GR());
                    /* indicamos cual es ese tiempo de reflexion al pasajero */
                    ascensor.getPasajeros().get(i).setTiempoReflexion(event_list.getR());
                    ascensor.getPasajeros().get(i).setPisoActual(piso_actual);
                    /* ponemos al pasajero en una lista de reflexion */
                    pasajeros_reflexion.add(ascensor.getPasajeros().get(i));
                
                    /* sacamos el pasajero del ascensor */
                    ascensor.getPasajeros().remove(i);
                    /* al eliminar un elemento de un ArrayList se desplazan todos hacia atras */
                    i--;
                  }                   
             }
             i++;
         }   
     }
     public void mostrar_pasajeros_actuales(){
        ////System.out.println(" ******PASAJEROS EN ASCENSOR************");
        for (int m = 0; m < ascensor.getPasajeros().size();m++){
            ////System.out.println(ascensor.getPasajeros().get(m));
        } 
     }
     public void proxima_parada_interior()
     {
         /* miramos los destinos de todos los pasajeros y nos quedamos con el siguiente */
         int i = 0;
         int piso_aux;
         if (ascensor.getPasajeros().size() > 0 ){
            piso_aux = ascensor.getPasajeros().get(0).getPisoDestino();
            while (i < ascensor.getPasajeros().size())
            {
               if (ascensor.getPasajeros().get(i).getPisoDestino() < piso_aux ){
                  piso_aux = ascensor.getPasajeros().get(i).getPisoDestino();
               }
            }
             /* actualizamos la proxima parada */
            ascensor.setProximaParada(piso_aux);
         }  
     }
     /* si hay algun pasajeros que quiera bajarse en este piso */
     public boolean existe_pasajero_irse()
     {
         int i = 0;
         boolean salir = false;
     
       while (i < ascensor.getPasajeros().size() && !salir)
         {   
            if (ascensor.getPasajeros().get(i).getPisoDestino() == piso_actual)
             {
                 salir = true;
             }
             i++;
         }
         return salir;
     }
     public  ArrayList<Cola>  getColaConAlguien()
     {
         if (ascensor.getDireccion().equals("ASC"))
         {
            return cola_subida;
         }else{ 
            return cola_bajada;
         }
     }
     public  ArrayList<Cola>  getColaActual()
     {
         if (ascensor.getDireccion().equals("ASC"))
         {
            return cola_subida;
         }else{
            return cola_bajada;
         }
     }
     
     public boolean transitorio()
     {
      return (cota_transitorio <= number_delayed[traza]);
      //return true;
        
     }
     
     public void aceptar_pasajeros ()
     {
         /* cola_subida.get(piso_actual).size() mientras haya gente que subir y sean menos de 10 personas en el ascensor */
         while (ascensor.getNumPasajeros() < MAX_PASAJEROS && getColaActual().get(piso_actual).size()  > 0 )
         {
           /* si recogemos a pasajeros de la planta baja, calculamos el tiempo de espera de ese pasajero && !transitorio */
            if (piso_actual==0 ){
                number_delayed[traza]++;
                
                if (transitorio()){
                total_delayed[traza] += clock - getColaActual().get(piso_actual).frente().getTiempoEntrada();    
                }
            }
            internas[getColaActual().get(piso_actual).frente().getPisoDestino()] = 1;
            ascensor.getPasajeros().add(getColaActual().get(piso_actual).frente());    
            getColaActual().get(piso_actual).desencolar();  
            
         }
         /* si al final no ha quedado nadie en la cola, actualizamos la botonera */
         if (getColaActual().get(piso_actual).isEmpty()) {         
             if (getColaActual().get(piso_actual).getDireccion() == "ASC"){
                 subidas[piso_actual]=0;           
             }else{
                 bajadas[piso_actual]=0;           
             }
         } 
     }

    /* calcular próxima para del ascensor. 
    *  si un pasajero en otra planta va en la misma dirección que el ascensor 
    *  el ascensor realiza una parada en esa planta para recogerlo
    * */
     public void actualizarDireccion(int p)
     {
         if (piso_actual < p){
             ascensor.setDireccion("ASC");
         }else{
             ascensor.setDireccion("DESC");
         }
         if (piso_actual == 0) ascensor.setDireccion("ASC");
         if (piso_actual == MAX_PISOS-1) ascensor.setDireccion("DESC");
     }
     public void actualizarDireccion()
     {
         if (piso_actual == 0) ascensor.setDireccion("ASC");
         if (piso_actual == MAX_PISOS-1) ascensor.setDireccion("DESC");
     }
     
     public void actualizarDireccion(int actual, int destino){
         if (actual < destino)
         {
             ascensor.setDireccion("ASC");
         }else{
             ascensor.setDireccion("DESC");
         }
     }
     public void salida_ascensor()
     {
//System.out.println("SALIDA_ASCENSOR: " + clock);
        if (ascensor.getNumPasajeros() < MAX_PASAJEROS) aceptar_pasajeros();
        piso_destino = proxima_parada();
        if (piso_destino == piso_actual ){
            event_list.setS((clock + 3));
        }else{
            ascensor.setViajando(true);  
            event_list.setL(clock + (Tviaje*(Math.abs(piso_destino - piso_actual))) + Tcerrar + Tabrir);
            event_list.setS(INFINITO);
           //System.out.println("salida ascensor: llegada ascensor: " + event_list.getL() + " piso_destino: " + piso_destino + " piso_Actual: " + piso_actual+ " "+ Tcerrar +" "+ Tabrir);
   
        }       
    }
     
     public int sig_piso(int p, int b[],boolean t)
     {
         int i = p;
         /* si hay llamadas internas en el mismo piso, no debe mirar si hay llamadas externas en ese mismo piso
          * por que puede que haya alguna llamada porque ya no entran más pasajeros
          */
         if (t) i = p+1;
         boolean salir = false;
         while (i < MAX_PISOS && !salir){
             if (b[i] == 1) salir = true;
             i++;
         }
         if (salir){
             return --i;
         }else{
             return -1;//no hay llamadas en los pisos siguientes
         }
     }
     public boolean hay_sig_piso(int p, int b[])
     {
         int i = p;
         boolean salir = false;
         
         while (i < MAX_PISOS && !salir){
             if (b[i] == 1) salir = true;
             i++;
         }
         return salir;
     }
     public boolean hay_ant_piso(int p, int b[])
     {
         int i = p;
         boolean salir = false;
         while (i >= 0 && !salir){
              
             if (b[i] == 1) salir = true;
             --i;
         }
         return salir;
     }
     public int ant_piso(int p, int b[], boolean t)
     {
         int i = p;
         if (t) i = p-1;
         
         boolean salir = false;
         while (i >= 0 && !salir){
              
             if (b[i] == 1) salir = true;
             --i;
         }
         if (salir){
             return ++i;
         }else{
             return -1;
         }
     }

     public boolean hay_llamadas_internas(){
         int i = 0;
         boolean salir = false;
         while ( i < MAX_PISOS && !salir)
         { 
             if (internas[i] == 1){
                 salir = true;
             }
             i++;
         }
         if (salir){
             return true;
         }else{
             return false;
         }
     }
     /* obtenemos la siguiente parada dependiendo si hay peticiones en otras plantas */
     public int proxima_parada()
     {
         
         int i = piso_actual;
         int a;
         int b;
         int next=0;
         
         if (!hay_llamadas_internas()){
            if (ascensor.getDireccion().equals("ASC") && (hay_sig_piso(piso_actual,subidas) || hay_sig_piso(piso_actual,bajadas))){
                if (hay_sig_piso(piso_actual,subidas)){
                    next = sig_piso(piso_actual,subidas,false);
                }else{
                    next = sig_piso(piso_actual,bajadas,false);
                    ascensor.setDireccion("DESC");
                }
            }else if (ascensor.getDireccion().equals("DESC") && (hay_ant_piso(piso_actual,subidas) || hay_ant_piso(piso_actual,bajadas))){
                if (hay_ant_piso(piso_actual,bajadas)){
                    next = ant_piso(piso_actual,bajadas,false);
                }else{
                    next = ant_piso(piso_actual,subidas,false);
                     ascensor.setDireccion("ASC");
                } 
            }else if(ascensor.getDireccion().equals("DESC") && (hay_sig_piso(piso_actual,subidas) || hay_sig_piso(piso_actual,bajadas))){
                if (hay_sig_piso(piso_actual,subidas)){
                    next = sig_piso(piso_actual,subidas,false);
                    ascensor.setDireccion("ASC");
                }else{
                    next = sig_piso(piso_actual,bajadas,false);  
                }
            }else if(ascensor.getDireccion().equals("ASC") && (hay_ant_piso(piso_actual,subidas) || hay_ant_piso(piso_actual,bajadas))){
                if (hay_ant_piso(piso_actual,bajadas)){
                    next = ant_piso(piso_actual,bajadas,false);
                    ascensor.setDireccion("DESC");
                }else{
                    next = ant_piso(piso_actual,subidas,false);   
                } 
            }else{
                next = piso_actual;
            }
         
    
         }else{
            /* si hay llamadas internas, miramos si entre el viaje hasta el destino del pasajero del ascensor
             * hay otro pasajero con la misma dirección, en algun piso por el que debemos pasar 
             */
            if (ascensor.getDireccion().equals("ASC"))
            {
                a = sig_piso(piso_actual,internas,true);
                b = sig_piso(piso_actual,subidas,true);
                if (a == -1) a = b;
                if (b == -1) b = a;
                next = Math.min(a,b);
            }
            if (ascensor.getDireccion().equals("DESC"))
            {
                a = ant_piso(piso_actual,internas,true);
                b = ant_piso(piso_actual,bajadas,true);
                if (a == -1) a = b;
                if (b == -1) b = a;
                next = Math.max(a,b);
            }
           actualizarDireccion(next);
         }
         if (next == -1) next = piso_actual;
         return next;
          
     }
     
     public void actualizarPisoAscensor()
     {
         piso_actual = piso_destino;
     }
     
     public void temporizacion()         
     {
         /* min (event_list()) */    
         if (event_list.getA() <= event_list.getL() && event_list.getA() <= event_list.getS() && event_list.getA() <= event_list.getR()){         
             clock = event_list.getA();           
         }else if (event_list.getL() <= event_list.getA() && event_list.getL() <= event_list.getS() && event_list.getL() <= event_list.getR()){           
             clock = event_list.getL();        
         }else if (event_list.getS() <= event_list.getA() && event_list.getS() <= event_list.getL() && event_list.getS() <= event_list.getR()){         
             clock = event_list.getS();   
         }else{   
             clock = event_list.getR();    
         }  
     }
    
     public double mean_of(int[] a){
         int total=0;
         for (int i =0; i< a.length; i++)
         {
             total += a[i];
         }
         return total/a.length;
     }
     public double variance_of(int[] valores, int size, double mean)
     {
        double acum = 0;
        int i;
        for (i=0; i<size; i++) acum += Math.pow((double)(valores[i]-mean),2);
        return acum/(size-1);
     }
     public ArrayList e = new ArrayList<>();
     
     public boolean procesar_resultados()
     {
        TDistribution t = new TDistribution(TOTAL_TRAZAS);
        double media = mean_of(mean_waiting_time); /* media de todas las trazas */
        double varianza = variance_of(mean_waiting_time,TOTAL_TRAZAS,media);
        double tstudent = 0;
        
        if(TOTAL_TRAZAS > 400){
            tstudent = 1.96;
        }else{   
            tstudent = Math.abs(t.inverseCumulativeProbability(0.025));  
        }
        double ic = tstudent * Math.sqrt(varianza / TOTAL_TRAZAS);
        double error = ic / media;
        
        if (error <= (0.1/(0.1 + 1))){
            System.out.println((int)media);
            //System.out.println("var: " + varianza);
            //System.out.println("Tstudent: " + tstudent);
            // System.out.println("intervalo confianza: " + ic);
            // System.out.println("error relativo: " + error);
        }
        
        return error <= (0.1/(0.1 + 1));
     }
     public void traza()
     {    
         inicializar_traza();
         while (number_delayed[traza] < K)
         {  
            temporizacion();  

            if (clock == event_list.getA())
            {
                llegada_pasajero();           
            }else if( clock == event_list.getL()){              
                llegada_ascensor();              
            }else if (clock == event_list.getS()){
                salida_ascensor();      
            }else{
                fin_reflexion();   
            }
            
         } 
          
       mean_waiting_time[traza] = total_delayed[traza]/(number_delayed[traza]-cota_transitorio); 
         //mean_waiting_time[traza] = total_delayed[traza]/(number_delayed[traza]);
         //System.out.println(mean_waiting_time[traza]);
         
     }
    public void conseguir_transitorio()
    {
        inicializar_colas();
        for (int j = 1; j <100; j++){
          K = j;
          aceptable = false;
          while (!aceptable)
          {
              for (int i = 0; i< TOTAL_TRAZAS; i++)
              {
                  traza();
                  traza++;      
              }
              aceptable = procesar_resultados();
              traza = 0;
          }
        }
    }
    public void media_sin_transitorio()
    {
        inicializar_colas();
        aceptable = false;
        traza = 0;
        while (!aceptable)
        {
            for (int i = 1; i< TOTAL_TRAZAS; i++)
            {
                traza();
                traza++;      
            }
            aceptable = procesar_resultados();
            traza = 0;
        }
    }
    public void media_de_trazas()
    {
        inicializar_colas();
        aceptable = false;
        traza = 0;
        for (int i = 1; i< TOTAL_TRAZAS; i++)
        {
            traza();
            traza++;      
        }
        procesar_resultados();
   
    }
    public void principal()
    {
        inicializar_colas(); 
        // evolucion del tiempo medio segun lambda creciente;
        for (int j = 1; j <98; j++){
            aceptable = false;
            while (!aceptable)
            {
                for (int i = 0; i< TOTAL_TRAZAS; i++)
                {
                    traza();
                    traza++;      
                }
                aceptable = procesar_resultados();
                traza = 0;
            }
            lambda += 0.01;     
        }
        for (int i = 0; i< e.size(); i++)
        {
            System.out.println(e.get(i));
        }
        
     }
     public static void main(String[] args) {

        // TODO code application logic here         ¡
        Main m = new Main();
        m.principal();
        //m.media_de_trazas();
       
       //m.media_sin_transitorio();
       // m.conseguir_transitorio();

    }
    public void prueba()
    {
        TDistribution t = new TDistribution(5);
        double ts = Math.abs(t.inverseCumulativeProbability(0.025));
        System.out.println(ts);
    }

    
 
}
