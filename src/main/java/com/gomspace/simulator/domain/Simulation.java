package com.gomspace.simulator.domain;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
public class Simulation {

    @Setter(AccessLevel.NONE)
    final private UUID id = UUID.randomUUID();
    private char[][] draw;

}
