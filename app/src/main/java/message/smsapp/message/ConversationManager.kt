package message.smsapp.message

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.ListAdapter
import android.widget.ListView
import message.smsapp.R
import message.smsapp.layouts_manager.MessagesAdapter
import java.util.*

class ConversationManager(private val context: Context, list: ListView, REQUEST_PERMISSION_KEY: Int) {
    private var listOfComponents: ArrayList<String>
    private var listOfContents: ArrayList<String>
    private val listOfIcons: ArrayList<Int>
    private val contactManager: ContactManager
    private var conversations: ArrayList<Conversation>

    private fun refreshSMS() {
        clearDatas()
        context.contentResolver.query(Uri.parse("content://sms/inbox"), arrayOf("*"), null, null, null)?.use {
            val indexBody = it.getColumnIndex("body")
            val indexAddress = it.getColumnIndex("address")
            if (indexBody < 0 || !it.moveToFirst()) return
            do {
                addConversation(contactManager.getContactNameWithNumber(it.getString(indexAddress)), getPreviewBody(it.getString(indexBody)), R.drawable.ic_menu_send)
            } while (it.isAfterLast)
        }
        conversations = Conversation.generateConversation(listOfComponents, listOfContents)
        generateListWithConversations()
    }

    private fun generateListWithConversations() {
        var temp = ArrayList<String>()
        for (i in conversations.indices) {
            temp.add(conversations[i].phoneNumber)
        }
        listOfComponents = temp
        temp = ArrayList()
        for (i in conversations.indices) {
            temp.add(conversations[i].messages[0])
        }
        listOfContents = temp
        addConversation(conversations.size.toString(), "test", R.drawable.ic_menu_camera)
    }

    private fun getPreviewBody(body: String): String {
        var preview = body.substring(0, body.length.coerceAtMost(35))
        preview += if (body.length > preview.length) "..." else ""
        return preview
    }

    private fun addConversation(title: String, content: String, id: Int) {
        listOfComponents.add(title)
        listOfContents.add(content)
        listOfIcons.add(id)
    }

    private fun clearDatas() {
        listOfComponents.clear()
        listOfContents.clear()
        listOfIcons.clear()
        conversations.clear()
    }

    init {
        listOfComponents = ArrayList()
        listOfContents = ArrayList()
        listOfIcons = ArrayList()
        conversations = ArrayList()
        contactManager = ContactManager(context, REQUEST_PERMISSION_KEY)
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((context as Activity),
                            Manifest.permission.READ_SMS)) {
            } else {
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.READ_SMS),
                        REQUEST_PERMISSION_KEY)
            }
        }
        refreshSMS()
        list.adapter = MessagesAdapter(context, mutableListOf<String?>().also { it += listOfComponents }, listOfContents,
                listOfIcons)
    }
}