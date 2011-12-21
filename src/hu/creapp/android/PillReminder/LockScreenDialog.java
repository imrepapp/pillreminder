package hu.creapp.android.PillReminder;

import android.app.Activity;
import android.os.Bundle;

import android.app.KeyguardManager;
import android.os.PowerManager;
import android.media.MediaPlayer;
import android.media.AudioManager;

public class LockScreenDialog extends Activity {
	private KeyguardManager km;
	private KeyguardManager.KeyguardLock kl;
	private MediaPlayer mMediaPlayer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		kl = km.newKeyguardLock("PillReminder");
		kl.disableKeyguard();

		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		PowerManager.WakeLock wl=pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, "PillReminder");
		wl.acquire();

		try{
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDataSource(getApplicationContext(), PillReminderApp.usercfg.getRingtoneUri());
			mMediaPlayer.prepare();
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.start();
		}catch(Exception e) {}

	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		mMediaPlayer.stop();
		kl.reenableKeyguard();
	}
}
