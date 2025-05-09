package com.gdgknu.Inveed.domain.gemini;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Content(@JsonProperty("parts") List<Part> parts) {}
