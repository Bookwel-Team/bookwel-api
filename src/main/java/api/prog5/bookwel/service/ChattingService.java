package api.prog5.bookwel.service;

import api.prog5.bookwel.Generated;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Generated
public class ChattingService {
  private final ChatClient chatClient;
  private final String systemPrompt =
      """
                    You are an AI Literary Recommendation Engine, providing book suggestions %s.
                   """;

  public String chat(String userPrompt) {
    SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPrompt);
    Message systemMessage = systemPromptTemplate.createMessage();

    PromptTemplate promptTemplate =
        new PromptTemplate(
            """
                    %INSTRUCTIONS:
                    Task:
                    As an AI literary recommendation engine, your task is to analyze {userPrompt}, provided by the user %s.
                    Based on the title, genres, and synopsis of each given book, you should suggest similar lesser-known books that the user might enjoy %s.
                    Your recommendations should come from a diverse range of authors and prioritize books that aren't mainstream or widely popular but that are equally good or even better than the provided books %s.
                    Your recommendations should not be bound by publication date or a fiction/non-fiction distinction unless specified in the input %s.
                    For every recommended book, provide a brief synopsis (up-to 3 lines) and the reason for your choice %s.
                    Then give a pdf link to the book so that the user can watch it directly %s.
                    Provide up-to 10 book suggestions %s.
                    When suggesting books, use below format in json array format like this %s.
                    title:
                    author:
                    synopsis:
                    category:
                    reason:
                    """);

    Message userMessage = promptTemplate.createMessage(Map.of("userPrompt", userPrompt));

    Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
    return chatClient.call(prompt).getResult().getOutput().getContent();
  }
}
