package message.smsapp.layouts_manager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import message.smsapp.R
import java.util.*

class MessagesAdapter(context: Context, titles: MutableList<String?>, private val content: MutableList<String>, private val icons: ArrayList<Int>) : ArrayAdapter<String?>(context, R.layout.personalized_list, titles) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        with((context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.personalized_list, parent, false)) {
            val textView = this.findViewById<TextView>(R.id.title)
            val contentText = this.findViewById<TextView>(R.id.content)
            val imageView = this.findViewById<ImageView>(R.id.iconChat)
            textView.text = getItem(position)
            contentText.text = content[position]

           /* if (convertView == null)
                imageView.setImageResource(icons[position])*/

            return convertView ?: this
        }
    }
}