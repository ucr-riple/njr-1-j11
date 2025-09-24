package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Talanov
 */
public interface Actor {

    void performMove(@NotNull SimulationState simulationState);

}
