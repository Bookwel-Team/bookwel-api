package api.prog5.bookwel.endpoint.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    @PutMapping("/users/{userId}")
    public Object crupdateUser(@RequestBody Object object){
        return object;
    }
}
