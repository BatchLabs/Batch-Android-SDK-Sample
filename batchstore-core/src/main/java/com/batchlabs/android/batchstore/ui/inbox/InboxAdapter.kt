package com.batchlabs.android.batchstore.ui.inbox

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.batch.android.BatchInboxNotificationContent
import com.batchlabs.android.batchstore.core.databinding.RowInboxBinding
import java.text.SimpleDateFormat
import java.util.*

class InboxAdapter(private val notifications: MutableList<BatchInboxNotificationContent>, private val clickListener: (BatchInboxNotificationContent) -> Unit): androidx.recyclerview.widget.RecyclerView.Adapter<InboxAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (notifications.isEmpty()) {
            holder.title.text = "No data"
            holder.body.text = "Your inbox is empty. Try to refresh ⬇️"
            holder.date.visibility = View.GONE
            holder.unreadView.visibility = View.GONE
            holder.itemView.setBackgroundColor(Color.WHITE)
        } else {
            val notification = notifications[position]

            holder.body.text = "${notification.body}"
            holder.title.text = "${notification.title}"

            val format = SimpleDateFormat("h:mm a - dd/MM/yyy", Locale.ENGLISH)
            holder.date.text = format.format(notification.date)

            if(!notification.isUnread) {
                holder.unreadView.visibility = View.GONE
            }

            holder.bind(notification, clickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = RowInboxBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(v)
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

    class ViewHolder(itemView: RowInboxBinding): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView.root){
        val unreadView = itemView.inboxUnread
        val body = itemView.inboxBody
        val title = itemView.inboxTitle
        val date = itemView.inboxDate

        fun bind(notification:BatchInboxNotificationContent, clickListener: (BatchInboxNotificationContent) -> Unit) {
            itemView.setOnClickListener { clickListener(notification)}
        }
    }
}
