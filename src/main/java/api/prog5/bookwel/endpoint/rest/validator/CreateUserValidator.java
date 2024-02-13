package api.prog5.bookwel.endpoint.rest.validator;

import api.prog5.bookwel.endpoint.rest.exception.BadRequestException;
import api.prog5.bookwel.endpoint.rest.model.CreateUser;
import api.prog5.bookwel.endpoint.rest.model.UserProfile;
import java.util.function.Consumer;
import org.springframework.stereotype.Component;

@Component
public class CreateUserValidator implements Consumer<CreateUser> {
    @Override
    public void accept(CreateUser createUser) {
        if (createUser.getProfile() == null) {
            throw new BadRequestException("profile is mandatory.");
        }
        UserProfile profile = createUser.getProfile();
        if (profile.getEmail() == null) {
            throw new BadRequestException("profile.email is mandatory.");
        }
    }
}
