package message.smsapp.message;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import message.smsapp.R;
import message.smsapp.layouts_manager.MessagesAdapter;

public class ConversationManager {
    private static ConversationManager instance;

    ArrayList<String> listOfComponents, listOfContents;
    ArrayList<Integer> listOfIcons;

    private ConversationManager(Context context, ListView list){
        this.listOfComponents = new ArrayList<>();
        this.listOfContents = new ArrayList<>();
        this.listOfIcons = new ArrayList<>();

        listOfComponents.add("Expéditeur 1");
        listOfComponents.add("Expéditeur 2");

        listOfContents.add("test1");
        listOfContents.add("test2");

        listOfIcons.add(R.drawable.ic_menu_send);
        listOfIcons.add(R.drawable.ic_menu_share);

        ListAdapter adapter = new MessagesAdapter(context, listOfComponents, listOfContents, listOfIcons);
        list.setAdapter(adapter);
    }

    public static ConversationManager getInstance(Context context, ListView list){
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
