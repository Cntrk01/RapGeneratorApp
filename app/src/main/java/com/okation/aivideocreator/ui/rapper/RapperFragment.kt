package com.okation.aivideocreator.ui.rapper

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.FragmentRapperBinding
import com.okation.aivideocreator.models.rappermodel.VoiceModel
import com.okation.aivideocreator.ui.rapper.adapter.RapperAdapter
import com.okation.aivideocreator.ui.rapper.model.RapperSetModel
import com.okation.aivideocreator.ui.rapper.viewmodel.RapperViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RapperFragment : Fragment() {
    private var _binding : FragmentRapperBinding?=null
    private val binding get() = _binding!!

    private val viewModel : RapperViewModel by viewModels()
    private lateinit var adapter: RapperAdapter
    private val args : RapperFragmentArgs by navArgs()

    private val image= arrayListOf<Int>()
    var list = ArrayList<String>()

    var rapperName=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentRapperBinding.inflate(inflater,container,false)
        initAdapter()
        addImage()
        observeRapper()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonClickItem()

    }

    private fun buttonClickItem(){
        adapter.onContinue={
            continueButton(it)
            binding.continueButton.setBackgroundResource(R.drawable.premium_bg)
        }

        binding.backButton.setOnClickListener {
            val action=RapperFragmentDirections.actionRapperFragmentToSelectBeatsFragment(args.lyricText,args.lyricTitle)
            findNavController().navigate(action)
        }
    }

    private fun continueButton(rapperVoice:RapperSetModel){
        binding.continueButton.setOnClickListener {
             if (rapperVoice.rapperName != ""){
                 //continue yaptıgı an durduruyor bu. ondestroy içinde vs yaptım fakat çalışmadı.
                 adapter.mediaPlayer.stop()
                 adapter.mediaPlayer.release()
                 val intent=RapperFragmentDirections.actionRapperFragmentToGeneratingFragment(
                     args.beatsUid,
                     args.lyricText,
                     args.lyricTitle,
                     args.bpm,
                     rapperVoice.rapperVoiceUrl,
                     rapperVoice.rapperName,
                     rapperVoice.rapperPhoto
                 )
                 findNavController().navigate(intent)
             }
        }
    }

    private fun initAdapter(){
        adapter= RapperAdapter(binding.recyclerView)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=GridLayoutManager(requireContext(),2)

    }


    private fun observeRapper(){
        viewModel.getVoices()
        //önce viewmodelden repcileri uid ile getirdim.
        viewModel.voices.observe(viewLifecycleOwner){datax->
            adapter.setRapper(datax.take(8),image)
            datax.forEach {
                viewModel.getRapperVoice(it.voicemodel_uuid)
            }
        }
        //bu kısımda çok hata aldım . adaptere set ettiğimde işte pozition alırken size 0 diyordu veriler geç geldiğiiçin set etmiyordu
        //bende size 8 olana kadar listeyi set etmesin dedim böylelikle problemi çözdüm
        viewModel.rapperVoice.observe(viewLifecycleOwner){
            it.forEach {its1->
                if (its1.url !=""){
                    list.add(its1.url.toString())
                    if (list.size==8){
                        adapter.updateData(list)
                    }
                }
            }
        }
    }

    private fun addImage(){
        image.add(R.drawable.img_rapper1)
        image.add(R.drawable.img_rapper2)
        image.add(R.drawable.img_rapper3)
        image.add(R.drawable.img_rapper4)
        image.add(R.drawable.img_rapper5)
        image.add(R.drawable.img_rapper6)
        image.add(R.drawable.img_rapper7)
        image.add(R.drawable.img_rapper8)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}