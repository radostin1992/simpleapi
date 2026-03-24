package com.myproject.simpleapi.entities;

public record JokeFromExtApi(String type,
        String setup, String punchline, Long id) {
}
