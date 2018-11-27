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
import android.util.Log;

import java.util.ArrayList;

import message.smsapp.activities.MainActivity;

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
		Cursor cur = resolver.query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);
		
		if ((cur != null ? cur.getCount() : 0) > 0) {
			while (cur != null && cur.moveToNext()) {
				String id = cur.getString(
						cur.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur.getString(cur.getColumnIndex(
						ContactsContract.Contacts.DISPLAY_NAME));
				
				if (cur.getInt(cur.getColumnIndex(
						ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
					Cursor pCur = resolver.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
							new String[]{id}, null);
					while (pCur.moveToNext()) {
						String phoneNo = pCur.getString(pCur.getColumnIndex(
								ContactsContract.CommonDataKinds.Phone.NUMBER));
						Log.i(MainActivity.class.getSimpleName(), "Name: " + name);
						Log.i(MainActivity.class.getSimpleName(), "Phone Number: " + phoneNo);
					}
					pCur.close();
				}
			}
		}
		if(cur!=null){
			cur.close();
		}
	}
	
	private void addContact(int id, String name, String phoneNumber) {
		this.ids.add(id);
		this.names.add(name);
		this.phoneNumbers.add(phoneNumber);
	}
}
