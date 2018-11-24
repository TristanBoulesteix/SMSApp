package message.smsapp.message;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import message.smsapp.R;
import message.smsapp.layouts_manager.MessagesAdapter;

public class ConversationManager {
	private static final int REQUEST_PERMISSION_KEY = 1;
	
	private static ConversationManager instance;
	
	private ArrayList<String> listOfComponents, listOfContents;
	private ArrayList<Integer> listOfIcons;
	private ArrayList<HashMap<String, String>> smsList;
	
	private ConversationManager(Context context, ListView list) {
		this.listOfComponents = new ArrayList<>();
		this.listOfContents = new ArrayList<>();
		this.listOfIcons = new ArrayList<>();
		this.smsList = new ArrayList<>();
		
		this.smsList.clear();
		
		this.addConversation("Expéditeur 1", "test 1", R.drawable.ic_menu_send);
		this.addConversation("Expéditeur 2", "test 2", R.drawable.ic_menu_share);
		
		ListAdapter adapter = new MessagesAdapter(context, listOfComponents, listOfContents, listOfIcons);
		list.setAdapter(adapter);
	}
	
	public static ConversationManager getInstance(Context context, ListView list) {
		if (ConversationManager.instance == null) {
			return ConversationManager.instance = new ConversationManager(context, list);
		} else {
			return ConversationManager.instance;
		}
	}
	
	private void addConversation(String title, String content, int id) {
		this.listOfComponents.add(title);
		this.listOfContents.add(content);
		this.listOfIcons.add(id);
	}
}
