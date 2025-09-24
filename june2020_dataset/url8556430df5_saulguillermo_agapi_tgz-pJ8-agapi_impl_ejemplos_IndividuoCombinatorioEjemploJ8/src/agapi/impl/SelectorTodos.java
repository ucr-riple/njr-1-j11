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
import agapi.Poblacion;
import agapi.Selector;

/**
 * Esta clase debe ser utilizada solo para realizar pruebas ya que realmente no
 * se ejecuta ningún tipo de <em>selección</em>. Simplemente se toman todos los
 * individuos de una población ordenadamente del primero al último, esto implica
 * que todos los individuos tendrán oportunidad de cruzarse y si, por ejemplo,
 * la <em>probabilidad de cruce</em> se configura en 1.0, absolutamente todos
 * los individuos se cruzarán, sin excepción. También, en forma opuesta, se
 * puede configurar la probabilidad de cruce en 0.0 y entonces ningún individuo
 * se cruzará.
 * 
 * @see agapi.Selector
 * @author Saul Gonzalez
 */
public class SelectorTodos implements Selector {
	private int contador;// contador de individuos
	private Individuo[] poolDeSeleccionados;// individuos elegidos

	/**
	 * Selecciona a todos los individuos de la población ordenadamente desde el
	 * primero hasta el último. Este orden se reinicia cada vez que se llama al
	 * método {@code prepararSeleccion}.
	 * 
	 * @param poblacion
	 *            la población de donde serán seleccionados los individuos
	 * @param cantidadSeleccionados
	 *            la cantidad de individuos a seleccionar
	 * @return un arreglo de tamaño {@code cantidadSeleccionados} con los
	 *         individuos seleccionados
	 */
	@Override
	public Individuo[] seleccion(Poblacion poblacion, int cantidadSeleccionados) {
		Individuo[] seleccionados = new Individuo[cantidadSeleccionados];
		int i = contador;
		int j = 0;
		while (j < cantidadSeleccionados) {// toma seleccionados del pool
			seleccionados[j] = poolDeSeleccionados[i];
			i++;
			j++;
		}
		// contador mantiene relacion entre seleccionados y poolDeSeleccionados
		contador = contador + cantidadSeleccionados;
		return seleccionados;
	}

	/**
	 * Su función es generar el pool con los individuos seleccionados, es decir,
	 * con TODOS los individuos de la población. También reinicia el conteo de
	 * individuos, esto con el objetivo de poder ser utilizado por el método
	 * {@code seleccion} para seleccionar ordenadamente un nueva población.
	 * 
	 * @param poblacion
	 *            la población cuyos individuos serán seleccionados (todos)
	 */
	@Override
	public void prepararSeleccion(Poblacion poblacion) {
		contador = 0;
		poolDeSeleccionados = poblacion.getIndividuos();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PRUEBA - Todos los individuos son seleccionados");
		return sb.toString();
	}

}