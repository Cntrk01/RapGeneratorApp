package com.okation.aivideocreator.ui.splashscreen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.databinding.FragmentOnBoard1Binding
import com.okation.aivideocreator.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardFragment1 : Fragment() {
    private var _binding : FragmentOnBoard1Binding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentOnBoard1Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val isPaymentSuccessful = sharedPrefs.getBoolean("is_payment_successful", false)

        if (isPaymentSuccessful){
            val bind= OnBoardFragment1Directions.actionOnBoardFragment1ToHomeFragment2()
            findNavController().navigate(bind)
        }else{
            if (sharedPrefs.getBoolean("skipOnBoarding", false)) {
                val navController = OnBoardFragment1Directions.actionOnBoardFragment1ToPremiumFragment()
                findNavController().navigate(navController)
            }
        }



        binding.nextButton.setOnClickListener {
            val action = OnBoardFragment1Directions.actionOnBoardFragment1ToOnBoardFragment2()
            findNavController().navigate(action)

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}