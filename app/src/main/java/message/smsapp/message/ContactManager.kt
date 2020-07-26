package message.smsapp.message

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import message.smsapp.debug.toast
import java.util.*

class ContactManager(private val context: Context, REQUEST_PERMISSION_KEY: Int) {
    private val contacts by lazy {
        val list = mutableListOf<Contact>()
        with(context.contentResolver) {
            this.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null).use {
                if (it != null && it.count > 0) {
                    while (it.moveToNext()) {
                        val id = it.getString(
                                it.getColumnIndex(ContactsContract.Contacts._ID))
                        val name = it.getString(it.getColumnIndex(
                                ContactsContract.Contacts.DISPLAY_NAME))
                        if (it.getInt(it.getColumnIndex(
                                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                            this.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null)?.use { cursorContact ->
                                while (cursorContact.moveToNext()) {
                                    val phoneNumber = cursorContact.getString(cursorContact.getColumnIndex(
                                            ContactsContract.CommonDataKinds.Phone.NUMBER))
                                    list += Contact(id.toInt(), name, phoneNumber)
                                }
                            }
                        }
                    }
                }
            }
        }

        return@lazy list
    }

    init {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((context as Activity),
                            Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.READ_CONTACTS),
                        REQUEST_PERMISSION_KEY)
            }
        }
    }

    fun getContactNameWithNumber(phoneNumber: String) = contacts.firstOrNull { it.phoneNumber == phoneNumber }?.name ?: phoneNumber

    data class Contact(val id: Int, var name: String, var phoneNumber: String)
}