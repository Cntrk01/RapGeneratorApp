package com.okation.aivideocreator.ui.settings.premium

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentPremiumBinding
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.getOfferingsWith
import com.revenuecat.purchases.purchaseWith
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PremiumFragment : Fragment() {
    private var _binding : FragmentPremiumBinding?=null
    private val binding get() = _binding!!
    private lateinit var revPackage : Package

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentPremiumBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        choosePrice()
        pricesText()
        continueButton()
        buttonClick()
        directionWebSite()
    }
    private fun directionWebSite(){
        with(binding){
            termofUs.setOnClickListener {
                val action = Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Term_of_office"))
                startActivity(action)
            }
            privacyPolicy.setOnClickListener {
                val action = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.neonapps.co/"))
                startActivity(action)
            }
        }
    }
    private fun buttonClick(){
        binding.closeButton.isClickable=true
        setButtonBoolValue(false)
    }
    private fun pricesText(){
        Purchases.sharedInstance.getOfferingsWith({
        }) { offerings ->
            offerings.current?.getPackage("Small")?.also {
                binding.weeklyPrice.text = it.product.price.formatted
            }
            offerings.current?.getPackage("Medium")?.also {
                binding.monthPrice.text = it.product.price.formatted
            }
            offerings.current?.getPackage("Large")?.also {
                binding.yearPrice.text = it.product.price.formatted
            }
        }
    }

    private fun choosePrice(){
        Purchases.sharedInstance.getOfferingsWith({
        }) { offerings ->
            binding.apply {
                weekly.setOnClickListener {
                    setBgColor(1)
                    binding.apply {
                        conBut.run {
                            isClickable = true
                            isActivated = true
                            isEnabled = true
                        }
                    }
                    offerings.current?.getPackage("Small")?.also {
                        revPackage = it
                    }
                }
                month.setOnClickListener {
                    setBgColor(2)
                    binding.apply {
                        conBut.run {
                            isClickable = true
                            isActivated = true
                            isEnabled = true
                        }
                    }
                    offerings.current?.getPackage("Medium")?.also {
                        revPackage = it
                    }
                }
                year.setOnClickListener {
                    setBgColor(3)
                    binding.apply {
                        conBut.run {
                            isClickable = true
                            isActivated = true
                            isEnabled = true
                        }
                    }
                    offerings.current?.getPackage("Large")?.also {
                        revPackage = it
                    }
                }
            }
        }
    }
    private fun continueButton(){
        val sharedPrefs = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        binding.conBut.setOnClickListener {
            Purchases.sharedInstance.purchaseWith(
                PurchaseParams.Builder(requireActivity(), revPackage).build(),
                onError = { error, _ ->
                    Log.e("errorCode: ${error.code}", error.message)
                },
                onSuccess = { _, _ ->
                    val editor = sharedPrefs.edit()
                    editor.putBoolean("is_payment_successful", true)
                    editor.apply()

                    val action=PremiumFragmentDirections.actionPremiumFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
            )
        }
    }

    private fun resetColors() {
        val blackColor = ContextCompat.getColor(requireContext(), R.color.black)

       binding.apply {
           weekConst.setBackgroundResource(R.color.white)
           weeklytext.setTextColor(blackColor)
           weeklyPrice.setTextColor(blackColor)
           monthConst.setBackgroundResource(R.color.white)
           monthText.setTextColor(blackColor)
           monthPrice.setTextColor(blackColor)
           yearConst.setBackgroundResource(R.color.white)
           yearText.setTextColor(blackColor)
           yearPrice.setTextColor(blackColor)
       }
    }

    private fun setSelectedStyle(view: View) {
        val whiteColor = ContextCompat.getColor(requireContext(), R.color.white)
        view.setBackgroundResource(R.drawable.premium_bg)
        setButtonBoolValue(true)
        when (view) {
            binding.weekConst -> {
                binding.weeklytext.setTextColor(whiteColor)
                binding.weeklyPrice.setTextColor(whiteColor)
            }
            binding.monthConst -> {
                binding.monthText.setTextColor(whiteColor)
                binding.monthPrice.setTextColor(whiteColor)
            }
            binding.yearConst -> {
                binding.yearText.setTextColor(whiteColor)
                binding.yearPrice.setTextColor(whiteColor)
            }
        }
    }
    private fun setButtonBoolValue(bool:Boolean){
        binding.continueButton.isClickable=bool
        binding.conBut.isClickable=bool
    }
    private fun setBgColor(ids: Int) {
        resetColors()

        when (ids) {
            1 -> setSelectedStyle(binding.weekConst)
            2 -> setSelectedStyle(binding.monthConst)
            3 -> setSelectedStyle(binding.yearConst)
        }
        binding.conBut.setBackgroundResource(R.drawable.premium_bg)
    }
}