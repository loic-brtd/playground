package org.jayson.parser;

import org.jayson.Json;
import org.jayson.dto.JsonObject;

import java.time.LocalDate;

import static org.jayson.format.JsonFormatter.MINIMIZED;

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
        JsonObject user = Json.parse(json).asObject();
        return new User(user.get("username").asString(),
                user.get("passwordHash").asString(),
                dateFromJson(user.get("dateOfBirth").asObject()),
                user.get("isPremium").asBoolean());
    }

    public String toJson() {
        return Json.object()
                .put("username", username)
                .put("passwordHash", passwordHash)
                .put("dateOfBirth", dateToJson(dateOfBirth))
                .put("isPremium", isPremium)
                .format(MINIMIZED);
    }

    private JsonObject dateToJson(LocalDate d) {
        return Json.object()
                .put("day", d.getDayOfMonth())
                .put("month", d.getMonthValue())
                .put("year", d.getYear());
    }

    private static LocalDate dateFromJson(JsonObject obj) {
        return LocalDate.of(obj.get("year").asInt(),
                obj.get("month").asInt(),
                obj.get("day").asInt());
    }

    public static void main(String[] args) {
        User user = new User("loic", "fg5h65fg", LocalDate.of(1998, 1, 19), true);
        String json = user.toJson();
        System.out.println(json);
        User loaded = User.fromJson(json);
        System.out.println(loaded.toJson());

        System.out.println(
                Json.parse("[{}, null, {}]").asArray()
                        .get(1).asObject()
        );

        JsonObject object = Json.parse("null").asObject();
        System.out.println(object);
    }
}
