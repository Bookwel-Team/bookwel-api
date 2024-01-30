package api.prog5.bookwel.integration;

import api.prog5.bookwel.endpoint.rest.controller.BookController;
import api.prog5.bookwel.integration.mocks.CustomFacadeIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookIT extends CustomFacadeIT {
    @Autowired
    BookController subject;

    @Test
    void get_books_ok(){

    }
}
