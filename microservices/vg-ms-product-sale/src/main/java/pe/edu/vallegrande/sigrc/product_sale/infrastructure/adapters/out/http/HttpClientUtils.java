package pe.edu.vallegrande.sigrc.product_sale.infrastructure.adapters.out.http;

import java.util.Map;

public final class HttpClientUtils {

    private HttpClientUtils() {
    }

    public static String str(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val != null ? val.toString() : null;
    }

    public static Integer toInt(Object value) {
        if (value == null) return null;
        if (value instanceof Number) return ((Number) value).intValue();
        if (value instanceof String) return Integer.parseInt((String) value);
        return null;
    }
}
