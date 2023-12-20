package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.FragmentLoginBinding
import com.ownlab.ownlab_client.databinding.FragmentRegisterBinding
import com.ownlab.ownlab_client.models.Id
import com.ownlab.ownlab_client.models.Info
import com.ownlab.ownlab_client.utils.ApiResponse

import com.ownlab.ownlab_client.viewmodels.RegisterViewModel
import com.ownlab.ownlab_client.viewmodels.`interface`.CoroutinesErrorHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment: Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        registerViewModel.idChkResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    if (it.data.message == "Failed") {
                        Toast.makeText(activity, it.data.message, Toast.LENGTH_LONG).show()

                        val action = RegisterFragmentDirections.register2RegisterIdChk("아이디가 이미 존재합니다.")
                        navController.navigate(action)
                    } else {
                        val action = RegisterFragmentDirections.register2RegisterIdChk("해당 아이디를 사용할 수 있습니다.")
                        navController.navigate(action)
                    }
                }
                is ApiResponse.Failure -> {
                    Toast.makeText(activity, "API Failed", Toast.LENGTH_LONG).show()
                }
            }
        }

        registerViewModel.registerResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Success -> {
                    if (it.data.message == "User Already Exists") {
                        Toast.makeText(activity, it.data.message, Toast.LENGTH_LONG).show()

                        val action = RegisterFragmentDirections.register2RegisterIdChk("계정이 이미 존재합니다.")
                        navController.navigate(action)
                    } else {
                        navController.navigate(R.id.register_2_login)
                    }
                }
                is ApiResponse.Failure -> {
                    Toast.makeText(activity, "API Failed", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.idChkBtn.setOnClickListener {
            registerViewModel.idChk(Id(binding.idField.text.toString()), object: CoroutinesErrorHandler {
                override fun onError(m : String) {
                    Toast.makeText(activity, "Error $m", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.registerBtn.setOnClickListener {
            val id: String = binding.idField.text.toString()
            val password: String = binding.passwordField.text.toString()
            val _password: String = binding.rePasswordField.text.toString()
            val name: String = binding.nameField.text.toString()
            val tel: String = binding.telField.text.toString()

            if (password != _password) {
                val action = RegisterFragmentDirections.register2RegisterIdChk("비밀번호가 동일하지 않습니다.")
                navController.navigate(action)
            } else {
                registerViewModel.register(Info(id, password, name, tel), object: CoroutinesErrorHandler {
                    override fun onError(m : String) {
                        Toast.makeText(activity, "Error $m", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}