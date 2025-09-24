/* AGAPI - API para el desarrollo de Algoritmos Geneticos
 * Copyright (C) 2013 Saul Gonzalez
 * 
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package agapi.impl.ejemplos;

import agapi.Configuracion;
import agapi.impl.*;

public class IndividuoCombinatorioEjemplo extends IndividuoCombinatorio {

	@Override
	public double calcFA() {
		double fa = 0;
		int[] s = this.getCromosoma();
		for (int i = 0; i < s.length; i++) {
			fa = (double) fa + (i + 1) * s[i];
		}
		return fa;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int[] s = this.getCromosoma();
		for (int i = 0; i < s.length; i++) {
			if (i != s.length - 1) {
				sb.append(s[i] + "-");
			} else {
				sb.append(s[i]);
			}
		}
		sb.append(" FA: " + this.getFA());
		return sb.toString();
	}

	public static void main(String[] args) {
		Configuracion c = new Configuracion();

		c.setNumeroEjecuciones(1);
		c.setNumeroGeneraciones(500);
		c.setSelector(new SelectorSUS(new FuncionClasico()));
		c.setSelectorPostCruce(new SelectorPostCruceSoloHijos());
		c.setProbabilidadCruce(0.7);
		c.setProbabilidadMutacion(0.05);
		c.setElitismo(true);
		c.setTamanoPoblacion(100);
		c.setTipoIndividuo(new IndividuoCombinatorioEjemplo());
		c.setTamanoCromosoma(20);
		
		c.iniciarProceso();
		
		System.out.println(
				c.aTexto(Configuracion.GENERACIONES_CON_POBLACIONES));
		double tiempo = c.getProceso().getTiempoProceso() / 1000000000.0;
		System.out.println("Tiempo: " + tiempo + " segundos");
	}
}
