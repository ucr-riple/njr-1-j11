/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Drew
 */
public class GACoursePref implements GAInterface<Schedule>{

    private CoursePref coursePref;
     private Random rand = new Random();

    public GACoursePref(CoursePref coursePref)
    {
        this.coursePref = coursePref;
    }

    public ArrayList<Integer> makeIndividual() {

        ArrayList<Integer> individual = new ArrayList<Integer>();

        Iterator<EquivCourseSelection> i = coursePref.getAllCourses().iterator();

        while(i.hasNext())
        {
            individual.add(getRandomCourse(i.next()));
        }
        return individual;
    }

    private Integer getRandomCourse(EquivCourseSelection equiv)
    {
        int numCourses = 0;
        //negative one means no course

        for (int i = 0; i < equiv.getNumCourseSelection(); i++)
        {
            numCourses += equiv.getCourseSelection(i).getNumCourses();
        }

        if (numCourses == 0)
        {
            return -1;
        }
        return rand.nextInt(numCourses);


    }


    public Schedule decode(ArrayList<Integer> individual) {

        List<Course> theCourses = new ArrayList<Course>();
        Iterator<EquivCourseSelection> i = coursePref.getAllCourses().iterator();
        Iterator<Integer> codedI = individual.iterator();
        while (i.hasNext() && codedI.hasNext()) {
            EquivCourseSelection equiv = i.next();
            int code = codedI.next();
            int count = 0;
            here:
            for (int h = 0; h < equiv.getNumCourseSelection(); h++) {
                CourseSelection courses = equiv.getCourseSelection(h);
                for (int j = 0; j < courses.getNumCourses(); j++) {

                    if (code == count) {
                        theCourses.add(courses.getCourse(j));
                        break here;
                    }
                    count++;
                }
            }



        }
        return new Schedule(theCourses);
    }

    public Integer mutate(int index) {
        return getRandomCourse(coursePref.getEquivCourses(index));
    }

    
}
