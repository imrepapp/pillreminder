package hu.creapp.android.PillReminder;

import android.app.PendingIntent;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;

public class AlarmRestartReceiver extends BroadcastReceiver {
	private PendingIntent mAlarmSender;

	@Override
	public void onReceive(Context context, Intent intent) {
		mAlarmSender = PendingIntent.getBroadcast(context, 0, new Intent(context, PillNotifyReceiver.class), 0);
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		try {
			am.cancel(mAlarmSender);
		} catch (Exception e) {}
		if (PillReminderApp.usercfg.isEnable() ) {
			am.setRepeating(AlarmManager.RTC_WAKEUP, PillReminderApp.usercfg.getNextNotify(), 24*60*60*1000, mAlarmSender);
		}
	}
}
