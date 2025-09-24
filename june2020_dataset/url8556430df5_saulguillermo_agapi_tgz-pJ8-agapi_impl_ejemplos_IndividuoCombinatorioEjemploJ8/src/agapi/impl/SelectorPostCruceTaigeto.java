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

import agapi.Individuo;
import agapi.SelectorPostCruce;

/**
 * Este tipo de selección es el utilizado por Díaz & Hernández (2010) en el
 * Trabajo de Grado
 * <em>"Desarrollo de un Algoritmo Genético para la secuencia de
 * tareas en ambientes Job-Shop en el taller de mecanización de la empresa
 * metalmecánica Moldes Mafra"</em> (Universidad de Carabobo). Consiste en
 * seleccionar a los dos mejores individuos entre los dos padres y los dos hijos
 * basados el valor de la función de aptitud de cada uno. Esta técnica garantiza
 * descendientes más aptos que sus padres, lo que puede traer como consecuencia
 * una convergencia más rápida hacia alguna solución.
 * 
 * @author Saul Gonzalez
 */
public class SelectorPostCruceTaigeto implements SelectorPostCruce {

	/**
	 * Este método selecciona a los dos mejores individuos entre dos padres y
	 * dos hijos descendientes del cruce de los primeros.
	 * 
	 * @param padres
	 *            los 2 individuos que se cruzan
	 * @param hijos
	 *            los 2 individuos productos del cruce
	 * @return los 2 individuos seleccionados para la siguiente generación
	 */
	@Override
	public Individuo[] seleccionPostCruce(Individuo[] padres, Individuo[] hijos) {
		Individuo[] exitosos = new Individuo[2];
		Individuo[] a = new Individuo[4];
		a[0] = padres[0];
		a[1] = padres[1];
		a[2] = hijos[0];
		a[3] = hijos[1];
		double mayor = -Double.MAX_VALUE;
		int indice = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i].getFA() >= mayor) {
				exitosos[0] = a[i];
				mayor = a[i].getFA();
				indice = i;
			}
		}
		double segundoMayor = -Double.MAX_VALUE;
		for (int i = 0; i < a.length; i++) {
			if (i != indice) {
				if (a[i].getFA() >= segundoMayor) {
					exitosos[1] = a[i];
					segundoMayor = a[i].getFA();
				}
			}
		}
		return exitosos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Taigeto");
		return sb.toString();
	}

}