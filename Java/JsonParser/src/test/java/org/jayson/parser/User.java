package org.jayson.parser;

import org.jayson.Json;
import org.jayson.dto.JsonObject;
import org.jayson.format.JsonFormatter;

import java.time.LocalDate;

public class User {

    private final String username;
    private final String passwordHash;
    private final LocalDate dateOfBirth;
    private final boolean isPremium;

    public User(String username, String passwordHash, LocalDate dateOfBirth, boolean isPremium) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.dateOfBirth = dateOfBirth;
        this.isPremium = isPremium;
    }

    private static User fromJson(String json) {
        JsonObject user = Json.parseObject(json);
        return new User(user.getString("username"),
                user.getString("passwordHash"),
                dateFromJson(user.getObject("dateOfBirth")),
                user.getBoolean("isPremium"));
    }

    public String toJson() {
        return Json.object()
                .put("username", username)
                .put("passwordHash", passwordHash)
                .put("dateOfBirth", dateToJson(dateOfBirth))
                .put("isPremium", isPremium)
                .format(JsonFormatter.MINIMIZED);
    }

    private JsonObject dateToJson(LocalDate d) {
        return Json.object()
                .put("day", d.getDayOfMonth())
                .put("month", d.getMonthValue())
                .put("year", d.getYear());
    }

    private static LocalDate dateFromJson(JsonObject obj) {
        return LocalDate.of(obj.getLong("year").intValue(),
                obj.getLong("month").intValue(),
                obj.getLong("day").intValue());
    }

    public static void main(String[] args) {
        User user = new User("loic", "fg5h65fg", LocalDate.of(1998, 1, 19), true);
        String json = user.toJson();
        System.out.println(json);
        User loaded = User.fromJson(json);
        System.out.println(loaded.toJson());
    }
}
