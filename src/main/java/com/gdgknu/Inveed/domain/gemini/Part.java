package com.gdgknu.Inveed.domain.gemini;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Part(@JsonProperty("text") String text) {}
