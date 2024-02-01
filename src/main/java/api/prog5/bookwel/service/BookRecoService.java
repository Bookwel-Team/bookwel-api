package api.prog5.bookwel.service;

import lombok.AllArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.ai.prompt.SystemPromptTemplate;
import org.springframework.ai.prompt.messages.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class BookRecoService {
  private static ChatClient chatClient;

  public Generation bookReco(String title, String author, String category) {
    String systemPrompt = """
                You are an AI Literary Recommendation Engine, providing book suggestions based on the given book titles, genres, and descriptions.
                """;
    SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPrompt);
    Message systemMessage = systemPromptTemplate.createMessage();

    // Create a prompt template
    PromptTemplate promptTemplate = new PromptTemplate("""
                %INSTRUCTIONS:
                Task:
                As an AI literary recommendation engine, your task is to analyze one or more {book titles with their descriptions} provided by the user.
                Based on the title, genres, and synopsis of each given book, you should suggest similar lesser-known books that the user might enjoy.
                Your recommendations should come from a diverse range of authors and prioritize books that aren't mainstream or widely popular but that are equally good or even better than the provided books.
                Your recommendations should not be bound by publication date or a fiction/non-fiction distinction unless specified in the input.
                For every recommended book, provide a brief synopsis (up-to 3 lines) and the reason for your choice.
                Provide up-to 3 book suggestions.
                When suggesting books, use below format.
                Title:
                Author:
                Synopsis:
                Reason for Recommendation:

                %BOOK TITLES WITH THEIR DESCRIPTIONS:
                """);

    Message userMessage = promptTemplate.createMessage(Map.of(
        "title", title,
        "author", author,
        "category", category
    ));

    Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

    return chatClient.generate(prompt).getGeneration();
  }
}
