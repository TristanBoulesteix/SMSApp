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

import java.util.ArrayList;

public class ContactManager {
	private Context context;
	private ArrayList<Integer> ids;
	private ArrayList<String> names;
	private ArrayList<String> phoneNumbers;
	
	public ContactManager(Context context, int REQUEST_PERMISSION_KEY) {
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
	
	private void getContactList() {
		ContentResolver resolver = this.context.getContentResolver();
		Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);
		
		if ((cursor != null ? cursor.getCount() : 0) > 0) {
			while (cursor != null && cursor.moveToNext()) {
				String id = cursor.getString(
						cursor.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cursor.getString(cursor.getColumnIndex(
						ContactsContract.Contacts.DISPLAY_NAME));
				
				if (cursor.getInt(cursor.getColumnIndex(
						ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
					Cursor cursorContact = resolver.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
							new String[]{id}, null);
					while (cursorContact.moveToNext()) {
						String phoneNumber = cursorContact.getString(cursorContact.getColumnIndex(
								ContactsContract.CommonDataKinds.Phone.NUMBER));
						
						this.addContact(Integer.parseInt(id), name, phoneNumber);
					}
					cursorContact.close();
				}
			}
		}
		if (cursor != null) {
			cursor.close();
		}
	}
	
	private void addContact(int id, String name, String phoneNumber) {
		this.ids.add(id);
		this.names.add(name);
		this.phoneNumbers.add(phoneNumber);
	}
	
	public String getContactNameWithNumber(String phoneNumber){
		if (this.phoneNumbers.contains(phoneNumber)) {
			return this.names.get(this.phoneNumbers.indexOf(phoneNumber));
		} else {
			return phoneNumber;
		}
	}
}
