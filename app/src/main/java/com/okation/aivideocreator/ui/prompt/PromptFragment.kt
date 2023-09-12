package com.okation.aivideocreator.ui.prompt

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentPromptBinding
import com.okation.aivideocreator.ui.prompt.promptviewmodel.PromptViewmodel
import com.okation.aivideocreator.ui.prompt.promtelement.*
import dagger.hilt.android.AndroidEntryPoint


class PromptFragment : Fragment() {
    private var _binding : FragmentPromptBinding?=null
    private val binding get() = _binding!!
    private lateinit var adapter : PromptViewPager
    var ids=0

    private val viewModel : PromptViewmodel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentPromptBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textPrompt.text.clear()

        observeText()
        viewPager()
        setClickButton()
        checkContinueButton()

    }

    private fun checkContinueButton(){
        binding.continueButton.setOnClickListener {
            val promptText=binding.textPrompt.text.toString()
            if (promptText.isBlank())
                Toast.makeText(requireContext(),"Select a Post First",Toast.LENGTH_SHORT).show()
            else{
                val action=PromptFragmentDirections.actionPromptFragmentToGeneratingLyricFragment(promptText)
                findNavController().navigate(action)

            }
        }
        binding.backButton.setOnClickListener {
            val action=PromptFragmentDirections.actionPromptFragmentToHomeFragment()
            findNavController().navigate(action)
        }

    }

    private fun viewPager(){
        val pageItems = listOf(
            FunFragment(),
            HappyFragment(),
            LoveFragment(),
            SadFragment(),
            SexyFragment()
        )
        adapter = PromptViewPager(childFragmentManager,lifecycle,pageItems)
        binding.apply {
            viewpager2.adapter = adapter
            viewpager2.isUserInputEnabled = false;
        }

    }

    private fun observeText(){
        viewModel.getText.observe(viewLifecycleOwner){
            it?.let {
               binding.continueButton.setBackgroundResource(R.drawable.premium_bg)
               binding.textPrompt.setText(it)
            }
        }

    }

    private fun setClickButton(){
        binding.apply {
            viewpager2.setCurrentItem(0, true)
            funCard.background=ContextCompat.getDrawable(requireContext(), R.drawable.premium_bg)
            fun1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

            fun1.setOnClickListener {
                ids=1
                setBgColor(ids)
                viewpager2.setCurrentItem(0, true)
            }
            happy.setOnClickListener {
                ids=2
                setBgColor(ids)
                viewpager2.setCurrentItem(1, true)
            }
            love.setOnClickListener {
                ids=3
                setBgColor(ids)
                viewpager2.setCurrentItem(2, true)
            }
            sad.setOnClickListener {
                ids=4
                setBgColor(ids)
                viewpager2.setCurrentItem(3, true)
            }
            sexy.setOnClickListener {
                ids=5
                setBgColor(ids)
                viewpager2.setCurrentItem(4, true)
            }
        }

    }

    private fun setBgColor(ids: Int) {
        resetColors()
        binding.apply {
            when (ids) {
                1 -> setSelectedStyle(funCard)
                2 -> setSelectedStyle(happyCard)
                3 -> setSelectedStyle(loveCard)
                4 -> setSelectedStyle(sadCard)
                5 -> setSelectedStyle(sexyCard)
            }

        }


    }

    private fun resetColors() {
        val blackColor = ContextCompat.getColor(requireContext(), R.color.black)

        binding.apply {
            funCard.setBackgroundResource(R.color.white)
            fun1.setTextColor(blackColor)

            happyCard.setBackgroundResource(R.color.white)
            happy.setTextColor(blackColor)

            loveCard.setBackgroundResource(R.color.white)
            love.setTextColor(blackColor)

            sadCard.setBackgroundResource(R.color.white)
            sad.setTextColor(blackColor)

            sexyCard.setBackgroundResource(R.color.white)
            sexy.setTextColor(blackColor)
        }
    }

    private fun setSelectedStyle(view: View) {
        val whiteColor = ContextCompat.getColor(requireContext(), R.color.white)
        val premiumBgDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.premium_bg)
        view.background = premiumBgDrawable

        binding.apply {
            when (view) {
                funCard -> {
                    fun1.setTextColor(whiteColor)
                }
                happyCard -> {
                    happy.setTextColor(whiteColor)
                }
                loveCard -> {
                    love.setTextColor(whiteColor)
                }
                sadCard -> {
                    sad.setTextColor(whiteColor)
                }
                sexyCard -> {
                    sexy.setTextColor(whiteColor)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getText.value=""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}