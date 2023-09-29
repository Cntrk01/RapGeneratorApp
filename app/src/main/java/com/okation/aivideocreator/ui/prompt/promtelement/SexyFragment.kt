package com.okation.aivideocreator.ui.prompt.promtelement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.okation.aivideocreator.databinding.FragmentSexyBinding
import com.okation.aivideocreator.ui.prompt.promptviewmodel.PromptViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SexyFragment : Fragment() {
    private var _binding : FragmentSexyBinding?=null
    private val binding get() = _binding!!
    private val viewModel : PromptViewmodel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSexyBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            card1.setOnClickListener {
                val text=text1.text.toString()
                viewModel.setFunText(text)
            }
            card2.setOnClickListener {
                val text=text2.text.toString()
                viewModel.setFunText(text)
            }
            card3.setOnClickListener {
                val text=text3.text.toString()
                viewModel.setFunText(text)
            }
            card4.setOnClickListener {
                val text=text4.text.toString()
                viewModel.setFunText(text)
            }
        }
    }
}