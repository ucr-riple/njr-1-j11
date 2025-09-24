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

package agapi.impl;

import agapi.Poblacion;

/**
 * Esta interface es utilizada por los selectores estocásticos, por ejemplo
 * {@code SelectorRuleta} y {@code SelectorSUS}, para la definición de la
 * función de valor esperado de sus individuos. La clase que implemente esta
 * interface debe desarrollar una función que determine todos los valores
 * esperados de los individuos de la población pasada como parámetro.
 * <p>
 * La forma clásica de la función de valor esperado utilizado por Goldberg
 * (1989) consiste en una función directamente proporcional a la función de
 * aptitud y que solo depende de esta; sin embargo otros trabajos (Tanese 1989)
 * han utilizado métodos de {@linkplain agapi.impl.FuncionTanese escalamiento}
 * que toman en cuenta, no solo la función de aptitud, sino también las medias y
 * las desviaciones estándares. También se han utilizado funciones no
 * proporcionales (Baker 1985) como en la selección
 * <em>{@linkplain agapi.impl.FuncionRanking por jerarquías (ranking)}</em> que
 * no dependen directamente de la aptitud.
 * 
 * @author sebukan
 * 
 */
public interface Funcion {

	/**
	 * Devuelve un arreglo con todos los valores esperados de los individuos de
	 * la población pasada como parámetro.
	 * 
	 * @param poblacion
	 *            la población a cuyos individuos se les calcularán la función
	 *            de valor esperado.
	 * @return un arreglo con todos los valores esperados de los individuos de
	 *         la población.
	 */
	double[] calcValEsp(Poblacion poblacion);

}