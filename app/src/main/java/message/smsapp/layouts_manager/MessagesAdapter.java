package message.smsapp.layouts_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import message.smsapp.R;

public class MessagesAdapter extends ArrayAdapter<String> {
	private ArrayList<Integer> icons;
	private ArrayList<String> content;
	
	public MessagesAdapter(Context context, ArrayList<String> titles, ArrayList<String> content,
	                       ArrayList<Integer> icons) {
		super(context, R.layout.personalized_list, titles);
		this.icons = icons;
		this.content = content;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)
				getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.personalized_list, parent, false);
		
		TextView textView = rowView.findViewById(R.id.title);
		TextView contentText = rowView.findViewById(R.id.content);
		ImageView imageView = rowView.findViewById(R.id.iconChat);
		
		textView.setText(getItem(position));
		contentText.setText(content.get(position));
		
		if (convertView == null)
			imageView.setImageResource(icons.get(position));
		else
			rowView = convertView;
		
		return rowView;
	}
}
