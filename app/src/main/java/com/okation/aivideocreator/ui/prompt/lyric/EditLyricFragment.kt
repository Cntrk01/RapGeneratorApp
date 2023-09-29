package com.okation.aivideocreator.ui.prompt.lyric

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.okation.aivideocreator.databinding.FragmentEditLyricBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditLyricFragment : Fragment() {
    private var _binding : FragmentEditLyricBinding?=null
    private val binding get() = _binding!!
    private val args : EditLyricFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentEditLyricBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            backButton.setOnClickListener {
                val getText=binding.changeText.text.toString()
                val action=EditLyricFragmentDirections.actionEditLyricFragmentToFinishLyricFragment(getText,args.title)
                findNavController().navigate(action)
            }
            saveButton.setOnClickListener {
                val getText=binding.changeText.text.toString()
                val action=EditLyricFragmentDirections.actionEditLyricFragmentToFinishLyricFragment(getText,args.title)
                findNavController().navigate(action)
            }
            changeText.setText(args.text)
        }
    }
}