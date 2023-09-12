package com.okation.aivideocreator.ui.splashscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.databinding.FragmentOnBoard2Binding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnBoardFragment2 : Fragment() {
    private var _binding : FragmentOnBoard2Binding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentOnBoard2Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            val action =
                OnBoardFragment2Directions.actionOnBoardFragment2ToOnBoardFragment3()
            findNavController().navigate(action)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}