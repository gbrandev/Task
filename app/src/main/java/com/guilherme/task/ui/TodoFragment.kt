package com.guilherme.task.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.guilherme.task.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.guilherme.task.databinding.FragmentHomeBinding
import com.guilherme.task.databinding.FragmentTodoBinding
import com.guilherme.task.ui.adapter.TaskAdapter
import com.guilherme.task.data.model.Task


class TodoFragment : Fragment() {

    private lateinit var taskAdapter: TaskAdapter
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()

        initRecyclerViewTask(getTask())
    }

    private fun initListeners(){
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate((R.id.action_homeFragment_to_formTaskFragment))
        }
    }

    private fun initRecyclerViewTask(taskList: List<Task>){

        taskAdapter = TaskAdapter(taskList)
        binding.recyclerViewTask.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTask.setHasFixedSize(true)

        binding.recyclerViewTask.adapter = taskAdapter
    }

    private fun getTask() = listOf(
        Task("0","Criar nova tela do app"),
        Task("1","Validar informações na tela de login"),
        Task("2","Adicionar nova funcionalidade no app"),
        Task("3","Salvar token localmente"),
        Task("2","Criar funcionalidade de logout no app"),
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}