package com.example.easyweather.service;

import com.example.easyweather.util.HttpUtil;
import com.example.easyweather.util.HttpcallbackListener;
import com.example.easyweather.util.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

public class AutoUpdateService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				updateWeather();
			}
		}).start();
		AlarmManager mamager = (AlarmManager)getSystemService(ALARM_SERVICE);
		int anHour = 8 * 60 * 60 * 1000;
		long tirggerAtTime = SystemClock.elapsedRealtime() + anHour;
		Intent i = new Intent(this,autoUpdateReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		mamager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, tirggerAtTime, pi);
		return super.onStartCommand(intent, flags, startId);
	}

	private void updateWeather() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String weatherCode = prefs.getString("weather_code", "");
		String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
		HttpUtil.sendHttpRequest(address, new HttpcallbackListener() {
			
			@Override
			public void onFinish(String reponse) {
				Utility.handleWeatherResponse(AutoUpdateService.this, reponse);
			}
			
			@Override
			public void onError(Exception e) {
				e.printStackTrace();
			}
		});
	}

}
