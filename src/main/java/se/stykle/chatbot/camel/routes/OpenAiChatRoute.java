package se.stykle.chatbot.camel.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.stykle.chatbot.camel.processor.OpenAIResponseProcessor;

@Component
public class OpenAiChatRoute extends RouteBuilder {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Override
    public void configure() {
        from("direct:chatGPT")
                .routeId("openai-chat-route")
                .log(LoggingLevel.INFO, "Sending message to OpenAI: ${body}")
                .setHeader("Authorization", constant("Bearer " + apiKey))
                .setHeader("Content-Type", constant("application/json"))
                .toD(apiUrl + "?bridgeEndpoint=true&throwExceptionOnFailure=false")
                .process(new OpenAIResponseProcessor())
                .log(LoggingLevel.INFO, "Cleaned response: ${body}");
    }

}

