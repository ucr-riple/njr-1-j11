package DATA;

import java.io.Serializable;

public class Link implements Serializable {

		private Teacher teach;
		private Group group;
		private Field field;
		
		public Link(Teacher aTeach, Group aGroup, Field aField)	{
			this.teach = aTeach;
			this.group = aGroup;
			this.field = aField;
		}

		public Teacher getTeacher() {
			return teach;
		}

		public Group getGroup() {
			return group;
		}

		public Field getField() {
			return field;
		}

		public void setGroup(Group group) {
			this.group = group;
		}

		public void setTeacher(Teacher teach) {
			this.teach = teach;
		}

		public void setField(Field field) {
			this.field = field;
		}
		
		public String toString()	{
		
			return "Link : " + this.teach + " " + this.group + " " + this.field + "\n";
		}
}
