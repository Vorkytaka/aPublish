package jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule

class TrimModule : SimpleModule() {
    init {
        addDeserializer(String::class.java, TrimDeserializer())
    }

    private inner class TrimDeserializer : StdScalarDeserializer<String>(String::class.java) {
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): String {
            return p.valueAsString.trim()
        }
    }
}