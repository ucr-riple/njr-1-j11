package jatrace;

/** This interface describes the sky of the simulated world. This sky is
 *  idealized in that it is "infinitely far away". */
public interface Sky
{
    /** Gets the color of the sky in the given direction. */
    public Color getColor(Vector direction);
    
    /** Gets an array of Vectors that describe the positions of the light
     *  sources in the sky. For the purposes of soft shadows, light-vector
     *  deltas should be precomputed when returned here. As of now, all light
     *  sources returned are considered equally bright. */
    public Vector [] getLight();
}
