package com.mudi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {

	private static Gson gson = null;
	static {
		if (gson == null) {
			gson = new Gson();
		}
	}

	private GsonUtil() {
	}

	/**
	 * 将object对象转成json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJsonString(Object object) {
		String gsonString = null;
		if (gson != null) {
			gsonString = gson.toJson(object);
		}
		return gsonString;
	}

	/**
	 * 将gsonString转成泛型bean
	 * 
	 * @param gsonString
	 * @param cls
	 * @return
	 */
	public static <T> T toBean(String gsonString, Class<T> cls) {
		T t = null;
		if (gson != null) {
			t = gson.fromJson(gsonString, cls);
		}
		return t;
	}

	/**
	 * 转成list
	 * 解决泛型问题
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> List<T> toList(String json, Class<T> cls) {
		Gson gson = new Gson();
		List<T> list = new ArrayList<T>();
		JsonArray array = new JsonParser().parse(json).getAsJsonArray();
		for (final JsonElement elem : array) {
			list.add(gson.fromJson(elem, cls));
		}
		return list;
	}

	/**
	 * 转成list<Map>
	 * 
	 * @param gsonString
	 * @return
	 */
	public static <T> List<Map<String, T>> toListMaps(String gsonString) {
		List<Map<String, T>> list = null;
		if (gson != null) {
			list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
			}.getType());
		}
		return list;
	}

	/**
	 * 转成map
	 * 
	 * @param gsonString
	 * @return
	 */
	public static <T> Map<String, T> toMap(String gsonString) {
		Map<String, T> map = null;
		if (gson != null) {
			map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
			}.getType());
		}
		return map;
	}

}