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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package agapi;

/**
 * Un proceso es un conjunto de <em>ejecuciones</em>. Es decir, un conjunto de
 * evoluciones independientes una de la otra. Esta formado por un arreglo de
 * objetos tipo {@linkplain agapi.Ejecucion Ejecucion}.
 * 
 * La finalidad de esta clase es obtener datos de varias ejecuciones en forma
 * automática para luego tener la posibilidad de computarlos todos
 * estadísticamente. Esto se logra por medio del método {@code procesar} que
 * inicia el proceso en una sucesión de ejecuciones.
 * 
 * @author Saul Gonzalez
 */
public class Proceso {

	private static int numeroEjecuciones;
	private Ejecucion[] ejecuciones;
	private long tiempoProceso;
	private boolean banderaProcesar;

	/**
	 * Devuelve el numero de {@linkplain agapi.Ejecucion ejecuciones} del
	 * proceso. Es decir, devuelve el numero de corridas independientes de
	 * algoritmos genéticos establecido.
	 * 
	 * @return el numero de ejecuciones.
	 */
	public static int getNumeroEjecuciones() {
		return numeroEjecuciones;
	}

	/**
	 * Establece el numero de {@linkplain agapi.Ejecucion ejecuciones} del
	 * algoritmo genético. Una <em>ejecución</em> es una corrida independiente
	 * de un algoritmo genético. Este parámetro esta disponible con el propósito
	 * de obtener datos de varias corridas en forma automática para luego tener
	 * la posibilidad de computarlos todos estadísticamente.
	 * <p>
	 * En las fases iniciales de desarrollo de un problema especifico se
	 * recomienda mantener este valor en 1, ya que valores mas elevados pudieran
	 * ocasionar colapsos de memoria en el sistema.
	 * 
	 * @param numeroEjecuciones
	 *            el numero de ejecuciones a utilizar.
	 * @throws IllegalArgumentException
	 *             si {@code numeroEjecuciones} es menor a 1.
	 */
	public static void setNumeroEjecuciones(int numeroEjecuciones) {
		if (numeroEjecuciones > 0) {
			Proceso.numeroEjecuciones = numeroEjecuciones;
		} else {
			throw new IllegalArgumentException(
					"numeroEjecuciones debe ser mayor a cero");
		}
	}

	/**
	 * Devuelve las {@linkplain agapi.Ejecucion ejecuciones} de un proceso. A
	 * partir de estas ejecuciones se pueden obtener otros datos de interés como
	 * las generaciones, las poblaciones y los individuos.
	 * 
	 * @return las ejecuciones del proceso.
	 */
	public Ejecucion[] getEjecuciones() {
		return ejecuciones;
	}

	/**
	 * Devuelve el tiempo que tarda el proceso en completarse. Es decir,
	 * devuelve el tiempo que tardan en ejecutarse todas las
	 * <em>ejecuciones</em>. Esta método debe llamarse después del método
	 * {@code procesar} de lo contrario se genera un excepción
	 * {@code IllegalStateException}.
	 * 
	 * @return el tiempo que se tarda el proceso.
	 * @throws IllegalStateException
	 *             si no se llama previamente al método {@code procesar}.
	 */
	public long getTiempoProceso() {
		if (banderaProcesar) {
			return tiempoProceso;
		} else {
			throw new IllegalStateException(
					"Aun no se ha ejecutado el método procesar");
		}
	}

	/**
	 * Inicia un proceso. Es decir, inicia una secuencia de
	 * {@linkplain agapi.Ejecucion ejecuciones}. Además, {@code procesar},
	 * calcula el tiempo en segundos que tarde en completarse el mismo y
	 * almacena esta valor en una variable a la que se puede acceder con el
	 * método {@code getTiempoProceso}.
	 */
	public void procesar() {
		long tiempoInicio = System.nanoTime();
		ejecuciones = new Ejecucion[numeroEjecuciones];
		for (int i = 0; i < numeroEjecuciones; i++) {
			ejecuciones[i] = new Ejecucion();
			ejecuciones[i].ejecutar();
		}
		tiempoProceso = System.nanoTime() - tiempoInicio;
		banderaProcesar = true;
	}

	/**
	 * Devuelve una cadena de caracteres con datos del proceso. Estos datos
	 * muestran la cantidad de ejecuciones mas los datos del algoritmo genético,
	 * que son los mismos datos de cada ejecución y son los configurados por los
	 * <em>setters</em> a los que se llama antes del método
	 * {@link agapi.Configuracion#iniciarProceso() iniciarProceso} de
	 * {@link agapi.Configuracion Configuracion}.
	 * 
	 * @return una cadena de caracteres con datos del proceso.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append("DATOS DEL PROCESO").append("\n");
		sb.append("Tipo de Individuo:").append("\t");
		sb.append(Individuo.getTipoIndividuo().getClass().getName()).append(
				"\n");
		sb.append("No de Ejecuciones:").append("\t");
		sb.append(getNumeroEjecuciones()).append("\n");
		sb.append("Metodo de Seleccion:").append("\t");
		sb.append(Generacion.getSelector()).append("\n");
		sb.append("Seleccion Post Cruce:").append("\t");
		sb.append(Generacion.getSelectorPostCruce()).append("\n");
		sb.append("Tamaño Cromosoma:").append("\t");
		sb.append(Individuo.getTamanoCromosoma()).append("\n");
		sb.append("Tamaño de Poblacion:").append("\t");
		sb.append(Poblacion.getTamanoPoblacion()).append("\n");
		sb.append("No de Generaciones:").append("\t");
		sb.append(Ejecucion.getNumeroGeneraciones()).append("\n");
		sb.append("Probabilidad Cruce:").append("\t");
		sb.append(Generacion.getProbabilidadCruce()).append("\n");
		sb.append("Probabilidad Mutacion:").append("\t");
		sb.append(Generacion.getProbabilidadMutacion()).append("\n");
		sb.append("Utilizar Elitismo?").append("\t");
		sb.append(Generacion.isElitismo()).append("\n");
		sb.append("\n");

		return sb.toString();
	}

	/**
	 * Devuelve una cadena de caracteres con datos de este proceso. El tipo de
	 * resultados varia en función de las opciones que se pasen como parámetros.
	 * Las opciones retornan lo siguiente:
	 * 
	 * <blockquote><b>{@code Configuracion.ULTIMA_GENERACION}</b> - Los datos
	 * básicos de la ultima generación de la ultima ejecución. </blockquote>
	 * 
	 * <blockquote><b>{@code Configuracion.GENERACIONES_SIN_POBLACIONES}</b> -
	 * Los datos básicos de todas las generaciones de todas las
	 * ejecuciones.</blockquote>
	 * 
	 * <blockquote><b>{@code Configuracion.GENERACIONES_CON_POBLACIONES}</b> -
	 * Los datos de todas las generaciones incluyendo sus respectivas
	 * poblaciones. </blockquote>
	 * 
	 * <strong>Advertencia:</strong> La cadena de caracteres depende de la
	 * representación que el usuario desee al sobrescribir el método
	 * {@code toString} del <em>individuo especifico</em>. Se debe recordar que
	 * un <em>individuo especifico</em> es una clase que hereda de la clase
	 * {@link agapi.Individuo Individuo}.
	 * 
	 * @param opcion
	 *            puede ser {@code Configuracion.ULTIMA_GENERACION},
	 *            {@code Configuracion.GENERACIONES_SIN_POBLACIONES} o
	 *            {@code Configuracion.GENERACIONES_CON_POBLACIONES}
	 * 
	 * @return una cadena de caracteres con datos del proceso.
	 * 
	 * @exception IllegalArgumentException
	 *                si no se selecciona alguna de las opciones válidas.
	 */
	protected String aTexto(int opcion) {
		StringBuilder sb = new StringBuilder();
		switch (opcion) {
		case (Configuracion.ULTIMA_GENERACION):
			sb.append(this.toString());
			sb.append("______________").append("EJECUCION ");
			sb.append(numeroEjecuciones - 1);
			sb.append("______________");
			sb.append("\n");
			Generacion[] g = ejecuciones[numeroEjecuciones - 1]
					.getGeneraciones();
			sb.append("Generacion ");
			sb.append(Ejecucion.getNumeroGeneraciones());
			sb.append("\n");
			sb.append(g[Ejecucion.getNumeroGeneraciones() - 1]);
			break;

		case (Configuracion.GENERACIONES_SIN_POBLACIONES):
			sb.append(this.toString());
			for (int i = 0; i < numeroEjecuciones; i++) {
				sb.append("\n");
				sb.append("______________").append("EJECUCION ").append(i + 1);
				sb.append("______________");
				sb.append("\n");
				sb.append(ejecuciones[i]);
			}
			break;

		case (Configuracion.GENERACIONES_CON_POBLACIONES):
			sb.append(this.toString());
			for (int i = 0; i < numeroEjecuciones; i++) {
				sb.append("\n");
				sb.append("______________").append("EJECUCION ").append(i + 1);
				sb.append("______________");
				sb.append("\n");
				sb.append(ejecuciones[i].toStringConPoblaciones());
			}
			break;
		default:
			throw new IllegalArgumentException("Opcion invalida");
		}
		return sb.toString();
	}
}