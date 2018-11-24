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

    public MessagesAdapter(Context context, ArrayList<String> titles, ArrayList<Integer> icons){
        super(context,R.layout.personalized_list ,titles);
        this.icons = icons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.personalized_list, parent, false);

        TextView textView = rowView.findViewById(R.id.title);
        ImageView imageView = rowView.findViewById(R.id.iconChat);

        textView.setText(getItem(position));

        if(convertView == null )
            imageView.setImageResource(icons.get(position));
        else
            rowView = convertView;

        return rowView;
    }
}
