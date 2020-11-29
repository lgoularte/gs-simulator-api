package com.gomspace.simulator;

import com.gomspace.simulator.domain.Simulation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.security.InvalidParameterException;

@Slf4j
@RestController
@RequestMapping("/simulations")
public class SimulationsController {

    final private SimulationService simulationService;

    @Autowired
    public SimulationsController(final SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @PutMapping("/white-black-grid")
    public ResponseEntity<Resource> simulateWhiteBlackGrid(@RequestParam(value = "steps", defaultValue = "1000") String steps) {
        try {
            validateParams(steps);
            Simulation simulation = simulationService.simulateWhiteBlackGrid(Integer.valueOf(steps));

            OutputStream output = new ByteArrayOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            char[][] draw = simulation.getDraw();
            for (int i=0; i < draw.length; i++) {
                writer.println(draw[i]);
            }
            writer.close();

            String filename = String.format("simulation-%s.txt", simulation.getId());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .headers(headers)
                .body(new ByteArrayResource(output.toString().getBytes()));
        } catch (InvalidParameterException ex) {
            return ResponseEntity.badRequest()
                .body(new ByteArrayResource(ex.getMessage().getBytes()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Validates request params.
     *
     * @param steps simulation steps
     */
    private void validateParams(String steps) {
        try {
            Integer value = Integer.valueOf(steps);
            if (value < 1) {
                throw new InvalidParameterException("Steps must be greater than zero.");
            }
        } catch (NumberFormatException ex) {
            throw new InvalidParameterException("Steps must be a valid integer greater than zero.");
        }
    }
}
