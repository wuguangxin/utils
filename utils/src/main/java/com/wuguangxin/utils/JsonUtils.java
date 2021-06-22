package com.wuguangxin.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtils {

	public static Gson mGson = new GsonBuilder().serializeNulls().create();

	/**
	 * 把List转换为json字符串
	 * @param list List
	 * @return String
	 */
	public static <T> String toJsonString(List<T> list){
		try {
			return mGson.toJson(list);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json对象解析为javaBean对象
	 * @param jsonObject JSONObject对象数据源
	 * @param cla javaBean对象的class
	 * @return
	 */
	public static <T> T toBean(JSONObject jsonObject, Class<T> cla){
		if (cla != null) {
			try {
				if (jsonObject == null) jsonObject = new JSONObject();
				return toBean(jsonObject.toString(), cla);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将bean对象解析为json字符串对象
	 * @param bean javBean
	 * @return
	 */
	public static <T> String toString(T bean){
		if (bean != null) {
			try {
				return mGson.toJson(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将json字符串解析为javaBean对象
	 * @param jsonString json字符串
	 * @param cla javaBean对象的class
	 * @return
	 */
	public static <T> T toBean(String jsonString, Class<T> cla){
		if (cla != null) {
			try {
				if (TextUtils.isEmpty(jsonString)) jsonString = new JSONObject().toString();
				T t = mGson.fromJson(jsonString, cla);
				if (t != null) return t;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将json对象解析为List
	 * @param jsonArray JSONArray对象数据源
	 * @param type 泛型类型：参考 Type type = new TypeToken<List<Object>>(){}.getType();
	 * @return
	 */
	public static <T> List<T> toList(JSONArray jsonArray, java.lang.reflect.Type type){
		List<T> list = new ArrayList<>();
		try {
			if (jsonArray != null) {
				list = mGson.fromJson(jsonArray.toString(), type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 将json对象解析为List
	 * @param jsonArrayString
	 * @param type 泛型类型：参考 Type type = new TypeToken<List<Object>>(){}.getType();
	 * @return
	 */
	public static <T> List<T> toList(String jsonArrayString, java.lang.reflect.Type type){
		try {
			List<T> list = mGson.fromJson(jsonArrayString, type);
			if (list != null) return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json对象解析为List
	 * @param json
	 * @param type 泛型类型：参考 Type type = new TypeToken<Map<String, T>>(){}.getType();
	 * @return
	 */
	public static <T> Map<String, T> toMap(String json, java.lang.reflect.Type type){
		try {
			Map<String, T> map = mGson.fromJson(json, type);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
