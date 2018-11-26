package message.smsapp.message;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class ContactManager {
	private static ContactManager instance;
	
	private Context context;
	
	private ContactManager(Context context, int REQUEST_PERMISSION_KEY) {
		this.context = context;
		
		if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) !=
				PackageManager.PERMISSION_GRANTED) {
			if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
					Manifest.permission.READ_CONTACTS)) {
				
			} else {
				ActivityCompat.requestPermissions((Activity) context,
						new String[]{Manifest.permission.READ_CONTACTS},
						REQUEST_PERMISSION_KEY);
			}
		}
	}
	
	public static ContactManager getInstance(Context context, int REQUEST_PERMISSION_KEY) {
		if (ContactManager.instance == null) {
			return ContactManager.instance = new ContactManager(context, REQUEST_PERMISSION_KEY);
		} else {
			return ContactManager.instance;
		}
	}
	
	private void getContactList() {
		ContentResolver resolver = context.getContentResolver();
		final String[] projection = new String[]{"*"};
		Cursor cursor = resolver.query(ContactsContract.Data.CONTENT_URI, projection,
				null, null, null);
		
		if (cursor != null && cursor.getCount() > 0) {
		
		}
	}
}
