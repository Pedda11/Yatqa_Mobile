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
import com.example.yatqa_mobile.data.datamodels.Login
import com.example.yatqa_mobile.databinding.FragmentLoginBinding
import com.example.yatqa_mobile.ui.main.MainViewModel

class LoginFragment : Fragment() {

    // Initialisiere binding & viewModel
    private lateinit var binding: FragmentLoginBinding
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
            R.layout.fragment_login,
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

        viewModel.actionCompleted.observe(
            viewLifecycleOwner
        ) {
            if (it) {
                viewModel.unsetComplete()
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToFavoritesFragment())
            }
        }

        viewModel.loginList.observe(
            viewLifecycleOwner
        ) {
            if (it.size > 0) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToFavoritesFragment())
            }
        }

        binding.cbSaveToFav.setOnClickListener {
            if (binding.cbSaveToFav.isChecked){
                binding.editTilListName.visibility = View.VISIBLE
            }else{
                binding.editTilListName.visibility = View.GONE
            }
        }

        binding.btnLogin.setOnClickListener {
            if (binding.cbSaveToFav.isChecked) {
                val ip = binding.editTextInputIp.text.toString()
                val qPort = binding.editTextInputQport.text.toString().toInt()
                val port = binding.editTextInputPort.text.toString().toInt()
                val userName = binding.editTextInputUserName.text.toString()
                val userPassword = binding.editTextInputUserPassword.text.toString()
                val listName = binding.editTextInputListName.text.toString()
                val newLogin = Login(
                    id = 0,
                    ip = ip,
                    qPort = qPort,
                    port = port,
                    userName = userName,
                    userPassword = userPassword,
                    listName = listName
                )

                viewModel.insert(newLogin)
            }
        }
    }
}