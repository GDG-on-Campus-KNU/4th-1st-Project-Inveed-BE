package com.gdgknu.Inveed.domain.gemini;

public record GeminiReqDTO(String prompt) {

    public String toPrompt() {
        return prompt;
    }
}
