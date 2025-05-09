package com.gdgknu.Inveed.domain.gemini;

import org.springframework.web.bind.annotation.*;

@RestController
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/ask-gemini")
    public GeminiResDTO askGemini(@RequestBody GeminiReqDTO geminiReqDTO) throws Exception {
        return geminiService.sendPrompt(geminiReqDTO);
    }
}
