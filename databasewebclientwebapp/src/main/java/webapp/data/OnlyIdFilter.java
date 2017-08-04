package webapp.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

public class OnlyIdFilter extends SimpleBeanPropertyFilter {
    @Override
    public void serializeAsField
            (Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer)
            throws Exception {
        if (include(writer)) {
            if (writer.getName().equals("id")) {
                writer.serializeAsField(pojo, jgen, provider);
                return;
            }
            return;
        }
//        else if (!jgen.canOmitFields()) {
//            writer.serializeAsOmittedField(pojo, jgen, provider);
//        }
    }
    @Override
    protected boolean include(BeanPropertyWriter writer) {
        return true;
    }
    @Override
    protected boolean include(PropertyWriter writer) {
        return true;
    }

}
