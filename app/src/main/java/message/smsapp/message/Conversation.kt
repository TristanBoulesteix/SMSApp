package message.smsapp.message

import java.util.*

class Conversation(val phoneNumber: String, val messages: ArrayList<String>) {
    companion object {
		fun generateConversation(adress: ArrayList<String>,
								 smsContent: ArrayList<String>): ArrayList<Conversation> {
            val listOfConversations = ArrayList<Conversation>()
            var currentNumber: String

            // Add first conversation
            currentNumber = adress[0]
            val messages: ArrayList<String> = ArrayList()

            for (i in smsContent.indices) {
                if (adress[i] == currentNumber) {
                    messages.add(smsContent[i])
                }
            }
            listOfConversations.add(Conversation(currentNumber, messages))

            // Add other conversations
            while (currentNumber != "") {
                currentNumber = selectANewConversation(listOfConversations, adress)
                for (i in smsContent.indices) {
                    if (adress[i] == currentNumber) {
                        messages.add(smsContent[i])
                    }
                }
                listOfConversations.add(Conversation(currentNumber, messages))
            }
            return listOfConversations
        }

        private fun selectANewConversation(list: ArrayList<Conversation>, adress: ArrayList<String>): String {
            val phoneUsed = ArrayList<String>()
            for (i in list.indices) {
                phoneUsed.add(list[i].phoneNumber)
            }
            for (i in adress.indices) {
                if (!phoneUsed.contains(adress[i])) {
                    return adress[i]
                }
            }
            return ""
        }
    }

}