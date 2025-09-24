/* AGAPI - API para el desarrollo de Algoritmos Geneticos
 * Copyright (C) 2013 Saul Gonzalez
 * 
 * This library is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */

package agapi;

/*
 * Es un tablero de control donde se establecen los parámetros de un proceso y
 * se da inicio a este. Esto se logra al hacer llamados a distintos métodos
 * <em>setters</em> y al llamar al método {@code iniciarAlgoritmo} que acciona
 * la corrida.
 * <p>
 * Al crear un objeto de tipo {@code Configuracion} se deben hacer llamados a la
 * totalidad de sus 10 métodos <em>setters</em>, posteriormente se debe llamar
 * al método {@code iniciarAlgoritmo} para que se inicie la corrida del
 * algoritmo. Los pasos anteriores son de carácter <b>obligatorio</b> para
 * cualquier corrida, y si, por ejemplo, se omite el llamado a algún
 * <em>setter</em> y luego se llama a {@code iniciarAlgoritmo} se genera una
 * excepción {@code IllegalStateException} indicando cual es el <em>setter</em>
 * omitido y que debe ser llamado.
 * 
 * @author Saul Gonzalez
 */
public class Configuracion {

	/**
	 * Especifica al método {@code aTexto} devolver datos básicos de la última
	 * generación de la última ejecución.
	 */
	public static final int ULTIMA_GENERACION = 0;

	/**
	 * Especifica al método {@code aTexto} devolver los datos básicos de todas
	 * las generaciones de todas las ejecuciones.
	 */
	public static final int GENERACIONES_SIN_POBLACIONES = 1;

	/**
	 * Especifica al método {@code aTexto} devolver los datos de todas las
	 * generaciones incluyendo sus respectivas poblaciones.
	 */
	public static final int GENERACIONES_CON_POBLACIONES = 2;

	private Proceso proceso;

	private boolean banderaIniciarProceso;
	private boolean banderaNumeroEjecuciones;
	private boolean banderaNumeroGeneraciones;
	private boolean banderaSelector;
	private boolean banderaSelectorPostCruce;
	private boolean banderaProbabilidadCruce;
	private boolean banderaProbabilidadMutacion;
	private boolean banderaElitismo;
	private boolean banderaTamanoPoblacion;
	private boolean banderaTipoIndividuo;
	private boolean banderaTamanoCromosoma;

	/**
	 * Devuelve un objeto de tipo {@link agapi.Proceso Proceso}. Este objeto se
	 * crea el ejecutar el método {@code iniciarProceso}, por lo que devuelve el
	 * {@linkplain agapi.Proceso proceso} asociado a esta configuración. A
	 * partir de aquí se pueden obtener los demás datos de interés como
	 * ejecuciones, generaciones, poblaciones, individuos...
	 * 
	 * @return el proceso asociado a esta configuración.
	 */
	public Proceso getProceso() {
		if (banderaIniciarProceso) {
			return proceso;
		} else {
			throw new IllegalStateException(
					"El método iniciarProceso debe llamarse antes de getProceso");
		}
	}

	/**
	 * Inicia un proceso. Es decir, inicia una secuencia de
	 * {@linkplain agapi.Ejecucion ejecuciones}. Este método tiene como
	 * requisito, el previo llamado a los 10 <em>setters</em> que establecen los
	 * parámetros de un algoritmo genético, de lo contrario dispara un excepción
	 * {@code IllegalStateException}.
	 * <p>
	 * Se recomienda el llamado a los <em>setters</em> en el siguiente orden:
	 * 
	 * <blockquote>1. {@code setNumeroEjecuciones}</blockquote>
	 * 
	 * <blockquote>2. {@code setNumeroGeneraciones}</blockquote>
	 * 
	 * <blockquote>3. {@code setSelector}</blockquote>
	 * 
	 * <blockquote>4. {@code setSelectorPostCruce}</blockquote>
	 * 
	 * <blockquote>5. {@code setProbabilidadCruce}</blockquote>
	 * 
	 * <blockquote>6. {@code setProbabilidadMutacion}</blockquote>
	 * 
	 * <blockquote>7. {@code setElitismo}</blockquote>
	 * 
	 * <blockquote>8. {@code setTamanoPoblacion}</blockquote>
	 * 
	 * <blockquote>9. {@code setTipoIndividuo}</blockquote>
	 * 
	 * <blockquote>10. {@code setTamanoCromosoma}</blockquote>
	 * 
	 * @throws IllegalStateException
	 *             si alguno de los 10 métodos obligatorios no ha sido llamado.
	 */
	public void iniciarProceso() {

		if (!banderaNumeroEjecuciones) {
			throw new IllegalStateException(
					"setNumeroEjecuciones debe llamarse antes de iniciarProceso");
		}
		if (!banderaNumeroGeneraciones) {
			throw new IllegalStateException(
					"setNumeroGeneraciones debe llamarse antes de iniciarProceso");
		}
		if (!banderaSelector) {
			throw new IllegalStateException(
					"setSelector debe llamarse antes de iniciarProceso");
		}
		if (!banderaSelectorPostCruce) {
			throw new IllegalStateException(
					"setSelectorPostCruce debe llamarse antes de iniciarProceso");
		}
		if (!banderaProbabilidadCruce) {
			throw new IllegalStateException(
					"setProbabilidadCruce debe llamarse antes de iniciarProceso");
		}
		if (!banderaProbabilidadMutacion) {
			throw new IllegalStateException(
					"setProbabilidadMutacion debe llamarse antes de iniciarProceso");
		}
		if (!banderaElitismo) {
			throw new IllegalStateException(
					"EsetElitismo debe llamarse antes de iniciarProceso");
		}
		if (!banderaTamanoPoblacion) {
			throw new IllegalStateException(
					"setTamanoPoblacion debe llamarse antes de iniciarProceso");
		}
		if (!banderaTipoIndividuo) {
			throw new IllegalStateException(
					"setTipoIndividuo debe llamarse antes de iniciarProceso");
		}
		if (!banderaTamanoCromosoma) {
			throw new IllegalStateException(
					"setTamanoCromosoma debe llamarse antes de iniciarProceso");
		}

		// Corrida del Algoritmo
		proceso = new Proceso();
		proceso.procesar();
		banderaIniciarProceso = true;
	}

	/**
	 * Establece el número de {@linkplain agapi.Ejecucion ejecuciones} del
	 * {@linkplain agapi.Proceso proceso}. Una <em>ejecución</em> es una corrida
	 * independiente de un algoritmo genético. El parámetro
	 * {@code numeroEjecuciones} se establece con el propósito de obtener datos
	 * de varias corridas en forma automática para luego tener la posibilidad de
	 * computarlos todos estadísticamente.
	 * <p>
	 * En las fases iniciales de desarrollo de un problema especifico se
	 * recomienda mantener este valor en 1, ya que valores más elevados pudieran
	 * ocasionar colapsos de memoria en el sistema.
	 * 
	 * @param numeroEjecuciones
	 *            el número de ejecuciones a utilizar.
	 * @throws IllegalArgumentException
	 *             si {@code numeroEjecuciones} es menor a 1.
	 */
	public void setNumeroEjecuciones(int numeroEjecuciones) {
		if (numeroEjecuciones > 0) {
			Proceso.setNumeroEjecuciones(numeroEjecuciones);
		} else {
			throw new IllegalArgumentException(
					"numeroEjecuciones debe ser mayor a cero");
		}
		banderaNumeroEjecuciones = true;
	}

	/**
	 * Establece el número de {@linkplain agapi.Generacion generaciones} por
	 * ejecución. Una <em>generación</em> consiste en una <em>población</em> más
	 * un conjunto de operadores genéticos y otras variables que son aplicadas a
	 * dicha población para crear una nueva generación.
	 * <p>
	 * Una generación produce otra generación y esta a la siguiente y así
	 * sucesivamente hasta alcanzar una cantidad igual al valor establecido en
	 * este método. Una vez se llega a la última generación se detiene la
	 * ejecución y se pasa a la siguiente si lo permite el parámetro
	 * {@code numeroEjecuciones}.
	 * 
	 * @param numeroGeneraciones
	 *            el numero de generaciones a utilizar.
	 * @throws IllegalArgumentException
	 *             si {@code numeroGeneraciones} es menor a 1.
	 */
	public void setNumeroGeneraciones(int numeroGeneraciones) {
		if (numeroGeneraciones > 0) {
			Ejecucion.setNumeroGeneraciones(numeroGeneraciones);
		} else {
			throw new IllegalArgumentException(
					"numeroGeneraciones debe ser mayor a cero");
		}
		banderaNumeroGeneraciones = true;
	}

	/**
	 * Determina que <em>selector</em> se va a utilizar en la etapa de
	 * selección. Un <em>selector</em> es un objeto de una clase que implemente
	 * la interface {@link agapi.Selector Selector} y que por consiguiente
	 * implemente el método {@link agapi.Selector#seleccion seleccion} encargado
	 * de seleccionar dos individuos de la población para que tengan la
	 * posibilidad de cruzarse, siguiendo un criterio especifico.
	 * 
	 * @param selector
	 *            el selector a utilizar
	 * @throws NullPointerException
	 *             si {@code selector} es {@code null}.
	 */
	public void setSelector(Selector selector) {
		if (selector != null) {
			Generacion.setSelector(selector);
		} else {
			throw new NullPointerException("selector no debe ser null");
		}
		banderaSelector = true;
	}

	/**
	 * Determina que <em>selector post cruce</em> se va a utilizar después del
	 * cruce. El <em> selector post cruce</em> es un objeto de una clase que
	 * implementa la interface {@link agapi.SelectorPostCruce} y que por
	 * consiguiente implemente el método
	 * {@link agapi.SelectorPostCruce#seleccionPostCruce seleccionPostCruce}.
	 * <p>
	 * Una vez efectuado el cruce entre dos individuos se producen un par de
	 * hijos, transformando un par en dos pares de individuos,
	 * {@code seleccionPostCruce} se encarga de determinar quienes entre los
	 * cuatro individuos pasaran a la siguiente generación.
	 * 
	 * @param selectorPostCruce
	 *            el selector post cruce a utilizar.
	 * @throws NullPointerException
	 *             si {@code selectorPostCruce} es {@code null}.
	 */
	public void setSelectorPostCruce(SelectorPostCruce selectorPostCruce) {
		if (selectorPostCruce != null) {
			Generacion.setSelectorPostCruce(selectorPostCruce);
		} else {
			throw new NullPointerException("selectorPostCruce no debe ser null");
		}
		banderaSelectorPostCruce = true;
	}

	/**
	 * Establece el valor de la probabilidad de cruce a utilizar. Una vez que un
	 * par de individuos es seleccionado por el <em>selector</em>, sus
	 * posibilidades de cruzarse dependen del valor establecido en este método.
	 * Este valor debe estar comprendido entre cero y uno de lo contrario se
	 * produce una excepción {@code IllegalArgumentException}.
	 * 
	 * @param probabilidadCruce
	 *            el valor de la probabilidad de cruce a utilizar.
	 * @throws IllegalArgumentException
	 *             si {@code probabilidadCruce} sale del rango (0,1).
	 */
	public void setProbabilidadCruce(double probabilidadCruce) {
		if (probabilidadCruce >= 0.0 && probabilidadCruce <= 1.0) {
			Generacion.setProbabilidadCruce(probabilidadCruce);
		} else {
			throw new IllegalArgumentException(
					"probabilidadCruce debe estar dentro del rango (0,1)");
		}
		banderaProbabilidadCruce = true;
	}

	/**
	 * Establece el valor de la probabilidad de mutación a utilizar. Después que
	 * un par de individuos se cruzan, el par de hijos resultantes tienen la
	 * posibilidad de mutar sus cromosomas, esta posibilidad la define el valor
	 * establecido en este método. Este valor debe estar comprendido entre cero
	 * y uno de lo contrario se produce una excepción
	 * {@code IllegalArgumentException}.
	 * 
	 * @param probabilidadMutacion
	 *            el valor de la probabilidad de mutación a utilizar.
	 * @throws IllegalArgumentException
	 *             si {@code probabilidadMutacion} sale del rango (0,1).
	 */
	public void setProbabilidadMutacion(double probabilidadMutacion) {
		if (probabilidadMutacion >= 0.0 && probabilidadMutacion <= 1.0) {
			Generacion.setProbabilidadMutacion(probabilidadMutacion);
		} else {
			throw new IllegalArgumentException(
					"probabilidadMutacion debe estar dentro del rango (0,1)");
		}
		banderaProbabilidadMutacion = true;
	}

	/**
	 * Establece si aplica o no el <em>elitismo</em>. Se llama <em>elitismo</em>
	 * al mecanismo utilizado en algunos algoritmos genéticos para asegurar que
	 * los individuos más aptos de una población pasen a la siguiente generación
	 * sin ser alterados por ningun operador genético.
	 * <p>
	 * El elitismo asegura que la aptitud máxima de la población nunca se
	 * reducirá de una generación a la siguiente. Sin embargo, no necesariamente
	 * mejora la posibilidad de localizar el optimo global de una función.
	 * 
	 * @param elitismo
	 *            un boolean, true si se desea utilizar elitismo.
	 */
	public void setElitismo(boolean elitismo) {
		Generacion.setElitismo(elitismo);
		banderaElitismo = true;
	}

	/**
	 * Establece el tamaño de la población, es decir, la cantidad de individuos.
	 * 
	 * @param tamanoPoblacion
	 *            el tamaño de la población a establecer
	 * @throws IllegalArgumentException
	 *             si {@code tamanoPoblacion} es menor a 2.
	 */
	public void setTamanoPoblacion(int tamanoPoblacion) {
		if (tamanoPoblacion > 1) {
			Poblacion.setTamanoPoblacion(tamanoPoblacion);
		} else {
			throw new IllegalArgumentException(
					"tamanoPoblacion debe ser mayor a 1.");
		}
		banderaTamanoPoblacion = true;
	}

	/**
	 * Establece el tipo de <em>individuo especifico</em> que se va a utilizar.
	 * Un <em>individuo especifico</em> es un objeto de una clase que ha
	 * heredado de {@link agapi.Individuo Individuo} y ha implementado todos sus
	 * métodos, esta es una tarea que corresponde al usuario.
	 * 
	 * @param tipoIndividuo
	 *            el tipo de individuo a establecer
	 * @throws NullPointerException
	 *             si {@code tipoIndividuo} es {@code null}.
	 */
	public void setTipoIndividuo(Individuo tipoIndividuo) {
		if (tipoIndividuo != null) {
			Individuo.setTipoIndividuo(tipoIndividuo);
		} else {
			throw new NullPointerException("tipoIndividuo no debe ser null");
		}
		banderaTipoIndividuo = true;
	}

	/**
	 * Establece el tamaño del cromosoma del <em>individuo especifico</em>. Este
	 * valor esta intrínsecamente relacionado con el diseño del
	 * <em>individuo especifico</em> por lo que el usuario debe tener muy claro
	 * como la variación del tamaño afecta los datos del algoritmo genético.
	 * Generalmente cuando se trata de cromosomas binarios, este valor deber ser
	 * fijo.
	 * <p>
	 * Un <em>individuo especifico</em> es un objeto de una clase que ha
	 * heredado de {@link agapi.Individuo Individuo} y ha implementado todos sus
	 * métodos, para lo cual, obligatoriamente, debe establecer el valor del
	 * tamaño del cromosoma como una variable determinada por el método
	 * {@link agapi.Individuo#getTamanoCromosoma getTamanoCromosoma}.
	 * 
	 * @param tamanoCromosoma
	 *            el tamaño de cromosoma a establecer
	 * @throws IllegalArgumentException
	 *             si {@code tamanoCromosoma} es menor a 1.
	 */
	public void setTamanoCromosoma(int tamanoCromosoma) {
		if (tamanoCromosoma > 0) {
			Individuo.setTamanoCromosoma(tamanoCromosoma);
		} else {
			throw new IllegalArgumentException(
					"tamanoCromosoma debe ser mayor a cero");
		}
		banderaTamanoCromosoma = true;
	}

	/**
	 * Devuelve una cadena de caracteres con datos del proceso. El tipo de
	 * resultados varia en función de las opciones que se pasen como parámetros.
	 * Las opciones retornan lo siguiente:
	 * 
	 * <blockquote><b>{@code ULTIMA_GENERACION}</b> - Los datos básicos de la
	 * última generación de la última ejecución. </blockquote>
	 * 
	 * <blockquote><b>{@code GENERACIONES_SIN_POBLACIONES}</b> - Los datos
	 * básicos de todas las generaciones de todas las ejecuciones.</blockquote>
	 * 
	 * <blockquote><b>{@code GENERACIONES_CON_POBLACIONES}</b> - Los datos de
	 * todas las generaciones incluyendo sus respectivas poblaciones.
	 * </blockquote>
	 * 
	 * <strong>Advertencia:</strong> La cadena de caracteres depende de la
	 * representación que el usuario desee al sobrescribir el método
	 * {@code toString} del <em>individuo especifico</em>. Se debe recordar que
	 * un <em>individuo especifico</em> es una clase que hereda de la clase
	 * {@link agapi.Individuo Individuo}.
	 * 
	 * @param opcion
	 *            puede ser {@code ULTIMA_GENERACION},
	 *            {@code GENERACIONES_SIN_POBLACIONES} o
	 *            {@code GENERACIONES_CON_POBLACIONES}
	 * 
	 * @return una cadena de caracteres con datos del proceso.
	 * 
	 * @throws IllegalArgumentException
	 *             si no se selecciona alguna de las opciones válidas.
	 */
	public String aTexto(int opcion) {
		String s;
		switch (opcion) {
		case (ULTIMA_GENERACION):
			s = proceso.aTexto(ULTIMA_GENERACION);
			break;

		case (GENERACIONES_SIN_POBLACIONES):
			s = proceso.aTexto(GENERACIONES_SIN_POBLACIONES);
			break;

		case (GENERACIONES_CON_POBLACIONES):
			s = proceso.aTexto(GENERACIONES_CON_POBLACIONES);
			break;
		default:
			throw new IllegalArgumentException("Opcion invalida");
		}
		return s;
	}
}
