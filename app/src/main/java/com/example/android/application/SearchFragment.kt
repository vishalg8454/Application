package com.example.android.application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.application.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding = FragmentSearchBinding.inflate(layoutInflater)

        binding.searchViewModel = viewModel
        binding.lifecycleOwner = this

        binding.button.setOnClickListener {
            var isbn = "isbn:"
            isbn += binding.editText.text
            viewModel.fetch(isbn)
        }
        return binding.root
    }
}
