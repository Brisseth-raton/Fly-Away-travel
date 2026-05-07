package com.extra.extra.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateManyFlightRequest {

    private List<FlightRequest> inputs;
}