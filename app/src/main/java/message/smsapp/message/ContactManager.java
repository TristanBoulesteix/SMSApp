package message.smsapp.message;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class ContactManager {
	public ContactManager(Context context, int REQUEST_PERMISSION_KEY){
		if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) !=
				PackageManager.PERMISSION_GRANTED) {
			if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
					Manifest.permission.READ_SMS)) {
				
			} else {
				ActivityCompat.requestPermissions((Activity) context,
						new String[]{Manifest.permission.READ_SMS},
						REQUEST_PERMISSION_KEY);
			}
		}
	}
}
