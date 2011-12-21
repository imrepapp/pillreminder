package hu.creapp.android.PillReminder;

import android.os.SystemClock;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;

public class PillNotifyReceiver extends BroadcastReceiver {
	private PendingIntent mAlarmSender;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (PillReminderApp.usercfg.onThisDay()){
			context.startActivity(new Intent(context, PillDialog.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		}
	}
}
