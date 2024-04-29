package com.batchlabs.android.batchstore.ui.inbox


import android.content.Context
import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.*
import com.batch.android.Batch
import com.batch.android.BatchInboxFetcher
import com.batch.android.BatchInboxNotificationContent
import com.batchlabs.android.batchstore.core.R
import com.batchlabs.android.batchstore.core.databinding.FragmentInboxBinding
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class InboxFragment : androidx.fragment.app.Fragment() {
    private val inboxSecret = ""

    private var _binding: FragmentInboxBinding? = null
    private val binding get() = _binding!!


    var notifications: List<BatchInboxNotificationContent> = emptyList()
    lateinit var inboxAPI: BatchInboxFetcher
    lateinit var inboxAdapter: InboxAdapter
    lateinit var swipe: SwipeRefreshLayout

    private var loading:Boolean = false
    private var TAG: String = "InboxFragment"

    companion object {
        fun newInstance(): InboxFragment {
            return InboxFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        _binding = FragmentInboxBinding.inflate(inflater, container, false)

        val view =binding.root
        val context = view.context
        swipe = binding.swipeRefresh

        setupInstallationFetcher(context)

        reloadData { adapter -> setAdapter(adapter) }

        swipe.setOnRefreshListener {
            reloadData { adapter -> setAdapter(adapter) }
        }

        return view
    }

    private fun setupInstallationFetcher(context: Context) {
        Log.d(TAG, "setupInstallationFetcher")
        inboxAPI = Batch.Inbox.getFetcher(context)
    }

    private fun setupUserFetcher(context: Context, customID: String) {
        val authKey = computeHash(inboxSecret, "<YOUR API KEY HERE>", customID)
        inboxAPI = Batch.Inbox.getFetcher(context,customID,authKey)
        Log.d(TAG, "setupUserFetcher customID : ${customID} auth : ${authKey}")
    }

    private fun reloadData(callback: (InboxAdapter?) -> Unit) {
        loading = true
        inboxAPI.fetchNewNotifications(object : BatchInboxFetcher.OnNewNotificationsFetchedListener {

            override fun onFetchSuccess(notificationsResult: MutableList<BatchInboxNotificationContent>, foundNewNotifications: Boolean, endReached: Boolean) {
                notifications = notificationsResult
                inboxAdapter = InboxAdapter(notifications as MutableList<BatchInboxNotificationContent>) { n -> setAsRead(n) }

                swipe.isRefreshing = false
                loading = false

                callback(inboxAdapter)
                Log.d(TAG, "onFetchSuccess ${notifications.size} foundNew: $foundNewNotifications endReached: $endReached")
            }

            override fun onFetchFailure(error: String) {
                swipe.isRefreshing = false
                loading = false

                callback(null)
                Log.d(TAG, "onFetchFailure $error")
            }
        })
    }

    private fun fetchMore() {
        loading = true
        inboxAPI.fetchNextPage(object :BatchInboxFetcher.OnNextPageFetchedListener {
            override fun onFetchSuccess(notifications: MutableList<BatchInboxNotificationContent>, error: Boolean) {
                inboxAdapter.addNotifications(notifications)
                inboxAdapter.notifyDataSetChanged()
                loading = false

                Log.d(TAG, "onFetchSuccess ${notifications.size} add")
            }

            override fun onFetchFailure(error: String) {
                Log.d(TAG, "onFetchFailure $error")
                loading = false
            }
        })
    }

    private fun setAdapter(adapter:InboxAdapter?) {
        if(adapter != null) {
            binding.inboxRecyclerview.adapter = adapter
            binding.inboxRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        val canScrollDownMore = recyclerView.canScrollHorizontally(1)
                        if (!canScrollDownMore) {
                            if (!loading) { fetchMore() }
                        }
                    }
                }
            })
        } else {
            Log.d(TAG, "Adapter is null")
            val emptyList: MutableList<BatchInboxNotificationContent> = arrayListOf()
            inboxAdapter = InboxAdapter(emptyList) { n -> setAsRead(n) }
            binding.inboxRecyclerview.adapter = inboxAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.inbox_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setAsRead(notification:BatchInboxNotificationContent) {
        inboxAPI.markAsRead(notification)
        inboxAdapter.notifyDataSetChanged()
    }

    private fun setAllAsRead() {
        inboxAPI.markAllAsRead()
        inboxAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.read_all -> {
                setAllAsRead()
            }
            else -> {
                setAllAsRead()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun computeHash(secret: String, apikey: String, identifier: String): String {
        try {
            val hmac = Mac.getInstance("HmacSHA256")
            val key = SecretKeySpec(secret.toByteArray(charset("UTF-8")), "HmacSHA256")
            hmac.init(key)

            return bytesToHex(hmac.doFinal((apikey + identifier).toByteArray(charset("UTF-8"))))
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            throw RuntimeException(e)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            throw RuntimeException(e)
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
            throw RuntimeException(e)
        }

    }

    fun bytesToHex(a: ByteArray): String {
        val sb = StringBuilder(a.size * 2)
        for (b in a)
            sb.append(String.format("%02x", b))
        return sb.toString()
    }
}
