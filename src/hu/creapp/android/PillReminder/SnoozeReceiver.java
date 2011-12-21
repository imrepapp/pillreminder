package hu.creapp.android.PillReminder;

import android.os.SystemClock;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;

public class SnoozeReceiver extends BroadcastReceiver {
	private PendingIntent mAlarmSender;

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startActivity(new Intent(context, PillDialog.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	}
}
