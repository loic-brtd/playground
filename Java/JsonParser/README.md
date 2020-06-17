# JSON parser

user.json :
```json
{
    "string": "value",
    "array": [1, 2, 3],
    "object": {
        "number": 12,
        "boolean": true
    }
}
```

```java
File file = new File("user.json");

JsonObject obj = Json.parse(file).asObject();

String value = obj.get("string").asString();

int two = obj.get("array").asArray()
             .get(1).asInt();

int twelve = obj.get("object").asObject()
                .get("number").asInt();

boolean yes = obj.get("object").asObject()
                 .get("boolean").asBoolean();
```