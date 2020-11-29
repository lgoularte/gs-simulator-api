package com.gomspace.simulator;

import com.gomspace.simulator.domain.WhiteBlackGrid;
import com.gomspace.simulator.domain.Simulation;
import com.gomspace.simulator.domain.Vector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimulationService {

    /**
     * Simulates an object moving through an infinite black and white grid.
     *
     * Initial state:
     * - Grid: all squares white.
     * - Position: (0, 0)
     * - Direction: (1, 0)
     *
     * The step length is defined by the direction magnitude, here 1 unit.
     *
     * @param steps number of steps to simulate
     * @return simulation result
     */
    public Simulation simulateWhiteBlackGrid(Integer steps) {
        final WhiteBlackGrid grid = new WhiteBlackGrid();
        final Vector initialPosition = new Vector(0, 0);
        final Vector initialDirection = new Vector(1, 0);
        return simulateWhiteBlackGrid(grid, initialPosition, initialDirection, steps);
    }

    /**
     * This method simulates an object moving through an infinite black and white grid.
     * It is generic enough to allow different initial states (grid, position, direction).
     * The moving algorithm is described bellow:
     *
     * Each movement is defined based on the current square color.
     * - If the square is white, it turns direction 90 degrees clockwise and moves forward.
     * - If the square is black, it turns direction 90 degrees counter-clockwise and moves forward.
     * After each movement the base squares color flips.
     *
     * The movements are simulated by a vector sum in the Cartesian space.
     * - The first vector points the current position.
     * - The second vector represents the movement direction and magnitude.
     * - The resulting vector points to the new position.
     *
     * @param grid grid initial state
     * @param initialPosition initial position
     * @param initialDirection initial direction
     * @param steps number of steps to simulate
     * @return simulation result
     */
    private Simulation simulateWhiteBlackGrid(
        final WhiteBlackGrid grid, final Vector initialPosition, final Vector initialDirection, final int steps
    ) {
        log.debug(String.format("[Simulation] Simulating infinite white black grid walking [initial position: %s, initial direction: %s, steps: %d]", initialPosition, initialDirection, steps));
        Vector position = initialPosition;
        Vector direction = initialDirection;

        for (int i = 0; i < steps; i++) {
            if (grid.isSquareBlack(position)) {
                direction = direction.rotate90DegreesCounterClockwise();
            } else {
                direction = direction.rotate90DegreesClockwise();
            }
            grid.flipSquareColor(position);
            log.debug(String.format("[Step %d] Moving from %s in the direction %s", i, position.toString(), direction.toString()));

            position = Vector.sum(position, direction);
            log.debug(String.format("[Step %d] New position: %s%n", i, position.toString()));
        }
        Simulation simulation =  new Simulation();
        simulation.setDraw(grid.draw());
        return simulation;
    }
}
