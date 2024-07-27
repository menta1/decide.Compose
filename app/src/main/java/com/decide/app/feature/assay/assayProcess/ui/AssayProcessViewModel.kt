package com.decide.app.feature.assay.assayProcess.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.assay.assayProcess.data.AssayProcessRepository
import com.decide.app.feature.assay.mainAssay.modals.QuestionAssay
import com.decide.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssayProcessViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AssayProcessRepository,

    ) : ViewModel() {

    private val assayId: Int = checkNotNull(savedStateHandle["idAssay"])

    init {
        Log.d("TAG", "userId = " + assayId.toString())
        getId(assayId)
    }

    private val _state: MutableStateFlow<AssayProcessState> =
        MutableStateFlow(AssayProcessState.Default)
    val state: StateFlow<AssayProcessState> = _state.asStateFlow()

    private val listAnswers: MutableList<Answers> = mutableListOf()
    private var listQuestions: List<QuestionAssay> = mutableListOf()
    private var currentProgress = 1

    fun onEvent(event: EventAssayProcess) {
        listAnswers.add(Answers(event.idQuestion, event.idAnswer))
        _state.update {
            if (listQuestions.size == event.idQuestion + 1) {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.saveResult(assayId, listAnswers)
                }
                AssayProcessState.End
            } else {
                currentProgress += 1
                AssayProcessState.AssayWithText(
                    listQuestions[currentProgress],
                    currentProgress.toFloat() / listQuestions.size.toFloat()
                )
            }
        }
    }

    private fun getId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val assay = repository.getAssay(id)) {
                is Resource.Error -> {
                    _state.update {
                        AssayProcessState.Error
                    }
                }

                is Resource.Success -> {
                    Log.d("TAG", "Resource.Success")
                    listQuestions = assay.data.countQuestions
                    _state.update {
                        AssayProcessState.AssayWithText(listQuestions.first(), 0f)
                    }
                }
            }
        }
    }

    private fun saveResult(){

    }
}