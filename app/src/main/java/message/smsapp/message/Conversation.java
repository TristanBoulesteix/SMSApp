package message.smsapp.message;

import android.util.Log;

import java.util.ArrayList;

import message.smsapp.activities.MainActivity;

public class Conversation {
	private String phoneNumber;
	private ArrayList<String> messages;
	
	public Conversation(String phoneNumber, ArrayList<String> messages) {
		this.phoneNumber = phoneNumber;
		this.messages = messages;
	}
	
	public static ArrayList<Conversation> generateConversation(ArrayList<String> adress,
	                                                           ArrayList<String> smsContent) {
		ArrayList<Conversation> listOfConversations = new ArrayList<>();
		
		String currentNumber;
		ArrayList<String> messages;
		
		// Add first conversation
		currentNumber = adress.get(0);
		messages = new ArrayList<>();
		
		for (int i = 0; i < smsContent.size(); i++) {
			if (adress.get(i).equals(currentNumber)) {
				messages.add(smsContent.get(i));
			}
		}
		
		listOfConversations.add(new Conversation(currentNumber, messages));
		
		// Add other conversations
		while (!currentNumber.equals("")) {
			currentNumber = Conversation.selectANewConversation(listOfConversations, adress);
			
			for (int i = 0; i < smsContent.size(); i++) {
				if (adress.get(i).equals(currentNumber)) {
					messages.add(smsContent.get(i));
				}
			}
			
			listOfConversations.add(new Conversation(currentNumber, messages));
		}
		
		return listOfConversations;
	}
	
	private static String selectANewConversation(ArrayList<Conversation> list, ArrayList<String>
			adress) {
		ArrayList<String> phoneUsed = new ArrayList<>();
		
		for (int i = 0; i < list.size(); i++) {
			phoneUsed.add(list.get(i).getPhoneNumber());
		}
		
		for (int i = 0; i < adress.size(); i++) {
			if (!phoneUsed.contains(adress.get(i))) {
				return adress.get(i);
			}
		}
		
		return "";
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public ArrayList<String> getMessages() {
		return messages;
	}
}
