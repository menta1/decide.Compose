package com.decide.app.feature.assay.assayProcess.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.assay.assayProcess.domain.useCase.GetAssayUseCase
import com.decide.app.feature.assay.assayProcess.domain.useCase.SaveResultUseCase
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
    private val getAssayUseCase: GetAssayUseCase,
    private val saveResultUseCase: SaveResultUseCase
) : ViewModel() {

    private val assayId: Int = checkNotNull(savedStateHandle["idAssay"])

    init {
        getId(assayId)
    }

    private val _state: MutableStateFlow<AssayProcessState> =
        MutableStateFlow(AssayProcessState.Loading)
    val state: StateFlow<AssayProcessState> = _state.asStateFlow()

    private val listAnswers: MutableList<Answers> = mutableListOf()
    private var listQuestions: List<QuestionAssay> = mutableListOf()
    private var currentProgress = 1

    fun onEvent(event: EventAssayProcess) {
        listAnswers.add(
            Answers(
                idQuestion = event.idQuestion,
                idAnswer = event.idAnswer,
                answerValue = event.answerValue
            )
        )
        _state.update {
            if (listQuestions.size == event.idQuestion + 1) {
                viewModelScope.launch(Dispatchers.IO) {
                    saveResultUseCase.invoke(
                        id = assayId,
                        answers = listAnswers,
                        onResult = { result ->
                            when (result) {
                                true -> {}
                                false -> {}
                            }
                        })
                }
                AssayProcessState.End(idAssay = assayId)
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
            when (val assay = getAssayUseCase.invoke(id)) {
                is Resource.Error -> {
                    _state.update {
                        AssayProcessState.Error
                    }
                }

                is Resource.Success -> {
                    listQuestions = assay.data.countQuestions
                    _state.update {
                        AssayProcessState.AssayWithText(listQuestions.first(), 0f)
                    }
                }
            }
        }
    }
}