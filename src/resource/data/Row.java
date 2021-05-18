package resource.data;

import java.util.HashMap;
import java.util.Map;

public class Row {

        private String name;
        private Map<String, Object> fields;

    public Row(Map<String, Object> fields) {
        this.fields = new HashMap<>();

    }
    public void addField(String fieldName, Object value){
        this.fields.put(fieldName, value);
    }
    public void removeField(String fieldName){
        this.fields.remove(fieldName);
    }
}
