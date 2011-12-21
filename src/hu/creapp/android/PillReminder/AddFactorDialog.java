package hu.creapp.android.PillReminder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

public class AddFactorDialog extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_addpill);
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
	}

	public void addPill(View view) {
		EditText mEdit = (EditText)findViewById(R.id.dose);
		PillReminderApp.usercfg.addPill((int)Integer.parseInt(mEdit.getText().toString()));
		finish();
	}

	public void selfDestruct(View view) {
		finish();
	}
}
