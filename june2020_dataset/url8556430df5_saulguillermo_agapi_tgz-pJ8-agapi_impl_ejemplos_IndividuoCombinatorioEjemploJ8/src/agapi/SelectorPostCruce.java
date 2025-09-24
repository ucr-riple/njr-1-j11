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
 * La interface SelectorPostCruce se ocupa de la selección que ocurre una vez se
 * ha realizado el cruce entre dos individuos. Es un proceso que no tiene
 * relación con la interface {@code Selector} ni con el método {@code seleccion}
 * de la misma.
 * <p>
 * El proceso de cruce involucra a dos padres (que se cruzan) y dos hijos (que
 * descienden de sus padres), al tener estos cuatro individuos se plantea la
 * pregunta: ¿Quienes pasarán a la siguiente generación?. El
 * usuario debe tomar esta decisión y estructurarla con la
 * implementación del método {@code seleccionPostCruce}.
 * <p>
 * Generalmente se utilizan dos criterios: seleccionar solo a los hijos o
 * seleccionar a las mejores entre padres e hijos. Ambos criterios se encuentran
 * implementados en las clases {@link agapi.impl.SelectorPostCruceSoloHijos} y
 * {@link agapi.impl.SelectorPostCruceTaigeto} ubicadas en el paquete
 * {@link agapi.impl}.
 * 
 * @author Saul Gonzalez
 */
public interface SelectorPostCruce {

	/**
	 * Este método selecciona dos individuos entre padres e hijos. Devuelve a un
	 * arreglo de {@linkplain agapi.Individuo individuos} que <b>siempre</b>
	 * debe ser de tamaño <b>dos (2)</b>. Sus parámetros también son arreglos de
	 * individuos de tamaño dos (2) que corresponden a padres e hijos.
	 * 
	 * @param padres
	 *            los 2 individuos que se cruzan
	 * @param hijos
	 *            los 2 individuos productos del cruce
	 * @return los 2 individuos seleccionados para la siguiente generación
	 */
	Individuo[] seleccionPostCruce(Individuo[] padres, Individuo[] hijos);
}