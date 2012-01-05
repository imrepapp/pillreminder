package hu.creapp.android.PillReminder;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.EditTextPreference;
import android.text.method.DigitsKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		EditTextPreference tmpEditBox;
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
		settings.edit().putBoolean("first_run", false).commit();
	}

	@Override
	public void onResume(){
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onPause(){
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onStop(){
		super.onStop();
		Intent i = new Intent();
		i.setAction("hu.creapp.android.PillReminder.action.ALARM_RESTART");
		getApplicationContext().sendBroadcast(i);
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,String key){
		if (key.equals("alarm_enable"))
			PillReminderApp.usercfg.setNotifyEnable();
		if (key.equals("remain_amount"))
			PillReminderApp.usercfg.setRemainAmount();
		if (key.equals("use_amount"))
			PillReminderApp.usercfg.setUseAmount();
		if (key.equals("alarm_time"))
			PillReminderApp.usercfg.setAlarmTime();
		if (key.equals("alarm_days"))
			PillReminderApp.usercfg.setAlarmTime();
		if (key.equals("alarm_ringtone"))
			PillReminderApp.usercfg.setAlarmRingtone();
	}
}
