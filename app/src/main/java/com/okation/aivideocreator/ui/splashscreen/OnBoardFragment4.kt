package com.okation.aivideocreator.ui.splashscreen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.databinding.FragmentOnBoard4Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardFragment4 : Fragment() {
    private var _binding : FragmentOnBoard4Binding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentOnBoard4Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)


        binding.nextButton.setOnClickListener {
            val action = OnBoardFragment4Directions.actionOnBoardFragment4ToPremiumFragment()
            findNavController().navigate(action)
            val editor = sharedPreferences.edit()
            editor.putBoolean("skipOnBoarding", true)
            editor.apply()
        }

//        if (sharedPreferences.getBoolean("skipOnBoarding", false)) {
//            val navController = OnBoardFragment4Directions.actionOnBoardFragment4ToPremiumFragment()
//            findNavController().navigate(navController)
//        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}