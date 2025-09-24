package edu.umw.cpsc.collegediversity;

/**
 * A special kind of group intended for just freshmen. Every new 
 * {@link Student} will be assigned to an orientation group with several
 * other students during that first year.
 */
public class OrientationGroup extends Group {

    private int occupancy;

    /**
     * Constructor for new orientation groups.
     * @param occupancy the maximum number of {@link Student}s this
     * orientation group can contain.
     */
    public OrientationGroup(int occupancy){
        this.occupancy=occupancy;
    }

    /**
     * Attempt to add a member to this group.
     * @throws GroupFullException if this group already has its maximum
     * number of members.
     */
    public void addMember(Student s) throws GroupFullException {
        if(members.size()<=occupancy){
            super.addMember(s);
        }else{
            throw new GroupFullException();
        }
    }

}
