package com.natixis.rag_test.controller;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.natixis.rag_test.Pergunta;
import org.json.JSONObject;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ChatController {

    private final ChatClient chatClient;

    private static final String BASE_URL = "https://isep-team-wl0oghzs.atlassian.net/wiki";
    private static final String API_TOKEN = "ATATT3xFfGF0Y8K_6vpVO8zcNh0Ubyj1Biv1g0S_IG5UvDp1ao43Dyuzj9OYzlhQFOITCFfVYUE4F-QjK_WedUcnBNxOFu8OmYaDBJ_k5aqQZecBA0sswkLbwhKliI2aOd2ef5c_UVtLxVqZTQ7uy72kRUwXK350-AjuUKsojDEwX200_RgwJ5A=516880D3";
    private static final String EMAIL = "1191048@isep.ipp.pt";
    private static final String PAGE_ID = "393223"; // Replace with your actual page ID

    public ChatController(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
    }

    @GetMapping("/")
    public String chat(@RequestBody Pergunta pergunta) {
        return chatClient.prompt()
                .user(pergunta.pergunta)
                .call()
                .content();
    }

    @GetMapping("/confluence/{id}")
    public String confluence(@PathVariable String id) {
       /* HttpResponse response = null;
        try {
            response =  Unirest.get("https://isep-team-wl0oghzs.atlassian.net/wiki/api/v2/pages")
                    .basicAuth(EMAIL, API_TOKEN)
                    .header("Accept", "application/json")
                    .asJson();
            System.out.println(response.getBody());
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }


        */

        return getPageContent(id);

    }

    public static String getPageContent(String pageId) {
        String url = BASE_URL + "/rest/api/content/" + pageId + "?expand=body.storage";

        try {
        HttpResponse<JsonNode> response = Unirest.get(url)
                .basicAuth(EMAIL, API_TOKEN)
                .header("Accept", "application/json")
                .asJson();



            if (response.getStatus() == 200) {
                JSONObject body = response.getBody().getObject();
                return body.getJSONObject("body")
                        .getJSONObject("storage")
                        .getString("value"); // Retrieves page content in storage format
            } else {
                System.err.println("Error: " + response.getStatus() + " - " + response.getStatusText());
                return null;
            }

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

}