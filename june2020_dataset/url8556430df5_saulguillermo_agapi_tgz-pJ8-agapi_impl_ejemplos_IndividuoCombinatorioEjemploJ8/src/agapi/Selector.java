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
 * Un selector selecciona individuos que tendrán posibilidad de cruzarse.
 * Cualquier clase de desee aplicar algún criterio de selección debe implementar
 * esta interface, en ella se encuentran dos métodos:
 * 
 * <blockquote>{@code seleccion} se encarga de seleccionar una cantidad
 * especifica de individuos que tendrán la posibilidad de cruzarse, ello según
 * el criterio definido por el usuario al implementar esta interface.
 * </blockquote>
 * 
 * <blockquote>{@code preparar} permite aplicar cálculos adicionales a toda la
 * población que luego puedan ser utilizados por {@code seleccion} como, por
 * ejemplo, calcular el valor esperado de cada individuo u ordenarlos por
 * jerarquías. Este método se aplica a toda una población y se ejecuta antes del
 * método {@link agapi.Generacion#generar() generar} de la clase
 * {@link agapi.Generacion}. </blockquote>
 * 
 * @see agapi.impl.SelectorEstocastico
 * @see agapi.impl.SelectorRuleta
 * @see agapi.impl.SelectorSUS
 * @see agapi.impl.SelectorTodos
 * 
 * @author Saul Gonzalez
 */
public interface Selector {

	/**
	 * Selecciona una cantidad especifica de individuos de la población que
	 * tendrán la posibilidad de cruzarse. El criterio utilizado para dicha
	 * selección es propio de la clase que implemente esta interface.
	 * <p>
	 * Este método es llamado por {@link agapi.Generador#generar(Generacion)
	 * generar} de {@link agapi.Generador} cada vez que se deseen seleccionar
	 * individuos con oportunidades de cruce.
	 * <p>
	 * Puede utilizar el método {@code prepararSeleccion} como apoyo cuando se
	 * deban calcular datos adicionales o hacer algún ordenamiento.
	 * 
	 * @param poblacion
	 *            la población de donde serán seleccionados los individuos
	 * @param cantidadSeleccionados
	 *            la cantidad de individuos a seleccionar
	 * @return un arreglo de tamaño {@code cantidadSeleccionados} con los
	 *         individuos seleccionados
	 */
	Individuo[] seleccion(Poblacion poblacion, int cantidadSeleccionados);

	/**
	 * Puede utilizarse para calcular datos adicionales a una población. Por
	 * ejemplo, se puede ordenar la población o calcular los valores esperados
	 * de cada individuo. Todo ello con la finalidad que el método
	 * {@code seleccion} cumpla con los requisitos del criterio de selección
	 * elegido por el usuario.
	 * <p>
	 * Este método es llamado por {@link agapi.Ejecucion#ejecutar() ejecutar} de
	 * la clase {@link agapi.Ejecucion} una vez por generación, dado que en cada
	 * generación se debe preparar los datos que utilizara la siguiente
	 * generación.
	 * <p>
	 * La implementación de este método puede quedar vacía (sin código) si el
	 * método {@code seleccion} no necesita datos adicionales o algún tipo de
	 * ordenamiento.
	 * 
	 * @param poblacion
	 *            la población a la que se le aplicaran cálculos adicionales
	 */
	void prepararSeleccion(Poblacion poblacion);

}