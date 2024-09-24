package LLD_Key_Value;

public class TypeUtil {

    public static Object inferType(String value) {
        if (value.matches("-?\\d+")) {
            return Integer.parseInt(value);
        } else if (value.matches("-?\\d+\\.\\d+")) {
            return Double.parseDouble(value);
        } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(value);
        } else {
            return value;
        }
    }

    public static Object parseValue(String value, Class<?> type) {
        if (type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == Boolean.class) {
            return Boolean.parseBoolean(value);
        } else {
            return value;
        }
    }
}

