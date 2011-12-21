package hu.creapp.android.PillReminder;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends Activity{
	private void printFreshData(){
		TextView t;
		t = (TextView)findViewById(R.id.time_value);
		t.setText(PillReminderApp.usercfg.getTimeStr());
		t = (TextView)findViewById(R.id.days_value);
		t.setText(PillReminderApp.usercfg.getDaysStr(", "));
	}
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	@Override
	public void onResume(){
		super.onResume();
		printFreshData();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if ( PillReminderApp.activityMap.containsKey(item.getItemId()) ) {
			Intent myIntent = new Intent(this.getBaseContext(), PillReminderApp.activityMap.get(item.getItemId()));
			startActivity(myIntent);
			return true;
		}
		return false;
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
	}

}
