package se.stykle.chatbot.camel.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class OpenAIResponseProcessor implements Processor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void process(Exchange exchange) throws Exception {

        String responseString = exchange.getIn().getBody(String.class);
        JsonNode jsonResponse = objectMapper.readTree(responseString);
        String content = jsonResponse.path("choices").get(0).path("message").path("content").asText();

        exchange.getIn().setBody(content);
    }

}
