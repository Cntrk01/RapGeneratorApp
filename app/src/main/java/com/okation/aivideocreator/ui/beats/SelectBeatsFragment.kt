package com.okation.aivideocreator.ui.beats

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.okation.aivideocreator.R
import com.okation.aivideocreator.models.beatsmodel.BackingTrack
import com.okation.aivideocreator.databinding.FragmentSelectBeatsBinding
import com.okation.aivideocreator.ui.beats.adapter.BeatsAdapter
import com.okation.aivideocreator.ui.beats.viewmodel.SelectBeatsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SelectBeatsFragment : Fragment() {
    private var _binding: FragmentSelectBeatsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SelectBeatsViewModel by viewModels()


    private val args: SelectBeatsFragmentArgs by navArgs()


    private lateinit var adapter: BeatsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectBeatsBinding.inflate(inflater, container, false)
        Log.e("lyricText",args.lyricText)
        Log.e("lyricTitle",args.lyricTitle)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        binding.backButton.setOnClickListener {
            val action=SelectBeatsFragmentDirections.actionSelectBeatsFragmentToFinishLyricFragment(args.lyricText,args.lyricTitle)
            findNavController().navigate(action)
        }
        observeBeats()
    }

    private fun initAdapter() {
        adapter = BeatsAdapter(binding.recyclerView)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.onContinue={backingTrack ->

            if (backingTrack.url == ""){
                Toast.makeText(requireContext(),"Choose One Item",Toast.LENGTH_SHORT).show()
            }else{
                navigationFragment(backingTrack)
                binding.continueButton12.setBackgroundResource(R.drawable.premium_bg)
            }
        }
    }
    private fun navigationFragment(its:BackingTrack){
        binding.continueButton12.setOnClickListener {
            //continue yaptıgı an durduruyor bu. ondestroy içinde vs yaptım fakat çalışmadı.
            adapter.mediaPlayer.stop()
            adapter.mediaPlayer.release()
            val action = SelectBeatsFragmentDirections.actionSelectBeatsFragmentToRapperFragment(its.uuid!!, args.lyricText,args.lyricTitle,its.bpm!!)
            findNavController().navigate(action)

        }
    }


    private fun observeBeats() {
        viewModel.beats.observe(viewLifecycleOwner) { resource ->
            if (resource.backing_tracks.isNotEmpty()) {
                adapter.setBeats(resource.backing_tracks)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}