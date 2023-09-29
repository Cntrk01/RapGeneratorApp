package com.okation.aivideocreator.ui.rapper.adapter

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.okation.aivideocreator.R
import com.okation.aivideocreator.databinding.SelectRapperItemBinding
import com.okation.aivideocreator.models.rappermodel.VoiceModel
import com.okation.aivideocreator.ui.rapper.model.RapperSetModel

class RapperAdapter(private val recyclerView: RecyclerView) : RecyclerView.Adapter<RapperAdapter.RapperViewHolder>() {

    var mediaPlayer = MediaPlayer()

    private var voiceModel = listOf<VoiceModel>()
    private var imageUrl = listOf<Int>()
    private var rapperVoiceList : ArrayList<String>? =null
    var onContinue: ((RapperSetModel) -> Unit)? = null

    private var selectedPosition: Int = -1

    @SuppressLint("NotifyDataSetChanged")
    fun setRapper(listing:List<VoiceModel>, imageString: ArrayList<Int>){
        this.voiceModel=listing
        this.imageUrl=imageString
        notifyDataSetChanged()
    }
//    // Veri listesini güncellemek için bu yöntemi kullanın
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: ArrayList<String>) {
       this.rapperVoiceList=newData
       notifyDataSetChanged()
    }

    inner class RapperViewHolder(val binding : SelectRapperItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RapperViewHolder {
        val inf=SelectRapperItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RapperViewHolder(inf)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RapperViewHolder, position: Int) {
        val pos=voiceModel[position]
        val imagePos=imageUrl[position]
        val rapperVoice1 = rapperVoiceList?.get(position)

        with(holder.binding){
            rapperName.text=pos.name
            Glide.with(holder.itemView).load(imagePos).into(rapperImage)
            playRapSound.setOnClickListener {
                if (selectedPosition != -1 && selectedPosition != position) {
                    // Önceki seçili öğenin görünümünü sıfırla
                    val prevSelectedHolder = recyclerView.findViewHolderForAdapterPosition(selectedPosition)
                    prevSelectedHolder?.let { holder ->
                        //burada holder ı casting yaparak sınıfın görünümüne erişmesini sağlıyor böylelikle görünüm değiştirebiliyorum.
                        val binding = (holder as RapperAdapter.RapperViewHolder).binding
                        binding.playRapSound.isVisible = true
                        binding.stopRapSound.isVisible = false
                        binding.cardView.strokeWidth = 0
                        binding.cardView.strokeColor = ContextCompat.getColor(holder.itemView.context, R.color.white)
                    }
                }
                // Şu anki öğenin görünümünü ayarla
                playRapSound.isVisible = false
                stopRapSound.isVisible = true
                val premiumColor = ContextCompat.getColor(holder.itemView.context, R.color.pink)
                cardView.strokeWidth = 9
                cardView.strokeColor = premiumColor
                selectedPosition = holder.adapterPosition
                listenMedia(rapperVoice1!!)
                val rapMod=RapperSetModel(rapperVoice1,pos.name,imagePos)
                onContinue?.invoke(rapMod)
            }
            stopRapSound.setOnClickListener {
                playRapSound.isVisible = true
                stopRapSound.isVisible = false
                cardView.strokeWidth = 0
                cardView.strokeColor = R.color.white
                selectedPosition=-1
                stopMedia()
            }
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
    private fun listenMedia(clickedItem: String) {
        stopMedia()
        //her tıklandıgı zaman mediaPlayer için yeni nesne olusması gerekiyor
        mediaPlayer= MediaPlayer()
        mediaPlayer.apply {
            try {
                setDataSource(clickedItem)

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
        return voiceModel.size.coerceAtMost(8)
    }
}