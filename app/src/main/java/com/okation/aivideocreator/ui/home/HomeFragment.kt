package com.okation.aivideocreator.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.okation.aivideocreator.R


import com.okation.aivideocreator.databinding.FragmentHomeBinding
import com.okation.aivideocreator.models.homesongmodel.SongModel
//import com.okation.aivideocreator.ui.HomeFragmentDirections
import com.okation.aivideocreator.ui.home.adapter.HomeAdapter
import com.okation.aivideocreator.ui.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding?=null
    private val binding get() = _binding!!

    private val viewModel : HomeViewModel by viewModels()

    private lateinit var adapter : HomeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentHomeBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //bunu sil ve test et çalışıyormu diye

        viewModel.getSongs()
        observeData()
        initButton()

        initAdapter()
        openBottomSheet()
        actionMediaPlayer()
    }

    //eğer veri gelmiyorsa onCreateView() içinde tanımla veriler görünüm oluşmadan gelmesi gerekiyor.
    private fun observeData(){
        viewModel.getSongs().observe(viewLifecycleOwner){songs ->
            if (songs != null && songs.isNotEmpty()) {
                adapter.setRapper(songs)
                with(binding){
                    recyclerView.visibility = View.VISIBLE
                    startItem.visibility=View.GONE
                    addRap2.visibility=View.VISIBLE
                    blurImageView.visibility=View.VISIBLE
                }
            }
        }
    }

    private fun initAdapter(){
        adapter= HomeAdapter(binding.recyclerView)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=GridLayoutManager(requireContext(),2)
    }

    private fun initButton(){
        with(binding){
            addRap2.setOnClickListener {
                val action= HomeFragmentDirections.actionHomeFragmentToPromptFragment()
                findNavController().navigate(action)
            }
            settings.setOnClickListener {
                val action= HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
                findNavController().navigate(action)
            }
            addRap.setOnClickListener {
                val action=HomeFragmentDirections.actionHomeFragmentToPromptFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun openBottomSheet(){
        adapter.bottomSheetClick={ songModel ->
            bottomSheetItemClick(songModel)
        }
    }

    private fun actionMediaPlayer(){
        adapter.setClickLoadData={
            val action=HomeFragmentDirections.actionHomeFragmentToSongFragment(musicUrl = it.songUrl, lyricTitle = it.songName, rapperName = it.rapperName, rapperPhoto = it.songImage,1)
            findNavController().navigate(action)
        }
    }

    private fun bottomSheetItemClick(songModel: SongModel){
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.fragment_bottom_sheet, null)

        val renameButton = view.findViewById<AppCompatButton>(R.id.rename)
        val deleteButton = view.findViewById<AppCompatButton>(R.id.delete)
        val shareButton = view.findViewById<AppCompatButton>(R.id.share)
        val cancelButton = view.findViewById<AppCompatButton>(R.id.cancel)

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, songModel.songName)
            shareIntent.putExtra(Intent.EXTRA_TEXT, songModel.songUrl)
            startActivity(Intent.createChooser(shareIntent, songModel.songName))
        }
        deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("DELETE MUSIC")
            builder.setMessage("ARE YOU SURE DELETE THIS MUSIC ?")

            builder.setPositiveButton("YES") { dialog, _ ->
                try {
                    viewModel.deleteSongId(songModel)
                    Toast.makeText(requireContext(), "DELETED ITEM", Toast.LENGTH_SHORT).show()
                }catch (e:IOException){
                    println(e.printStackTrace())
                }catch (e:java.lang.Exception){
                    println(e.printStackTrace())
                }
                dialog.dismiss()
            }

            builder.setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
        renameButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Title")
            val input = EditText(requireContext())
            input.hint = "Change Song Name..."
            builder.setView(input)

            builder.setPositiveButton("OKEY") { dialog, _ ->
                try {
                    val userInput = input.text.toString()
                    viewModel.updateSong(
                        SongModel(
                            id = songModel.id,
                            songUrl = songModel.songUrl,
                            songImage = songModel.songImage,
                            songName = songModel.rapperName,
                            rapperName = userInput
                        )
                    )
                    Toast.makeText(requireContext(), "SUCCES UPDATE", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    println(e.printStackTrace())
                } catch (e:java.lang.Exception){
                    println(e.printStackTrace())
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("CANCEL"){dialog, _ ->
                Toast.makeText(requireContext(), "UPDATE CANCELED", Toast.LENGTH_SHORT).show()
                dialog.cancel()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
        adapter.stopMedia()
    }
}

