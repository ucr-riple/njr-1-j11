package attribute;

import java.util.HashMap;
import java.util.Map;

import units.Unit;
import units.reactions.Reaction;
import environment.Environment;

/**
 * Makes the unit reactable to environmental factors
 * Contains a map of reactions that the unit reacts to the environment
 * and a map of booleans that that tell the unit if they can/cannot move onto the environment
 * @author lenajia
 *
 */
public class AttributeReactable extends Attribute {
		private Map <Environment, Reaction> myReactions;
		private Map <Environment, Boolean> myValidMoves;
		
		public AttributeReactable(Unit unit,Map <Environment, Reaction> reactions, Map<Environment, Boolean> moves){
		    super(unit);
		    myReactions = reactions;
		    myValidMoves = moves;
		}
		
		public AttributeReactable(Unit unit){
			   this (unit, new HashMap <Environment, Reaction>(), new HashMap<Environment, Boolean>());
		}
		
		/**
		 * Checks to see if the unit can move onto the environment,
		 * based on the specific type of unit and environment  
		 * @param u
		 * @param environ
		 * @return boolean
		 */
		public boolean isValidMove(Unit u, Environment environ) {
			Boolean r = myValidMoves.get(environ);
			if (r!= null)
				return r.booleanValue();
			return true;
        }
		
		/**
		 * Gets the appropriate reaction based on the passed in environment and applies
		 * the reaction on the unit
		 * @param u
		 * @param environ
		 */
		public void reactToEnvironment(Unit u, Environment environ) {
			Reaction r = myReactions.get(environ);
			if (u != null && r!=null) r.apply(u);
        }
		
		@Override
        public void refresh() {
	        // TODO Auto-generated method stub
	        
        }
		
		public void setReaction(Environment environ, Reaction r) {
			myReactions.put(environ, r);
		}
		
		public void setMap(Map<Environment, Reaction> map) {
			myReactions = map;
		}
		public void setMoves(Map<Environment, Boolean> map) {
			myValidMoves = map;
		}

        @Override
        public String name() {
            return "Reactable";
        }

        @Override
        public void augmentDataTemplate(Object dataElement) {
            // TODO Auto-generated method stub
            
        }
}
