package com.gomspace.simulator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class represents a position vector in the Cartesian space.
 *
 * It allows to rotate the vector 90 degrees clockwise or counter-clockwise applying
 * the following rotation matrix:
 *
 * Rot(θ) = | cosθ  -sinθ |
 *          | sinθ   cosθ |
 *
 * For 90 (counter-clockwise) and -90 (clockwise) degrees cos is always 0 and sin is 1 and -1 respectively.
 * Resulting in the following matrices:
 *
 * Rot(90) = | 0  -1 |
 *           | 1   0 |
 *
 * Rot(-90) = |  0  1 |
 *            | -1  0 |
 */
@Getter
@AllArgsConstructor
public class Vector {

    /**
     * Util method to sum two vectors.
     *
     * @param v1 first vector
     * @param v2 second vector
     * @return resulting vector
     */
    public static Vector sum(Vector v1, Vector v2) {
        int x = v1.getX() + v2.getX();
        int y = v1.getY() + v2.getY();
        return new Vector(x, y);
    }

    /**
     * Vector terminal point.
     */
    final private int x;
    final private int y;

    /**
     *  Clockwise means negative degrees (-90).
     *  Applying the rotation matrix to the coordinate we have:
     *  x' =  y
     *  y' = -x
     *
     *  @return resulting vector
     */
    public Vector rotate90DegreesClockwise() {
        int _x = y, _y = -x;
        return new Vector(_x, _y);
    }

    /**
     *  Counter-clockwise means positive degrees (90).
     *  Applying the rotation matrix to the coordinate we have:
     *  x' = -y
     *  y' =  x
     *
     *  @return resulting vector
     */
    public Vector rotate90DegreesCounterClockwise() {
        int _x = -y, _y = x;
        return new Vector(_x, _y);
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }

}
