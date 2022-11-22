package com.example.yatqa_mobile.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.yatqa_mobile.R
import com.example.yatqa_mobile.adapter.FavoritesAdapter
import com.example.yatqa_mobile.databinding.FragmentFavoritesBinding
import com.example.yatqa_mobile.ui.main.MainViewModel

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: MainViewModel by activityViewModels()

    /**
     * Lifecycle Funktion onCreateView
     * Hier wird das binding initialisiert und das Layout gebaut
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorites,
            container,
            false
        )

        binding.lifecycleOwner = this.viewLifecycleOwner

        return binding.root
    }

    /**
     * Lifecycle Funktion onViewCreated
     * Hier werden die Elemente eingerichtet und z.B. onClickListener gesetzt
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginList.observe(
            viewLifecycleOwner
        ) {
            //generate adapter
            val recycler = binding.rvFavorites
            recycler.adapter = FavoritesAdapter(it, viewModel.ts3ApiConnect, viewModel.removeLogin)
        }

        //move to manual login
        binding.btnToLogin.setOnClickListener {
            findNavController().navigate(
                FavoritesFragmentDirections.actionFavoritesFragmentToLoginFragment(
                    0
                )
            )
        }
    }
}