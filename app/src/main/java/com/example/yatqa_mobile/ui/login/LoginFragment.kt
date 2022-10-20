package com.example.yatqa_mobile.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.yatqa_mobile.R
import com.example.yatqa_mobile.data.datamodels.Login
import com.example.yatqa_mobile.data.local.getDatabase
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
    ): View? {
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


        val database = getDatabase(requireActivity().application)

        binding.editTextInputUserPassword.setText(viewModel.loginList.value?.find { it.id == 0 }?.userPassword)

        viewModel.actionCompleted.observe(
            viewLifecycleOwner,
            Observer {
                if (it) {
                    viewModel.unsetComplete()
                    database.loginDatabaseDao.getAll()
                }
            }
        )

        binding.cbSaveToFav.setOnClickListener {
            if (binding.cbSaveToFav.isChecked){
                binding.editTextInputListName.visibility = View.VISIBLE
            }else{
                binding.editTextInputListName.visibility = View.GONE
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