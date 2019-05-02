package jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import org.jsoup.Jsoup
import org.jsoup.safety.Whitelist

class HtmlCleanModule : SimpleModule() {

    init {
        addDeserializer(String::class.java, HtmlCleanDeserializer())
    }

    private inner class HtmlCleanDeserializer : StdScalarDeserializer<String>(String::class.java) {
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): String {
            return Jsoup.clean(p.valueAsString, Whitelist.basic())
        }
    }
}