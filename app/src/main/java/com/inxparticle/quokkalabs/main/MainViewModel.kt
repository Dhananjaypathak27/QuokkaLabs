package com.inxparticle.quokkalabs.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inxparticle.quokkalabs.utils.DispatcherProvider
import com.inxparticle.quokkalabs.utils.Resource
import com.inxparticle.quokkalabs.data.model.BrakingBatApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class BrakingBatEvent {
        class Success(val result: ArrayList<BrakingBatApiResponse.BrakingBatApiResponseItem>) : BrakingBatEvent()
        class Failure(val errorText: String) : BrakingBatEvent()
        object Loading : BrakingBatEvent()
        object Empty : BrakingBatEvent()
    }

    private val _placeHolder = MutableStateFlow<BrakingBatEvent>(BrakingBatEvent.Empty)
    val visibilityOfProgress = MutableStateFlow(true)

    val _brakingBatList = MutableStateFlow<ArrayList<BrakingBatApiResponse.BrakingBatApiResponseItem>>(ArrayList())

    fun fetchBrakingBatAPIData(
    ) {

        viewModelScope.launch(dispatchers.io) {
            _placeHolder.value = BrakingBatEvent.Loading
            when (val placeHolderResponse = repository.getCharacter()) {
                is Resource.Error -> {
                    visibilityOfProgress.value = false
                    _placeHolder.value =
                        BrakingBatEvent.Failure(
                            placeHolderResponse.message ?: "Error Occurred"
                        )
                }
                is Resource.Success -> {
                    visibilityOfProgress.value = false
                    if (placeHolderResponse.data != null) {
                        BrakingBatEvent.Success(placeHolderResponse.data)
                        _brakingBatList.value = placeHolderResponse.data
                    } else {
                        _placeHolder.value = BrakingBatEvent.Failure("UnExpected Error")
                    }
                }
            }
        }
    }


}