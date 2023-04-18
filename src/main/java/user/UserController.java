package user;

import db.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserController() {

    }

    private static class UserControllerHelper {
        private static final UserController INSTANCE = new UserController();
    }

    public static UserController getUserController() {
        return UserControllerHelper.INSTANCE;
    }
    public String join(Map<String, String> params) {
        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        Database.addUser(user);
        logger.debug("user : {}" , user.toString());

        return "redirect:/index.html";
    }

    public String login(Map<String, String> params) {
        Optional<User> user = Database.findUserById(params.get("userId"));
        if (user.isPresent()) {
            //Todo: http 헤더의 쿠키 값 설정, 여기서 하는게 맞을까?
            return "redirect:/index.html";
        }
        return "/user/login_failed.html";
    }
}
