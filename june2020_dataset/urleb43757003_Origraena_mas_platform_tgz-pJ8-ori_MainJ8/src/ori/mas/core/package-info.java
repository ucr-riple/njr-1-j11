/**
 * This package contains all the mas-platform core.
 * <p>
 * An agent is composed by a Body and a Mind.
 * The first represents the agent inside its world, thus it provides
 * a shape and physical properties.
 * The second is the decision maker, at each world tick, the mind can
 * make a choice.
 * The body is divided into three main sets, the sensors are used to
 * perceive the world and convert it to a percept. The actors are
 * used to produce an influence which will be taken into account by
 * the world heart. Finally, the properties are a map of key/value where
 * keys are strings, and values can be any object.
 * </p>
 * <p>
 * The world may be seen as an agent set and a scene. 
 * The scene is the world physical representation, this is what agents perceive,
 * and where they can act. At each tick, all agents produce
 * influences, the world submit them to its heart. Once all agents have made
 * their decision, the heart "pulses", analysing and applying all influences.
 * </p>
 */
package ori.mas.core;
