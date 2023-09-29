package com.okation.aivideocreator.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.okation.aivideocreator.databinding.FragmentBottomSheetBinding
import com.okation.aivideocreator.models.homesongmodel.SongModel
import com.okation.aivideocreator.ui.home.viewmodel.HomeViewModel
import java.io.IOException


class BottomSheetFragment() : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val homeFragment = HomeFragment()
    val songModelList = ArrayList<SongModel>()
    private val viewModel : HomeViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickItemData()

        binding.apply {
            songModelList.forEach {its->
                share.setOnClickListener {
                   shareItem(its)
                }
                cancel.setOnClickListener {
                    dismiss()
                }
                rename.setOnClickListener {
                    renameItem(its)
                }
                delete.setOnClickListener {
                    deleteItem(its)
                }
            }
        }

    }
    private fun deleteItem(songModel: SongModel){
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
    private fun shareItem(its:SongModel){
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, its.songName)
        shareIntent.putExtra(Intent.EXTRA_TEXT, its.songUrl)
        startActivity(Intent.createChooser(shareIntent, its.songName))
    }
    private fun renameItem(songModel: SongModel){
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

    private fun clickItemData() {
        homeFragment.setOnItemSelectedListener(object : HomeFragment.OnItemSelectedListener {
            override fun onItemSelected(songModel: SongModel) {
                songModelList.clear()
                songModelList.addAll(listOf(songModel))
            }
        })
    }
}