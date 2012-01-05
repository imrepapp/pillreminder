package hu.creapp.android.PillReminder;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.view.View;
import android.content.Intent;
import android.content.Context;
import android.widget.Button;

import hu.creapp.android.PillReminder.LockScreenDialog;

public class PillDialog extends LockScreenDialog {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_pillremind);
	}

	@Override
	public void onResume(){
		Button button;
		super.onResume();

		button = (Button) findViewById(R.id.positive_button);
		button.setOnClickListener(new View.OnClickListener() { public void onClick(View v) {
			PillReminderApp.usercfg.takePill();
			PillDialog.this.finish();
		} });

		button = (Button) findViewById(R.id.negative_button);
		button.setOnClickListener(new View.OnClickListener() { public void onClick(View v) {
			Context context = getBaseContext();
			PendingIntent mAlarmSender = PendingIntent.getBroadcast(context, 0, new Intent(context, SnoozeReceiver.class), PendingIntent.FLAG_ONE_SHOT);

			AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
			try {
				am.cancel(mAlarmSender);
			} catch (Exception e) {}
			am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+(10*60*1000), mAlarmSender);

			PillDialog.this.finish();

		} });
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
}
