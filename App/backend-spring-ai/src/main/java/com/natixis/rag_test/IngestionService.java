package com.natixis.rag_test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class IngestionService implements CommandLineRunner {

    private static final String BASE_URL = "https://isep-team-wl0oghzs.atlassian.net/wiki";
    private static final String API_TOKEN = "ATATT3xFfGF0Y8K_6vpVO8zcNh0Ubyj1Biv1g0S_IG5UvDp1ao43Dyuzj9OYzlhQFOITCFfVYUE4F-QjK_WedUcnBNxOFu8OmYaDBJ_k5aqQZecBA0sswkLbwhKliI2aOd2ef5c_UVtLxVqZTQ7uy72kRUwXK350-AjuUKsojDEwX200_RgwJ5A=516880D3";
    private static final String EMAIL = "1191048@isep.ipp.pt";
    private static final String PAGE_ID = "393223";

    private static final Logger log = LoggerFactory.getLogger(IngestionService.class);
    private final VectorStore vectorStore;

    @Value("classpath:/docs/doc1.pdf")
    private Resource marketPDF;

    public IngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) throws Exception {
        var reader = new MarkdownDocumentReader(getConfluenceDocument(PAGE_ID),
                 MarkdownDocumentReaderConfig.builder().build());
       // var reader = new ParagraphPdfDocumentReader(marketPDF);
        TextSplitter textSplitter = new TokenTextSplitter();
        vectorStore.accept(textSplitter.apply(reader.get()));
        log.info("VectorStore Loaded with data!");
    }

    private Resource getConfluenceDocument(String pageId) {


        String url = BASE_URL + "/rest/api/content/" + pageId + "?expand=body.storage";

        try {
            HttpResponse<JsonNode> response = Unirest.get(url)
                    .basicAuth(EMAIL, API_TOKEN)
                    .header("Accept", "application/json")
                    .asJson();

            if (response.getStatus() == 200) {
                JSONObject body = response.getBody().getObject();
                String html =  body.getJSONObject("body")
                        .getJSONObject("storage")
                        .getString("value");


                String markdown = FlexmarkHtmlConverter.builder().build().convert(html);

                return  new ByteArrayResource(markdown.getBytes());

            }

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}