package ladyaev.development.myfirstfinance.core.api.apiclients.network.adapters

import java.io.*
import java.math.*
import java.text.*
import java.util.*
import android.os.*
import com.google.gson.*
import com.google.gson.stream.*

@Suppress("MemberVisibilityCanBePrivate")
class DecimalAdapter(var serializationStrategy: DecimalSerializationStrategy) : TypeAdapter<BigDecimal?>() {
	@Throws(IOException::class)
	override fun write(writer: JsonWriter, value: BigDecimal?) {
		if (value == null) writer.nullValue() else {
			when (serializationStrategy) {
				DecimalSerializationStrategy.NUMBER -> writer.value(value)
				DecimalSerializationStrategy.STRING -> writer.value(value.toString())
			}
		}
	}

	@Throws(IOException::class)
	override fun read(reader: JsonReader): BigDecimal? {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull()
			return null
		}
		return BigDecimal(reader.nextString())
	}

	enum class DecimalSerializationStrategy {
		NUMBER, STRING
	}
}
