package com.wuguangxin.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtils {

	public static Gson mGson = new GsonBuilder().serializeNulls().create();
	/**
	 * 字符转到json对象
	 * @param inReader json字符串
	 * @return JSONObject
	 */
	public static JSONObject parseStr2Json(String inReader){
		JSONTokener jsonParser = new JSONTokener(inReader);
		JSONObject jobject;
		try {
			jobject = (JSONObject) jsonParser.nextValue();
			return jobject;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new JSONObject();
	}
	
	/**
	 * 把标准json字符串转换为json对象JSONObject
	 * @param stringJson json字符串
	 * @return JSONObject
	 */
	public static JSONObject toJsonObject(String stringJson){
		try {
			return new JSONObject(stringJson);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return new JSONObject();
	}

	/**
	 * 把标准json字符串转换为json对象JSONObject
	 * @param stringJsonArray jsonArray字符串
	 * @return JSONObject
	 */
	public static JSONArray toJsonArray(String stringJsonArray){
		try {
			return new JSONArray(stringJsonArray);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return new JSONArray();
	}

	/**
	 * 把标准json字符串转换为json对象JSONObject
	 * @param list List
	 * @return String
	 */
	public static String toJsonArray(List list){
		try {
			return mGson.toJson(list);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return new JSONArray().toString();
	}

	/**
	 * 解析JSONObject，从jsonObject中根据key获取对应的value（JSONObject），如果数据jsonObject或key为空，则返回为 new JSONObject();
	 * @param jsonObject JSONObject对象数据源
	 * @param key 关键字
	 * @return
	 */
	public static JSONObject getJSONObject(JSONObject jsonObject, String key){
		if (jsonObject != null && !TextUtils.isEmpty(key)) {
			try {
				JSONObject json = jsonObject.optJSONObject(key);
				if (json != null) {
					return json;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new JSONObject();
	}

	/**
	 * 解析JSONArray，从jsonObject中根据key获取对应的value（JSONArray），如果数据jsonObject或key为空，则返回为 new JSONArray();
	 * @param jsonObject JSONObject对象数据源
	 * @param key 关键字
	 * @return
	 */
	public static JSONArray getJSONArray(JSONObject jsonObject, String key){
		if (jsonObject != null && !TextUtils.isEmpty(key)) {
			try {
				JSONArray jsonArray = jsonObject.optJSONArray(key);
				if (jsonArray != null) {
					return jsonArray;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new JSONArray();
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
