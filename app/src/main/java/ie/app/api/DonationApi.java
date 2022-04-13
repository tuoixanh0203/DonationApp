package ie.app.api;

import android.util.Log;

import java.lang.reflect.Type;
import java.util.List;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ie.app.models.Donation;

public class DonationApi {

	public static List<Donation> getAll(String call) {
		String json = Rest.get(call);
		Log.v("Donation", call);
		Log.v("Donate", "JSON RESULT: " + json);
		Type collectionType = new TypeToken<List<Donation>>(){}.getType();
		
		return new Gson().fromJson(json, collectionType);
	}
	public static List<Donation> get(String call,String id) {
		String filePath = call + "?_id=" + id;
		Log.v("donate", "get by id with RESULT : " + filePath);
		String json = Rest.get(filePath);

		Type objType = new TypeToken<List<Donation>>(){}.getType();

		return new Gson().fromJson(json, objType);
	}

	public static String deleteAll(String call) {
		return Rest.delete(call);
	}

	public static String delete(String call, String id) {
		String filePath = call + ".php?_id=" + id;
		Log.v("delete", Rest.delete(filePath));
		return Rest.delete(filePath);
	}

	public static String insert(String call, Donation donation) {
		Type objType = new TypeToken<Donation>(){}.getType();
		String json = new Gson().toJson(donation, objType);
  
		return Rest.post(call, json);
	}
}
