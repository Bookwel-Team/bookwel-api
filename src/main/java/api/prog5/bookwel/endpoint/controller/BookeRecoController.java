package api.prog5.bookwel.endpoint.controller;

import api.prog5.bookwel.service.BookRecoService;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.Generation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BookeRecoController {

  private final BookRecoService bookRecoService;

  @GetMapping("/book")
  public Generation bookReco(
    @RequestParam String title, @RequestParam String author, @RequestParam String category) {
    return bookRecoService.bookReco(title, author, category);
  }
}
