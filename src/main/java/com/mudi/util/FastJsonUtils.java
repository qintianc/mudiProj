package com.mudi.util;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * fastjson工具类
 * @version:1.0.0
 */
public class FastJsonUtils {

	private static final SerializeConfig config;

	static {
		config = new SerializeConfig();
		config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
		config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
	}

	private static final SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
			SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
	};

	/**
	 * 根据指定配置，java对象转json字符串
	 * @param object
	 * @return
	 */
	public static String toJsonFeatures(Object object) {
		return JSON.toJSONString(object, config, features);
	}

	/**
	 * 根据默认配置，java对象转json字符串
	 * @param object
	 * @return
	 */
	public static String toJsonNoFeatures(Object object) {
		return JSON.toJSONString(object, config);
	}

	/**
	 * json字符串转java对象
	 * @param jsonStr
	 * @return
	 */
	public static Object toBean(String jsonStr) {
		return JSON.parse(jsonStr);
	}

	/**
	 * json字符串转java对象
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T toBean(String jsonStr, Class<T> clazz) {
		return JSON.parseObject(jsonStr, clazz);
	}

	/**
	 * json字符串转数组
	 * @param jsonStr
	 * @return
	 */
	public static <T> Object[] toArray(String jsonStr) {
		return toArray(jsonStr, null);
	}

	/**
	 * json字符串转数组
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(String jsonStr, Class<T> clazz) {
		List<T> parseArray = JSON.parseArray(jsonStr, clazz);
		T[] array = (T[]) Array.newInstance(clazz, parseArray.size()); // 利用反射机制
		for (int i = 0; i < parseArray.size(); i++) {
			T t = parseArray.get(i);
			array[i] = t;
		}
		return array;
	}

	/**
	 * json字符串转list
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> toList(String jsonStr, Class<T> clazz) {
		return JSON.parseArray(jsonStr, clazz);
	}

	/**
	 * json字符串转化为map
	 * @param s
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> toMap(String jsonStr) {
		return (Map<K, V>) JSON.parseObject(jsonStr);
	}

	/**
	 * 功能描述：把JSON数据转换成较为复杂的List<Map<String, Object>>
	 * @param jsonData JSON数据
	 * @return List<Map<String, Object>>
	 */
	public static List<Map<String, Object>> toListMap(String jsonData) {
		return JSON.parseObject(jsonData, new TypeReference<List<Map<String, Object>>>() {});
	}

	public static void main(String[] args) {
		String jsonStr = "{\"studentName\":\"lily\",\"studentAge\":12}";
		Object bean = FastJsonUtils.toBean(jsonStr);
		System.out.println(bean);

		String jsonMap = "{\"teacherName\":\"crystall\",\"teacherAge\":27,\"course\":{\"courseName\":\"english\",\"code\":1270}}";
		Map<Object, Object> map = FastJsonUtils.toMap(jsonMap);
		System.out.println(map);

		String intJson = "[\"studentName\",\"studentAge\"]";
		String[] array = FastJsonUtils.toArray(intJson, String.class);
		System.out.println(array[0] + "," + array[1]);

		String jsonList = "[{\"courseName\":\"english\",\"code\":1270},{\"studentName\":\"lily\",\"studentAge\":12},{\"studentName\":\"lucy\",\"studentAge\":15}]";
		List<Map<String, Object>> listMap = FastJsonUtils.toListMap(jsonList);
		System.out.println(listMap);

	}
}
