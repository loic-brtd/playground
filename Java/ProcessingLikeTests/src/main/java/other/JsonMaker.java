package other;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class JsonMaker {

  public static String jsonify(Object obj) {
    return makeObject(obj, 0);
  }

  private static String makeNSpaces(int n) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; i++) {
      sb.append(" ");
    }
    return sb.toString();
  }

  private static String makeObject(Object obj, int level) {
    String spaces = makeNSpaces(level * 2);
    if (obj != null) {
      StringBuilder sb = new StringBuilder("{\n");
      Field[] fields = obj.getClass().getDeclaredFields();
      for (int i = 0; i < fields.length - 1; i++) {
        Field field = fields[i];
        field.setAccessible(true);
        // Name
        String name = field.getName();
        sb.append(spaces + "  ");
        sb.append("\"" + name + "\": ");
        // Value
        Object value = "null";
        try {
          value = field.get(obj);
        } catch (Exception e) {
          e.printStackTrace();
        }
        sb.append(makeValue(value, level + 1));
        if (i != fields.length - 2) {
          sb.append(", ");
        }
        sb.append("\n");
      }
      sb.append(spaces);
      sb.append("}");
      return sb.toString();
    } else {
      return "null";
    }
  }

  private static String makeValue(Object value, int level) {
    if (value == null) {
      return "null";
    } else if (value.getClass().isArray()) {
      return makeArray(value, level);
    } else if (value instanceof String) {
      return "\"" + value + "\"";
    } else if (value instanceof Number || value instanceof Boolean) {
      return String.valueOf(value);
    } else if (level < 6){
      return makeObject(value, level);
    }
    return "MAX_LEVEL";
  }

  private static String makeArray(Object array, int level) {
    StringBuilder sb = new StringBuilder();
    int len = Array.getLength(array);
    if (len == 0) {
      sb.append("[]");
    } else {
      sb.append("[\n");
      for (int i = 0; i < len; i++) {
        Object obj = Array.get(array, i);
        sb.append(makeNSpaces(level * 2 + 2));
        sb.append(makeValue(obj, level));
        if (i != len - 1) {
          sb.append(",");
        }
        sb.append("\n");
      }
      sb.append(makeNSpaces(level * 2));
      sb.append("]");
    }
    return sb.toString();
  }

}
