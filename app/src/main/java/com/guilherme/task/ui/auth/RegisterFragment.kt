package com.guilherme.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.guilherme.task.R
import com.guilherme.task.databinding.FragmentRegisterBinding
import com.guilherme.task.util.initToolbar
import com.guilherme.task.util.showBottomSheet

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListener()
    }

    private fun initListener(){
        binding.buttonRegister.setOnClickListener {
            validadeData();
        }
    }

    private fun validadeData(){
        val email = binding.edittextEmail.text.toString().trim()
        val senha = binding.edittextSenha.text.toString().trim()

        if (email.isNotBlank()){
            if(senha.isNotBlank()){
                Toast.makeText(requireContext(),"Tudo OK!", Toast.LENGTH_SHORT).show()
            }else{
                showBottomSheet(message = getString(R.string.password_empty_register_fragment))
            }
        }else{
            showBottomSheet(message = getString(R.string.email_empty_register_fragment))
        }
    }

    private fun registerUser(email: String, password: String){
        try {
            val auth = FirebaseAuth.getInstance()

            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        binding.progressBar.isVisible = true
                        //Criar o usuário e encaminha-lo para a tela home
                        findNavController().navigate(R.id.action_global_homeFragment2)
                        registerUser(email, password)
                    } else {
                        binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
                    }

                }
        } catch (e: Exception){
            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}