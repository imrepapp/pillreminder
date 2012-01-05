package hu.creapp.android.PillReminder.lib;

import android.os.SystemClock;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.Time;
import android.preference.PreferenceManager;
import android.net.Uri;

import hu.creapp.android.PillReminder.R;
import hu.creapp.android.preference.MultiSelectListPreference;

public class UserConfig {
	private boolean notifyEnable = true;
	private int[] notifyDays = {0,0,0,0,0,0,0};
	private Time nextNotifyTime = new Time();
	private Context context;
	private Uri ringtone;
	private int remainAmount;
	private int useAmount;
	private SharedPreferences settings;

	public UserConfig(Context context) {
		this.context = context;
		this.settings = PreferenceManager.getDefaultSharedPreferences(this.context);
		this.parseSettings();
	}


	public void setNotifyEnable(){
		this.notifyEnable = this.settings.getBoolean("alarm_enable", true);
	}
	public void setRemainAmount(){
		this.remainAmount = Integer.valueOf(this.settings.getString("remain_amount", "0"));
		if ( this.remainAmount <= 0 ){
			this.notifyEnable = false;
		} else {
			this.setNotifyEnable();
		}
	}
	public void setUseAmount(){
		this.useAmount = Integer.parseInt(this.settings.getString("use_amount", "0"));
		if ( this.useAmount <= 0 ){
			this.notifyEnable = false;
		} else {
			this.setNotifyEnable();
		}
	}
	public void setAlarmTime(){
		int weekday = -1;
		int hour;
		int min;
		String tmpStr;
		String[] days;
		Time currentTime = new Time();

		currentTime.setToNow();

		tmpStr = this.settings.getString("alarm_time", "8:00");
		hour = Integer.valueOf(tmpStr.split(":")[0]);
		min = Integer.valueOf(tmpStr.split(":")[1]);

		days = MultiSelectListPreference.parseStoredValue(this.settings.getString("alarm_days", ""));

		for(int i = 0; i < this.notifyDays.length ; i++){ this.notifyDays[i] = 0; }
		if(days != null && days.length>0){
			for(String value : days){ this.notifyDays[Integer.parseInt(value)] = 1; }

			int i = currentTime.weekDay;
			while( i != (currentTime.weekDay-1)){
				if( this.notifyDays[i] == 1 ){
					weekday = i;
					break;
				}
				if((i+1) == this.notifyDays.length){
					i = 0;
				} else {
					i++;
				}
			}
		}
		if (weekday < 0) {
			this.notifyEnable = false;
		}

		this.nextNotifyTime.set(0, min, hour, (currentTime.monthDay + (weekday - currentTime.weekDay)), currentTime.month, currentTime.year);
	}

	public void setAlarmRingtone(){
		String tmpStr;
		tmpStr = this.settings.getString("alarm_ringtone", "");
		if (tmpStr.length() > 0){
			this.ringtone = Uri.parse(tmpStr);
		}
	}

	private void parseSettings(){
		this.setNotifyEnable();
		this.setRemainAmount();
		this.setUseAmount();
		this.setAlarmTime();
		this.setAlarmRingtone();
	}

	public boolean isEnable(){
		return this.notifyEnable;
	}
	public long getNextNotify(){
		/*
		long tmpTime;
		tmpTime = this.nextNotifyTime.toMillis(true) - currentTime.toMillis(true);
		if (tmpTime < 0 ){ tmpTime += 24 * 60 * 60 * 1000; }
		return (SystemClock.elapsedRealtime()+tmpTime);
		*/
		Time currentTime = new Time();
		currentTime.setToNow();
		if(Time.compare(this.nextNotifyTime, currentTime) <= 0 ){
			this.nextNotifyTime.set((this.nextNotifyTime.toMillis(true)+24*60*60*1000));
		}
		return this.nextNotifyTime.toMillis(true);
	}
	public String getRemainAmountStr(){
		return Integer.toString(this.remainAmount);
	}
	public String getTimeStr(){
		return this.nextNotifyTime.format("%H:%M");
	}
	public String getDaysStr(String delimiter){
		System.out.println("ixcDEBUG: getdaystr ->"+this.notifyEnable);

		String[] daysStr = this.context.getResources().getStringArray(R.array.alarm_days);
		int[] daysIdx = this.context.getResources().getIntArray(R.array.alarm_day_value_pair);
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<this.notifyDays.length; i++){
			if (this.notifyDays[i] == 1) {
				sb.append(daysStr[daysIdx[i]]);
				sb.append(delimiter);
			}
		}

		int length = sb.length();
		if(length > 0) {
			sb.setLength(length - delimiter.length());
		}
		return sb.toString();
	}
	public Uri getRingtoneUri() {
		return this.ringtone;
	}
	public boolean onThisDay(){
		Time currentTime = new Time();
		currentTime.setToNow();
		return (this.notifyDays[currentTime.weekDay] != 0);
	}
	public void takePill(){
		this.remainAmount -= useAmount;
		if (this.remainAmount < 0)
			this.remainAmount = 0;
		this.settings.edit().putString("remain_amount", ""+this.remainAmount).commit();
	}
	public void addPill(int dose){
		this.remainAmount += dose;
		if (this.remainAmount > 999999999)
			this.remainAmount = 999999999;
		this.settings.edit().putString("remain_amount", ""+this.remainAmount).commit();
	}
}
