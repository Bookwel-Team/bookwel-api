package api.prog5.bookwel.endpoint.rest.controller;

import api.prog5.bookwel.service.ChattingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ChatBotController {
  private final ChattingService chattingService;

  @PostMapping("/chats")
  public String chat(@RequestBody String userPrompt) {
    return chattingService.chat(userPrompt);
  }
}
