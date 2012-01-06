package hu.creapp.android.PillReminder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.content.Intent;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class AddFactorDialog extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.dialog_addpill, null);
		final EditText input = (EditText) textEntryView.findViewById(R.id.dose);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.title_addpill_dialog);
		builder.setView(textEntryView);
		builder.setPositiveButton(R.string.button_addpill_yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				PillReminderApp.usercfg.addPill((int)Integer.parseInt(input.getText().toString()));
				finish();
			}
		});
		builder.setNegativeButton(R.string.button_addpill_no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				finish();
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}

	@Override
	public void onDestroy(){
		super.onDestroy();
	}

	public void addPill(View view) {
	}

	public void selfDestruct(View view) {
		finish();
	}
}
