package com.example.yatqa_mobile.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.yatqa_mobile.R
import com.example.yatqa_mobile.data.datamodels.Login
import com.example.yatqa_mobile.databinding.FragmentLoginBinding
import com.example.yatqa_mobile.ui.main.MainViewModel
import kotlinx.coroutines.delay

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

        val loginId = requireArguments().getInt("loginId")

        /**
         * fill editText when edit login
         */
        if (loginId != 0) {
            val login = viewModel.loginList.value!!.find { it?.id == loginId }
            if (login != null) {
                binding.editTextInputIp.setText(login.ip)
                binding.editTextInputQport.setText(login.qPort.toString())
                binding.editTextInputPort.setText(if (login.port == null) "" else login.port.toString())
                binding.editTextInputUserName.setText(login.userName)
                binding.editTextInputUserPassword.setText(login.userPassword)
                binding.editTextInputListName.setText(login.listName)

                binding.btnLogin.text = getString(R.string.saveChanges)
                binding.cbSaveToFav.visibility = View.GONE
                binding.cbSaveToFav.isChecked = false
                binding.editTilListName.visibility = View.VISIBLE
            }
        }

        binding.cbSaveToFav.setOnClickListener {
            if (binding.cbSaveToFav.isChecked) {
                binding.editTilListName.visibility = View.VISIBLE
                binding.btnLogin.text = getString(R.string.saveToFav)
            } else {
                binding.editTilListName.visibility = View.GONE
                binding.btnLogin.text = getString(R.string.connect)
            }
        }

        binding.btnLogin.setOnClickListener {

            if (loginId != 0) {
                //edit login
                try {
                    val data = loginData(loginId) ?: return@setOnClickListener

                    viewModel.updateLogin(data)

                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToFavoritesFragment())
                } catch (e: Exception) {
                    Log.e("LoginFragment", "btnLoginClicked changing loginData: $e")
                }
            } else {
                //new login
                try {
                    val data = loginData(loginId) ?: return@setOnClickListener

                    if (binding.cbSaveToFav.isChecked) {
                        saveNewLogin(data)
                        findNavController().navigate(R.id.favoritesFragment)
                    } else {
                        viewModel.ts3ApiConnect(data)
                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToGlobalServerFragment())
                    }

                } catch (e: Exception) {
                    Log.e("LoginFragment", "Login credentials, connect: $e")
                }
            }
        }
//weitermachen
        binding.editTextInputListName.setOnClickListener {
            binding.scrollViewLogin.scrollTo(150,0)
        }
    }

    //insert new login
    private fun saveNewLogin(newLogin: Login) {
        try {
            viewModel.insert(newLogin)
        } catch (e: Exception) {
            Log.e("LoginFragment", "saveNewLogin, goto favorites: $e")
        }
    }

    //set login data
    private fun loginData(loginId :Int): Login? {
        val ip = binding.editTextInputIp.text.toString()

        val qPort = if (!binding.editTextInputQport.text.isNullOrEmpty()) binding.editTextInputQport.text.toString().toInt()
        else null

        if (qPort == null || ip.isEmpty()) {
            Toast.makeText(requireContext(), "Query port must not be empty!", Toast.LENGTH_SHORT)
                .show()
            return null
        }

        val port = if (!binding.editTextInputPort.text.isNullOrEmpty())
            binding.editTextInputPort.text.toString().toInt()
        else
            null

        val userName = binding.editTextInputUserName.text.toString()
        val userPassword = binding.editTextInputUserPassword.text.toString()
        val listName = binding.editTextInputListName.text.toString()

        var updateLoginId = 0

        if (loginId != 0){
            updateLoginId = loginId
        }

        return Login(
            id = updateLoginId,
            ip = ip,
            qPort = qPort,
            port = port,
            userName = userName,
            userPassword = userPassword,
            listName = listName
        )
    }
}