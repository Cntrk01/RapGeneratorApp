package com.okation.aivideocreator.ui.beats.adapter

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.SelectBeatItemBinding
import com.okation.aivideocreator.models.beatsmodel.BackingTrack

@Suppress("DEPRECATION")
class BeatsAdapter(private val recyclerView: RecyclerView) : RecyclerView.Adapter<BeatsAdapter.BeatsViewHolder>() {

    var listBeats = listOf<BackingTrack>()
    var onContinue: ((BackingTrack) -> Unit)? = null

    var mediaPlayer = MediaPlayer()
    private var selectedPosition: Int = -1


    @SuppressLint("NotifyDataSetChanged")
    fun setBeats(beats: List<BackingTrack>) {
        this.listBeats = beats
        notifyDataSetChanged()
    }


    inner class BeatsViewHolder(val binding: SelectBeatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeatsViewHolder {
        val inf = SelectBeatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeatsViewHolder(inf)
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun onBindViewHolder(holder: BeatsViewHolder, position: Int) {
        val pos = listBeats[position]

        holder.binding.beatsName.text = pos.name

        with(holder.binding) {
            playButton.setOnClickListener {
                if (selectedPosition != -1 && selectedPosition != position) {
                    // Önceki seçili öğenin görünümünü sıfırla
                    val prevSelectedHolder = recyclerView.findViewHolderForAdapterPosition(selectedPosition)
                    prevSelectedHolder?.let { holder ->
                        //burada holder ı casting yaparak sınıfın görünümüne erişmesini sağlıyor böylelikle görünüm değiştirebiliyorum.
                        val binding = (holder as BeatsAdapter.BeatsViewHolder).binding
                        binding.playButton.isVisible = true
                        binding.stopButton.isVisible = false
                        binding.imageSoundWave.isVisible = false
                        binding.cardView.strokeWidth = 0
                        binding.cardView.strokeColor = ContextCompat.getColor(holder.itemView.context, R.color.white)
                    }
                }
                playButton.isVisible = false
                stopButton.isVisible = true
                imageSoundWave.isVisible = true
                val premiumColor = ContextCompat.getColor(holder.itemView.context, R.color.pink)
                cardView.strokeWidth = 5
                cardView.strokeColor = premiumColor

                selectedPosition = holder.adapterPosition

                listenMedia(clickedItemUrl = pos)
                pos.url?.let {
                    onContinue?.invoke(pos)
                }
            }
            stopButton.setOnClickListener {
                playButton.isVisible = true
                stopButton.isVisible = false
                imageSoundWave.isVisible = false
                cardView.strokeWidth = 0
                cardView.strokeColor = R.color.white
                selectedPosition=-1
                stopMedia()
            }
        }
    }

    fun stopMedia() {
        try {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
            }
        } catch (e: java.lang.IllegalStateException) {
            e.printStackTrace()
        }
    }

    private fun listenMedia(clickedItemUrl: BackingTrack) {
        stopMedia()
        mediaPlayer = MediaPlayer()
        mediaPlayer.apply {
            try {
                setDataSource(clickedItemUrl.url)
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
        return listBeats.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
