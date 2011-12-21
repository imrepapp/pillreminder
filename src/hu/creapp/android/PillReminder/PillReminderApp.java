package hu.creapp.android.PillReminder;

import java.util.HashMap;

import android.app.Application;

import hu.creapp.android.PillReminder.R;
import hu.creapp.android.PillReminder.lib.UserConfig;

public class PillReminderApp extends Application {
	public static final HashMap<Integer, Class> activityMap = new HashMap<Integer, Class>();
	public static UserConfig usercfg;
	public void onCreate() {
		activityMap.put(R.id.menu_settings, SettingsActivity.class);
		activityMap.put(R.id.menu_addfactor, AddFactorDialog.class);

		usercfg = new UserConfig(this);
	}
}
