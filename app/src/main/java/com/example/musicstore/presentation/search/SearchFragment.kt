package com.example.musicstore.presentation.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicstore.R
import com.example.musicstore.presentation.search.songs.SongsFragmentDirections
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_results, container, false)

        setupSearchView()

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = SearchRecyclerViewAdapter(listOf()) { }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.results.observe(viewLifecycleOwner) {

                    (view as RecyclerView).adapter = SearchRecyclerViewAdapter(it) { id ->
                        searchResultClicked(id)
                    }
                }
            }
        }
    }

    private fun setupSearchView() {
        val searchView=  (requireActivity() as AppCompatActivity).findViewById<SearchView>(R.id.searchView)
        with(searchView) {
            setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    handleSearchInput(query)
                    hideKeyboard(view)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
    }

    private fun handleSearchInput(query: String?) {
        viewModel.newSearch(query)
    }

    private fun searchResultClicked(id: String): Unit {
        val albumDirections = SearchFragmentDirections.albumSelected(id)
        findNavController().navigate(albumDirections)
    }

    private fun hideKeyboard(view: View?) {
        val inputMethodManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}