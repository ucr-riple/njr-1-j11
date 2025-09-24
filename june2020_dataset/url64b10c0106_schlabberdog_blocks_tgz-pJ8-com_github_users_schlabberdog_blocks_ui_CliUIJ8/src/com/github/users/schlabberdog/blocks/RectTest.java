package com.github.users.schlabberdog.blocks;

import com.github.users.schlabberdog.blocks.mccs.Rect;
import org.junit.Test;
import static org.junit.Assert.*;

public class RectTest {

    @Test
    public void testIntersection() throws Exception {
        //einfacher fall: beide sind identisch
        {
            Rect a = new Rect(2,2,8,8);
            Rect b = new Rect(2,2,8,8);

            assertEquals(new Rect(2,2,8,8), Rect.intersection(a, b));
        }
        //einfacher fall: keine überschneidung
        {
            Rect a = new Rect(2,2,4,4);
            Rect b = new Rect(7,7,4,4);

            assertNull(Rect.intersection(a, b));
        }
        //einfacher fall: simple überschneidung
        {
            Rect a = new Rect(2,2,6,6);
            Rect b = new Rect(4,4,8,8);

            assertEquals(new Rect(4,4,4,4), Rect.intersection(a, b));
        }
        //B liegt komplett in A
        {
            Rect a = new Rect(2,2,6,6);
            Rect b = new Rect(3,3,3,3);

            assertEquals(new Rect(3,3,3,3), Rect.intersection(a, b));
        }
    }
}
