package com.okation.aivideocreator.ui.prompt.lyric

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.okation.aivideocreator.databinding.FragmentFinishLyricBinding
import com.okation.aivideocreator.ui.prompt.promptviewmodel.PromptGptViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FinishLyricFragment : Fragment() {
    private var _binding : FragmentFinishLyricBinding?=null
    private val binding get() = _binding!!
    private val args : FinishLyricFragmentArgs by navArgs()
    private val promptViewmodel : PromptGptViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentFinishLyricBinding.inflate(inflater,container,false)
        binding.getTextArgs.text = args.lyricText.replace("\"", "").trim()
        binding.textTitle.text=args.lyricTitle.replace("\"", "").trim()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
        observeData()
    }
    private fun initButton(){
        with(binding){
            editLyric.setOnClickListener {
                val action=FinishLyricFragmentDirections.actionFinishLyricFragmentToEditLyricFragment(args.lyricText,args.lyricTitle)
                findNavController().navigate(action)
            }
            backButton.setOnClickListener {
                val action=FinishLyricFragmentDirections.actionFinishLyricFragmentToPromptFragment()
                findNavController().navigate(action)
            }
            conBut.setOnClickListener {
                val action=FinishLyricFragmentDirections.actionFinishLyricFragmentToSelectBeatsFragment(args.lyricText,args.lyricTitle)
                findNavController().navigate(action)
            }
            refresh.setOnClickListener {
                binding.loadingLinearLayout.visibility=View.VISIBLE
                promptViewmodel.callApi(args.lyricText)
                promptViewmodel.callApi(args.lyricTitle)
            }
        }
    }

    private fun observeData(){
        promptViewmodel.apiMessage.observe(viewLifecycleOwner){
            if (it !=null){
                binding.loadingLinearLayout.visibility=View.GONE
                binding.getTextArgs.text=it.trim()
            }else{
                Toast.makeText(requireContext(),"Try One More Again",Toast.LENGTH_SHORT).show()
            }
        }
        promptViewmodel.apiMessageTitle.observe(viewLifecycleOwner){
            if (it !=null){
                binding.textTitle.text=it.replace("\"", "").trim()
            }else{
                Toast.makeText(requireContext(),"Try One More Again",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun onResume() {
        super.onResume()
        binding.getTextArgs.text=args.lyricText.replace("\"", "").trim()
        binding.textTitle.text=args.lyricTitle.replace("\"", "").trim()
    }
}