package user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final UserController userController = new UserController();

    private UserController() {

    }

    public static UserController getUserController() {
        return userController;
    }
    public String join(Map<String, String> params) {
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        logger.debug("user : {}" , user.toString());

        return "/index.html";
    }
}
