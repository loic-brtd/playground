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
JsonElement obj = Json.parse(file);

String value = obj.get("string").asString();
int two      = obj.get("array").get(1).asInt();
int twelve   = obj.get("object").get("number").asInt();
boolean yes  = obj.get("object").get("boolean").asBoolean();
```