package com.okation.aivideocreator.ui.home.adapter

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.HomeRowItemBinding
import com.okation.aivideocreator.models.homesongmodel.SongModel
import com.okation.aivideocreator.ui.beats.adapter.BeatsAdapter

class HomeAdapter(private val recyclerView: RecyclerView) :RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var selectedPosition: Int = -1
    private var mediaPlayer = MediaPlayer()

    var bottomSheetClick : ((SongModel) -> Unit)? = null
    var setClickLoadData : ((SongModel)->Unit)? =null

    var list = listOf<SongModel>()
    @SuppressLint("NotifyDataSetChanged")
    fun setRapper(its:List<SongModel>){
        this.list=its
        notifyDataSetChanged()
    }


    inner class HomeViewHolder(val binding : HomeRowItemBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inf=HomeRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(inf)
    }


    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val pos=list[position]

        with(holder.binding){
            rapperImage.setImageResource(pos.songImage)

            songName.text = pos.rapperName

            playRapSound.setOnClickListener {
                listenMedia(pos)

                if (selectedPosition != -1 && selectedPosition != position) {
                    // Önceki seçili öğenin görünümünü sıfırla
                    val prevSelectedHolder = recyclerView.findViewHolderForAdapterPosition(selectedPosition)
                    prevSelectedHolder?.let { holder ->
                        //burada holder ı casting yaparak sınıfın görünümüne erişmesini sağlıyor böylelikle görünüm değiştirebiliyorum.
                        val binding = (holder as HomeAdapter.HomeViewHolder).binding
                        with(binding){
                            playRapSound.isVisible = true
                            stopRapSound.isVisible = false
                            cardView.strokeWidth = 0
                            cardView.strokeColor = ContextCompat.getColor(holder.itemView.context, R.color.white)
                        }
                    }
                }
                selectedPosition = holder.adapterPosition

                stopRapSound.visibility=View.VISIBLE
                playRapSound.visibility=View.INVISIBLE
                cardView.strokeWidth = 8
                cardView.strokeColor = ContextCompat.getColor(holder.itemView.context, R.color.pink)

            }
            stopRapSound.setOnClickListener {
                stopMedia()
                stopRapSound.visibility=View.INVISIBLE
                playRapSound.visibility=View.VISIBLE
                cardView.strokeWidth = 0
                cardView.strokeColor = ContextCompat.getColor(holder.itemView.context, R.color.white)
                selectedPosition=-1
            }
        }
        holder.binding.songSettings.setOnClickListener {
            bottomSheetClick?.invoke(pos)
        }
        holder.itemView.setOnClickListener {
            setClickLoadData?.invoke(pos)
        }
    }

    fun stopMedia() {
        try {
            //bu if blogunu silince müzik hiç çalmıyor.
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()

            }
        }catch (e:java.lang.IllegalStateException){
            println(e.printStackTrace())
        }
    }
    private fun listenMedia(clickedItem: SongModel) {
        stopMedia()
        //her tıklandıgı zaman mediaPlayer için yeni nesne olusması gerekiyor
        mediaPlayer= MediaPlayer()
        mediaPlayer.apply {
            try {

                setDataSource(clickedItem.songUrl)
                setOnPreparedListener {

                    it.start()

                }
                prepareAsync()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}