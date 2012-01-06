package hu.creapp.android.PillReminder;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.view.View;
import android.content.Intent;
import android.content.Context;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;

import hu.creapp.android.PillReminder.LockScreenDialog;

public class PillDialog extends LockScreenDialog {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Button button;
		super.onCreate(savedInstanceState);

		AlertDialog.Builder alert = new AlertDialog.Builder(PillDialog.this);
		alert.setTitle(R.string.title_pill_dialog);
		alert.setMessage(R.string.text_pills_out);
		alert.setPositiveButton(R.string.button_pill_yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				PillReminderApp.usercfg.takePill();
				if (PillReminderApp.usercfg.isOutPill()){
					AlertDialog.Builder ralert = new AlertDialog.Builder(PillDialog.this);
					ralert.setTitle(R.string.title_pill_dialog);
					ralert.setMessage(R.string.text_pills_out);
					ralert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							PillDialog.this.finish();
						}
					});
					ralert.create().show();
				}
				else { PillDialog.this.finish(); }
			}
		});
		alert.setNegativeButton(R.string.button_pill_no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Context context = getBaseContext();
				PendingIntent mAlarmSender = PendingIntent.getBroadcast(context, 0, new Intent(context, SnoozeReceiver.class), PendingIntent.FLAG_ONE_SHOT);

				AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
				try {
					am.cancel(mAlarmSender);
				} catch (Exception e) {}
				am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+(10*60*1000), mAlarmSender);

				PillDialog.this.finish();
			}
		});
		alert.create().show();
	}

	@Override
	public void onResume(){
		super.onResume();
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
}
