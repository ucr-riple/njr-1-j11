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
 * Es el tipo clásico de selección post cruce. Este tipo de selección es el
 * clásico utilizado por David Goldberg (1989) en su libro
 * <em>"Genetic algorithms in search, optimization, and machine learning"</em>
 * (Algoritmos Genéticos en Búsqueda, Optimización y Aprendizaje de Máquinas),
 * en el que solo los hijos pasan a la siguiente generación, los padres son
 * desechados independientemente de su aptitud, lo que abre la posibilidad de
 * tener descendientes menos aptos que sus padres y por tanto la convergencia
 * hacia alguna solución puede ser más lenta.
 * 
 * @see agapi.SelectorPostCruce
 * @author Saul Gonzalez
 */
public class SelectorPostCruceSoloHijos implements SelectorPostCruce {

	/**
	 * Este método solo selecciona a los hijos productos del cruce entre los
	 * padres.
	 * 
	 * @param padres
	 *            los 2 individuos que se cruzan
	 * @param hijos
	 *            los 2 individuos productos del cruce
	 * @return los 2 individuos seleccionados para la siguiente generación
	 */
	@Override
	public Individuo[] seleccionPostCruce(Individuo[] padres, Individuo[] hijos) {
		return hijos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Solo Hijos");
		return sb.toString();
	}

}