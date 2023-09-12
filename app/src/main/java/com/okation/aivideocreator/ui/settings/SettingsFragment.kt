package com.okation.aivideocreator.ui.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.databinding.FragmentSettingsBinding
import com.okation.aivideocreator.ui.splashscreen.OnBoardFragment1Directions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding : FragmentSettingsBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation()
        checkPremium()
        directionWebSite()
    }

    private fun checkPremium(){
        val sharedPrefs = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val isPaymentSuccessful = sharedPrefs.getBoolean("is_payment_successful", false)

        if (isPaymentSuccessful){
            binding.getPremium.visibility=View.GONE
        }
    }

    private fun initNavigation(){
        binding.backButton.setOnClickListener {
            val action=SettingsFragmentDirections.actionSettingsFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }


    private fun directionWebSite(){
        with(binding){
            termofUs.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Term_of_office"))
                startActivity(intent)
            }
            contacUs.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.neonapps.co/contact-us"))
                startActivity(intent)
            }
            privacyPolicy.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.neonapps.co/"))
                startActivity(intent)
            }
            help.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.neonapps.co/contact-us"))
                startActivity(intent)
            }
        }
    }
}