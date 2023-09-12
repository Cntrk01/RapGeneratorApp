package com.okation.aivideocreator.ui.prompt.promtrepository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.okation.aivideocreator.models.gptmodel.CompletionRequest
import com.okation.aivideocreator.models.gptmodel.CompletionResponse
import com.okation.aivideocreator.network.OpenAiApi
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject

class PromptRepository @Inject constructor(private val api: OpenAiApi) {
    suspend fun getEditedText(res: CompletionRequest) = api.getEditedText(res)


    private val _apiMessage: MutableLiveData<String> = MutableLiveData()
    val apiMessage : MutableLiveData<String> get() = _apiMessage
    private val _apiMessageTitle: MutableLiveData<String> = MutableLiveData()
    val apiMessageTitle: LiveData<String> get() = _apiMessageTitle

    private val _counters = MutableLiveData<Int>()
    val listenCounter: LiveData<Int> get() = _counters
    private var job: Job? = null

    init {
        _counters.value = 10
    }

    suspend fun callApi(question: String) {
        val title= "$question (Can you give a short title about the content?)"

        val request = CompletionRequest("text-davinci-003", question, 100)
        val requestTitle=CompletionRequest("text-davinci-003", title, 100)
        try {
            val response = api.getEditedText(request)
            val responseTitle=api.getEditedText(requestTitle)

            handleApiResponse(response)
            handleApiResponse2(responseTitle)
        } catch (e: Exception) {
            Log.e("except", e.toString())
        }
    }

    private fun handleApiResponse(response: Response<CompletionResponse>) {
        if (response.isSuccessful) {
            response.body()?.let {
                val result = it.choices.firstOrNull()?.text
                if (result != null) {
                    _apiMessage.postValue(result!!)
                }
            }
        }

    }

    private fun handleApiResponse2(response: Response<CompletionResponse>) {
        if (response.isSuccessful) {
            response.body()?.let {
                val result = it.choices.firstOrNull()?.text
                if (result != null) {
                    _apiMessageTitle.postValue(result!!)
                }
            }
        }

    }

    fun startCounter() {
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(1000)
                _counters.postValue(_counters.value?.minus(1))
            }
        }
    }

    fun stopCounter() {
        job?.cancel()
    }
}