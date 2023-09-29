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
import com.okation.aivideocreator.databinding.FragmentGeneratingLyricBinding
import com.okation.aivideocreator.ui.prompt.promptviewmodel.PromptGptViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GeneratingLyricFragment : Fragment() {
    private var _binding : FragmentGeneratingLyricBinding?=null
    private val binding get() = _binding!!
    private val args : GeneratingLyricFragmentArgs by navArgs()
    private val promptViewmodel : PromptGptViewModel by viewModels()
    private var message=""
    private var title=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentGeneratingLyricBinding.inflate(inflater,container,false)
        //null yaptık ki prompt fragmentten 2.ve sonrası gelişlerinde ilk seferki değeri atıyordu ve otomatik else çalışıyordu
        //apiden yeni veri almadan.Eski veri kalıyordu.Görünüm her oluştugunda null olcak . onViewCreated çalışıcak apiye istek atıp veriyi alcak.
        promptViewmodel.apiMessage.value=null
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelProcess()
        binding.apply {
            getText.text=args.getTextName

            backButton.setOnClickListener {
                val action= GeneratingLyricFragmentDirections.actionGeneratingLyricFragmentToPromptFragment()
                findNavController().navigate(action)
            }
        }
        getGptText()
    }

    private fun getGptText() {
        with(promptViewmodel){
            apiMessage.observe(viewLifecycleOwner) { messageList ->
                if (messageList != null) {
                   message=messageList
                }else{
                    message="Lyric lyric lyric lyric"
                }
            }
            apiMessageTitle.observe(viewLifecycleOwner) {
                if (it !=null){
                    title = it
                }else{
                    title="Music Titleee"
                }
                checkTitleAndPrompt()
            }
        }
    }

    private fun checkTitleAndPrompt(){
        if (message != "" && title !="") {
            val action = GeneratingLyricFragmentDirections.actionGeneratingLyricFragmentToFinishLyricFragment(message,title)
            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Not Generate Prompt", Toast.LENGTH_SHORT).show()
            val action = GeneratingLyricFragmentDirections.actionGeneratingLyricFragmentToPromptFragment()
            findNavController().navigate(action)
        }
        promptViewmodel.stopCounter()
    }

    private fun viewModelProcess(){
        with(promptViewmodel){
            callApi(args.getTextName)
            startCounter()
            listenCounter.observe(viewLifecycleOwner){
                if (it == 0){
                    Toast.makeText(requireContext(),"Lyric Not Created",Toast.LENGTH_SHORT).show()
                    val bind=GeneratingLyricFragmentDirections.actionGeneratingLyricFragmentToPromptFragment()
                    findNavController().navigate(bind)
                }
                binding.timeText.text=it.toString()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
        promptViewmodel.stopCounter()
    }

}