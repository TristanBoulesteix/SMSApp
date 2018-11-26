package message.smsapp.message;

import android.Manifest;
import android.app.Activity;
import android.arch.core.util.Function;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import message.smsapp.R;
import message.smsapp.layouts_manager.MessagesAdapter;

public class ConversationManager {
	private static ConversationManager instance;
	
	private final Context context;
	
	private ArrayList<String> listOfComponents, listOfContents;
	private ArrayList<Integer> listOfIcons;
	private ArrayList<String> smsList;
	
	private ConversationManager(Context context, ListView list, int REQUEST_PERMISSION_KEY) {
		this.context = context;
		this.listOfComponents = new ArrayList<>();
		this.listOfContents = new ArrayList<>();
		this.listOfIcons = new ArrayList<>();
		this.smsList = new ArrayList<>();
		
		if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) !=
				PackageManager.PERMISSION_GRANTED) {
			if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
					Manifest.permission.READ_SMS)) {
				
			} else {
				ActivityCompat.requestPermissions((Activity) context,
						new String[]{Manifest.permission.READ_SMS},
						REQUEST_PERMISSION_KEY);
			}
		} else {
		
		}
		
		this.refreshSMS();
		
		ListAdapter adapter = new MessagesAdapter(context, listOfComponents, listOfContents, listOfIcons);
		list.setAdapter(adapter);
	}
	
	public static ConversationManager getInstance(Context context, ListView list, int REQUEST_PERMISSION_KEY) {
		if (ConversationManager.instance == null) {
			return ConversationManager.instance = new ConversationManager(context, list, REQUEST_PERMISSION_KEY);
		} else {
			return ConversationManager.instance;
		}
	}
	
	private void refreshSMS(){
		ContentResolver contentResolver = context.getContentResolver();
		final String[] projection = new String[]{"*"};
		Uri uri = Uri.parse("content://mms-sms/conversations/");
		Cursor cursor = contentResolver.query(uri, projection, null, null, null);
		
		int indexBody = cursor.getColumnIndex("body");
		int indexAddress = cursor.getColumnIndex("address");
		if (indexBody < 0 || !cursor.moveToFirst()) return;
		
		do {
			this.addConversation(cursor.getString(indexAddress), "content", R.drawable
					.ic_menu_send);
		} while (cursor.moveToNext());
	}
	
	private void addConversation(String title, String content, int id) {
		this.listOfComponents.add(title);
		this.listOfContents.add(content);
		this.listOfIcons.add(id);
	}
}
