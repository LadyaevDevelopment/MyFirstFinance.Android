package ladyaev.development.myfirstfinance.core.api.apiclients.network.adapters

import java.io.*
import java.text.*
import java.util.*
import android.os.*
import android.annotation.*
import android.util.Log
import com.google.gson.*
import com.google.gson.stream.*

@Suppress("MemberVisibilityCanBePrivate")
@SuppressLint("ObsoleteSdkInt")
class DateAdapter(var serializationStrategy: DateSerializationStrategy) : TypeAdapter<Date?>() {
	companion object {
		fun createDefaultIso8601Format(): SimpleDateFormat {
			return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.ROOT).apply {
				timeZone = TimeZone.getTimeZone("UTC")
			}
		}
	}

	@Throws(IOException::class)
	override fun write(writer: JsonWriter, value: Date?) {
		if (value == null) writer.nullValue() else {
			when (serializationStrategy) {
				DateSerializationStrategy.ISO_8601 -> writer.value(createDefaultIso8601Format().format(value))
				DateSerializationStrategy.MILLISECONDS_SINCE_1970 -> writer.value(value.time)
				DateSerializationStrategy.SECONDS_SINCE_1970 -> writer.value(value.time / 1000)
			}
		}
	}

	@Throws(IOException::class)
	override fun read(reader: JsonReader): Date? {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull()
			return null
		}
		return when (serializationStrategy) {
			DateSerializationStrategy.ISO_8601 -> {
				val dateString = reader.nextString()
				val formats = mutableListOf(createDefaultIso8601Format())
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
					formats.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSX", Locale.ROOT))
				}
				formats.add(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ", Locale.ROOT))
				for (format in formats) {
					try {
						return format.parse(dateString)
					}
					catch (e: ParseException) {
						Log.d("DateAdapter", "Can not parse date $dateString with format string ${format.toPattern()}")
					}
				}
				throw IOException(ParseException("Can not parse date $dateString", 0))
			}
			DateSerializationStrategy.MILLISECONDS_SINCE_1970 -> Date(reader.nextLong())
			DateSerializationStrategy.SECONDS_SINCE_1970 -> Date(reader.nextLong() * 1000)
		}
	}

	enum class DateSerializationStrategy {
		ISO_8601, MILLISECONDS_SINCE_1970, SECONDS_SINCE_1970
	}
}
