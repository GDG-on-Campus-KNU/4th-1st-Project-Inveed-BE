package com.gdgknu.Inveed.domain.gemini;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Service
@RequiredArgsConstructor
public class GeminiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

//    private final String geminiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";
//    private final String geminiKey = "AIzaSyAXsfG3E5aiC51KUeUvxkf0vrmyeTfo5yA";
    @Value("${gemini.url}")
    private String geminiUrl;

    @Value("${gemini.key}")
    private String geminiKey;


    public GeminiResDTO sendPrompt(GeminiReqDTO geminiReqDTO) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        GeminiRequest request = new GeminiRequest(List.of(new Content(List.of(new Part(geminiReqDTO.toPrompt())))));

        HttpEntity<GeminiRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(geminiUrl+geminiKey, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
             return GeminiResDTO.fromResponse(extractText(response.getBody()));
        } else {
            throw new RuntimeException("Gemini API error: " + response.getStatusCode());
        }
    }

    public String extractText(String jsonResponse) throws Exception {
        JsonNode rootNode = objectMapper.readTree(jsonResponse);
        return rootNode.path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();
    }

}


