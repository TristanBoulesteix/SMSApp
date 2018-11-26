package message.smsapp.message;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

public class ContactManager {
	private static ContactManager instance;
	
	private Context context;
	private ArrayList<Integer> ids;
	private ArrayList<String> names;
	private ArrayList<String> phoneNumbers;
	
	private ContactManager(Context context, int REQUEST_PERMISSION_KEY) {
		this.context = context;
		this.ids = new ArrayList<>();
		this.names = new ArrayList<>();
		this.phoneNumbers = new ArrayList<>();
		
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
		
		this.getContactList();
	}
	
	public static ContactManager getInstance(Context context, int REQUEST_PERMISSION_KEY) {
		if (ContactManager.instance == null) {
			return ContactManager.instance = new ContactManager(context, REQUEST_PERMISSION_KEY);
		} else {
			return ContactManager.instance;
		}
	}
	
	private void getContactList() {
		Cursor contactCursor = this.context.getContentResolver().query(ContactsContract.Contacts
						.CONTENT_URI,
				new String[]{ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
		
		do {
			this.addContact(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone
					._ID), Integer.toString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone
					.DISPLAY_NAME)), Integer.toString(contactCursor.getColumnIndex(ContactsContract
					.CommonDataKinds.Phone
					.NUMBER)));
		} while (contactCursor.moveToNext());
	}
	
	private void addContact(int id, String name, String phoneNumber) {
		this.ids.add(id);
		this.names.add(name);
		this.phoneNumbers.add(phoneNumber);
	}
}
