package user;

import db.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.Session;

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

    public String login(Map<String, String> params, Session session) {
        Optional<User> user = Database.findUserById(params.get("userId"));
        //form으로 엽력한 아이디 존재 시
        if (user.isPresent()) {
            //비밀번호 일치 시 redirect
            if (user.get().getPassword().equals(params.get("password"))) {
                //세션 생성
                session.createSessionID();
                return "redirect:/index.html";
            }
        }
        return "/user/login_failed.html";
    }
}
