package com.batchlabs.android.batchstore.ui.cart

import android.os.Bundle
import android.view.*
import com.batchlabs.android.batchstore.CartManager
import com.batchlabs.android.batchstore.core.R
import com.batchlabs.android.batchstore.core.databinding.FragmentCartBinding

class CartFragment : androidx.fragment.app.Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): CartFragment {
            return CartFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        refresh()
        return binding.root
    }

    private fun refresh(){
        val articles = CartManager.articles
        val adapter = CartAdapter(articles)

        val recyclerView = binding.cartRecyclerView
        recyclerView.adapter = adapter

        val totalTv = binding.totalTextView
        if (articles.size > 0) {
            totalTv.text = "${CartManager.computeTotal()} €"
        } else {
            totalTv.text = "0 €"
        }

        val checkButton = binding.checkoutButton
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cut ->
                clear()
            else -> {
                clear()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
