package message.smsapp.message;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import message.smsapp.R;
import message.smsapp.layouts_manager.MessagesAdapter;

public class ConversationManager {
	private final Context context;
	
	private ArrayList<String> listOfComponents, listOfContents;
	private ArrayList<Integer> listOfIcons;
	private ContactManager contactManager;
	private ArrayList<Conversation> conversations;
	
	public ConversationManager(Context context, ListView list, int REQUEST_PERMISSION_KEY) {
		this.context = context;
		this.listOfComponents = new ArrayList<>();
		this.listOfContents = new ArrayList<>();
		this.listOfIcons = new ArrayList<>();
		this.conversations = new ArrayList<>();
		this.contactManager = new ContactManager(context, REQUEST_PERMISSION_KEY);
		
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
		
		this.refreshSMS();
		
		ListAdapter adapter = new MessagesAdapter(this.context, listOfComponents, listOfContents,
				listOfIcons);
		list.setAdapter(adapter);
	}
	
	private void refreshSMS() {
		this.clearDatas();
		
		ContentResolver contentResolver = this.context.getContentResolver();
		final String[] projection = new String[]{"*"};
		Uri uri = Uri.parse("content://sms/");
		Cursor cursor = contentResolver.query(uri, projection, null, null, null);
		
		int indexBody = cursor.getColumnIndex("body");
		int indexAddress = cursor.getColumnIndex("address");
		if (indexBody < 0 || !cursor.moveToFirst()) return;
		
		do {
			this.addConversation(this.contactManager.getContactNameWithNumber(cursor.getString
					(indexAddress)), getPreviewBody(cursor.getString(indexBody)), R
					.drawable
					.ic_menu_send);
		} while (cursor.moveToNext());
		
		cursor.close();
		
		this.conversations = Conversation.generateConversation(listOfComponents, listOfContents);
		
		this.generateListWithConversations();
	}
	
	private void generateListWithConversations(){
		ArrayList<String> temp = new ArrayList<>();
		
		for (int i = 0; i < this.conversations.size(); i++) {
			temp.add(this.conversations.get(i).getPhoneNumber());
		}
		
		this.listOfComponents = temp;
		
		temp = new ArrayList<>();
		
		for (int i = 0; i < this.conversations.size(); i++) {
			temp.add(this.conversations.get(i).getMessages().get(0));
		}
		
		this.listOfContents = temp;
		
		this.addConversation(Integer.toString(this.conversations.size()), "test", R.drawable.ic_menu_camera);
	}
	
	@NonNull
	private String getPreviewBody(String body) {
		String preview = body.substring(0, Math.min(body.length(), 35));
		preview += (body.length() > preview.length()) ? "..." : "";
		
		return preview;
	}
	
	private void addConversation(String title, String content, int id) {
		this.listOfComponents.add(title);
		this.listOfContents.add(content);
		this.listOfIcons.add(id);
	}
	
	private void clearDatas() {
		this.listOfComponents.clear();
		this.listOfContents.clear();
		this.listOfIcons.clear();
		this.conversations.clear();
	}
}
