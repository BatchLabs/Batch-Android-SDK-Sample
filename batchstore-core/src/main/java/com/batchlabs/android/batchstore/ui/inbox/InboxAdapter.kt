package com.batchlabs.android.batchstore.ui.inbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.batch.android.BatchInboxNotificationContent
import com.batchlabs.android.batchstore.core.R
import kotlinx.android.synthetic.main.row_inbox.view.*
import java.text.SimpleDateFormat
import java.util.*

class InboxAdapter(private val notifications: MutableList<BatchInboxNotificationContent>, private val clickListener: (BatchInboxNotificationContent) -> Unit): androidx.recyclerview.widget.RecyclerView.Adapter<InboxAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (notifications.isEmpty()) {
            holder.title.text = "No data"
            holder.body.text = "Your inbox is empty. Try to refresh ⬇️"
            holder.date.visibility = View.GONE
            holder.unreadView.visibility = View.GONE
        } else {
            val nofitication = notifications[position]

            holder.body.text = "${nofitication.body}"
            holder.title.text = "${nofitication.title}"

            val format = SimpleDateFormat("h:mm a - dd/MM/yyy", Locale.ENGLISH)
            holder.date.text = format.format(nofitication.date)

            if(!nofitication.isUnread) {
                holder.unreadView.visibility = View.GONE
            }

            holder.bind(nofitication, clickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxAdapter.ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.row_inbox, parent, false)
        return ViewHolder(v);
    }

    fun addNotifications(newNotifications:List<BatchInboxNotificationContent>) {
        newNotifications.forEach { notification -> notifications.add(notification) }
    }

    override fun getItemCount(): Int {
        return if (notifications.isEmpty()) {
            1
        } else {
            notifications.size
        }

    }

    class ViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        val unreadView = itemView.inboxUnread!!
        val body = itemView.inboxBody!!
        val title = itemView.inboxTitle!!
        val date = itemView.inboxDate!!

        fun bind(notification:BatchInboxNotificationContent, clickListener: (BatchInboxNotificationContent) -> Unit) {
            itemView.setOnClickListener { clickListener(notification)}
        }
    }
}