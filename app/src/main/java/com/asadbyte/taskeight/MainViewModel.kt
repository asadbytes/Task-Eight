package com.asadbyte.taskeight

import androidx.lifecycle.*
import com.asadbyte.taskeight.apiService.ApiService
import com.asadbyte.taskeight.model.ApiResult
import com.asadbyte.taskeight.model.HttpBinResponse
import com.asadbyte.taskeight.network.UserRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import com.asadbyte.taskeight.model.DummyUser

class MainViewModel : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    private var repository: UserRepository? = null

    fun setApiService(apiService: ApiService) {
        repository = UserRepository(apiService)
    }

    fun performGet() {
        repository?.let { repo ->
            viewModelScope.launch {
                _uiState.value = UiState(isLoading = true)
                val result = repo.get()
                _uiState.value = UiState(result = result)
            }
        }
    }

    fun performPost(dummyUser: DummyUser) {
        repository?.let { repo ->
            viewModelScope.launch {
                _uiState.value = UiState(isLoading = true)
                val result = repo.add(dummyUser)
                _uiState.value = UiState(result = result)
            }
        }
    }

    fun performPut(dummyUser: DummyUser) {
        repository?.let { repo ->
            viewModelScope.launch {
                _uiState.value = UiState(isLoading = true)
                val result = repo.update(dummyUser)
                _uiState.value = UiState(result = result)
            }
        }
    }

    fun performDelete(userId: Int) {
        repository?.let { repo ->
            viewModelScope.launch {
                _uiState.value = UiState(isLoading = true)
                val result = repo.delete(userId)
                _uiState.value = UiState(result = result)
            }
        }
    }
}

data class UiState(
    val isLoading: Boolean = false,
    val result: ApiResult<HttpBinResponse>? = null,
    val error: String? = null
)
