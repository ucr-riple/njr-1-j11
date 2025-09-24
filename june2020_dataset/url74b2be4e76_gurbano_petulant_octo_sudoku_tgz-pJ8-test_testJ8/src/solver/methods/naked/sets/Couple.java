package solver.methods.naked.sets;

import java.util.List;

public class Couple{
		public Integer a,b;

		public Couple(int a, int b) {
			super();
			this.a = a;
			this.b = b;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + a;
			result = prime * result + b;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Couple other = (Couple) obj;
		
			if (a != other.a)
				return false;
			if (b != other.b)
				return false;
			return true;
		}

	

		@Override
		public String toString() {
			return "[" + a + ", " + b + "]";
		}
		
		public boolean isIn(List<Integer> candidates){
			return (candidates.contains(a) && candidates.contains(b));
		}
		public boolean atLeastOneIsIn(List<Integer> candidates) {
			return (candidates.contains(a) || candidates.contains(b));
		}
		public int size() {
			return 2;
		}


	}