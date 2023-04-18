package db;

import com.google.common.collect.Maps;

import user.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findUserById(String userId) {
        return users.entrySet().stream()
                .filter(e -> userId.equals(e.getKey()))
                .map(Map.Entry::getValue)
                .findAny();
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
