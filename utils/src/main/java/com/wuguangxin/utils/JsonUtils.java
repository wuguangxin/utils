package com.wuguangxin.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
		try {
			JSONTokener jsonParser = new JSONTokener(inReader);
			return (JSONObject) jsonParser.nextValue();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 把标准json字符串转换为json对象JSONObject
	 * @param stringJson json字符串
	 * @return JSONObject
	 */
	public static JSONObject toJsonObject(String stringJson){
		try {
			return new JSONObject(stringJson);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把标准json字符串转换为json对象JSONObject
	 * @param stringJsonArray jsonArray字符串
	 * @return JSONObject
	 */
	public static JSONArray toJsonArray(String stringJsonArray){
		try {
			return new JSONArray(stringJsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把标准json字符串转换为json对象JSONObject
	 * @param list List<T>
	 * @return String
	 */
	public static <T> String toJsonArray(List<T> list){
		try {
			return mGson.toJson(list);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析JSONObject，从jsonObject中根据key获取对应的value（JSONObject），如果数据jsonObject或key为空，则返回为 new JSONObject();
	 * @param jsonObject JSONObject对象数据源
	 * @param key 关键字
	 * @return
	 */
	public static JSONObject getJSONObject(JSONObject jsonObject, String key){
		try {
			return jsonObject.optJSONObject(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析JSONArray，从jsonObject中根据key获取对应的value（JSONArray），如果数据jsonObject或key为空，则返回为 new JSONArray();
	 * @param jsonObject JSONObject对象数据源
	 * @param key 关键字
	 * @return
	 */
	public static JSONArray getJSONArray(JSONObject jsonObject, String key){
		try {
			return jsonObject.optJSONArray(key);
		} catch (Exception e) {
			e.printStackTrace();
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
		try {
			return toBean(jsonObject.toString(), cla);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将bean对象解析为json字符串对象
	 * @param javaBean javBean
	 * @return
	 */
	public static <T> String toString(T javaBean){
		try {
			return mGson.toJson(javaBean);
		} catch (Exception e) {
			e.printStackTrace();
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
		try {
			return mGson.fromJson(jsonString, cla);
		} catch (Exception e) {
			e.printStackTrace();
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
		try {
			return mGson.fromJson(jsonArray.toString(), type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json对象解析为List
	 * @param jsonArrayString
	 * @param type 泛型类型：参考 Type type = new TypeToken<List<Object>>(){}.getType();
	 * @return
	 */
	public static <T> List<T> toList(String jsonArrayString, java.lang.reflect.Type type){
		try {
			return mGson.fromJson(jsonArrayString, type);
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
			return mGson.fromJson(json, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
