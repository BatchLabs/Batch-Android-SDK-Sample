package com.batchlabs.android.batchstore.core.UI.Cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import com.batchlabs.android.batchstore.CartManager
import com.batchlabs.android.batchstore.core.R
import kotlinx.android.synthetic.main.fragment_cart.view.*

class CartFragment : androidx.fragment.app.Fragment() {

    private lateinit var layoutView:View

    companion object {
        fun newInstance(): CartFragment {
            return CartFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val view = inflater!!.inflate(R.layout.fragment_cart,container,false)
        layoutView = view
        refresh()
        return view
    }

    private fun refresh(){
        val view = layoutView
        val articles = CartManager.articles
        val adapter = CartAdapter(articles)

        val recyclerView = view.cart_recycler_view
        recyclerView.adapter = adapter

        val totalTv = view.totalTextView
        if (articles.size > 0) {
            totalTv.text = "${CartManager.computeTotal()} €"
        } else {
            totalTv.text = "0 €"
        }

        val checkButton = view.checkoutButton
        checkButton.isEnabled = articles.size > 0

        checkButton.setOnClickListener {
            CartManager.checkout()
            refresh()
        }
    }

    private fun clear(){
        CartManager.clear()
        refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.cart_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.action_cut ->
                    clear()
                else -> {
                    clear()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}