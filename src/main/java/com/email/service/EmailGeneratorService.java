package com.email.service;

import com.email.EmailRequest.EmailRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailGeneratorService {
    private final WebClient webClient;
    private final String apiKey;

    public EmailGeneratorService(  WebClient.Builder webClientBuilder,
                            @Value("${gemini.api.url}") String baseUrl,
                            @Value("${gemini.api.key}") String geminiApiKey)
    {
        this.apiKey=geminiApiKey;
        this.webClient=webClientBuilder.baseUrl(baseUrl).build();
    }
    public String generateEmailReply(EmailRequest emailRequest) {

        //Build prompt
        String prompt=buildPrompt(emailRequest);
        
        //Prepare raw JSON Body
        String requestBody=String.format("""
                {
                    "contents": [
                      {
                        "parts": [
                          {
                            "text": "%s"
                          }
                        ]
                      }
                    ]
                  }""",prompt);
        //Send Request

        //Response Extraction
        return "";
    }

    private String buildPrompt(EmailRequest emailRequest) {
        StringBuilder prompt=new StringBuilder();
        prompt.append("Generate a professional email reply for the following email");
        if(emailRequest.getTone()!=null && !emailRequest.getTone().isEmpty()){
            prompt.append("Use a ").append(emailRequest.getTone()).append(" tone");
        }
        prompt.append("Original Email: \n ").append(emailRequest.getEmailContent());
        return prompt.toString();
    }
}
