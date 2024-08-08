package ladyaev.development.myfirstfinance.core.api.apiclients.network.adapters

import java.io.*
import java.lang.reflect.*
import java.security.*
import java.util.*
import com.google.gson.*
import com.google.gson.annotations.*
import com.google.gson.reflect.*
import com.google.gson.stream.*

@Suppress("UNCHECKED_CAST", "MemberVisibilityCanBePrivate")
class EnumAdapterFactory(val serializationStrategy: EnumSerializationStrategy) :
	TypeAdapterFactory {
	override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
		type.rawType
		var rawType = type.rawType
		type.rawType.isEnum
		if (!Enum::class.java.isAssignableFrom(rawType) || rawType == Enum::class.java) {
			return null
		}
		if (!rawType.isEnum) {
			rawType = rawType.superclass
		}
		return EnumAdapter(rawType as Class<T>, serializationStrategy)
	}

	internal class EnumAdapter<T>(
		classOfT: Class<T>,
		private val serializationStrategy: EnumSerializationStrategy
	) : TypeAdapter<T>() {
		private val nameToConstant: MutableMap<String, T> = HashMap()
		private val constantToName: MutableMap<T, String> = HashMap()

		init {
			try {
				val constantFields = AccessController.doPrivileged(
					PrivilegedAction {
						val fields = classOfT.declaredFields
						val constantFieldsList = ArrayList<Field>(fields.size)
						for (f in fields) {
							if (f.isEnumConstant) {
								constantFieldsList.add(f)
							}
						}
						val constantFields = constantFieldsList.toTypedArray()
						AccessibleObject.setAccessible(constantFields, true)
						constantFields
					})
				for (constantField in constantFields) {
					val constant = constantField[null] as Enum<*>
					var name = constant.name
					val annotation = constantField.getAnnotation(SerializedName::class.java)
					if (annotation != null) {
						name = annotation.value
						for (alternate in annotation.alternate) {
							nameToConstant[alternate] = constant as T
						}
					}
					nameToConstant[name] = constant as T
					constantToName[constant] = name
				}
			} catch (e: IllegalAccessException) {
				throw AssertionError(e)
			}
		}

		@Throws(IOException::class)
		override fun read(reader: JsonReader): T? {
			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull()
				return null
			}
			if (serializationStrategy == EnumSerializationStrategy.INTEGERS) {
				val key = reader.nextLong().toString()
				assertValueIsValid(key)
				return nameToConstant[key]
			}
			val key = reader.nextString()
			assertValueIsValid(key)
			return nameToConstant[key]
		}

		@Throws(IOException::class)
		override fun write(writer: JsonWriter, value: T?) {
			when (serializationStrategy) {
				EnumSerializationStrategy.INTEGERS -> writer.value(
					if (value == null) null else constantToName[value]!!
						.toLong()
				)
				EnumSerializationStrategy.STRINGS -> writer.value(if (value == null) null else constantToName[value])
			}
		}

		@Throws(IOException::class)
		private fun assertValueIsValid(value: String) {
			if (!nameToConstant.containsKey(value)) {
				throw IOException("Value $value is not valid; available values are " +
						nameToConstant.keys.sorted().joinToString(", ") + "]")
			}
		}
	}

	enum class EnumSerializationStrategy {
		INTEGERS, STRINGS
	}
}
