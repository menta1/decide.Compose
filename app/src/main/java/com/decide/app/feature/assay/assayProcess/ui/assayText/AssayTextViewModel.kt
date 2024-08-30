package com.decide.app.feature.assay.assayProcess.ui.assayText

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.assay.assayProcess.domain.useCase.GetAssayUseCase
import com.decide.app.feature.assay.assayProcess.domain.useCase.SaveResultUseCase
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.assay.assayProcess.ui.Answers
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
class AssayTextViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAssayUseCase: GetAssayUseCase,
    private val saveResultUseCase: SaveResultUseCase
) : ViewModel() {

    private val assayId: Int = checkNotNull(savedStateHandle["idAssay"])

    init {
        getId(assayId)
    }

    private val _state: MutableStateFlow<AssayTextState> =
        MutableStateFlow(AssayTextState.Loading)
    val state: StateFlow<AssayTextState> = _state.asStateFlow()

    private val listAnswer: MutableList<Answer> = mutableListOf()
    private val listAnswers: MutableList<Answers> = mutableListOf()
    private var listQuestions: List<QuestionAssay> = mutableListOf()
    private var currentProgress = 0

    fun onEvent(event: EventAssayText) {
        if (event.idAnswer.size == 1) {
            listAnswer.add(
                Answer(
                    idQuestion = event.idQuestion,
                    idAnswer = event.idAnswer.first(),
                    answerValue = event.answerValue.first()
                )
            )
        } else {
            listAnswers.add(
                Answers(
                    idQuestion = event.idQuestion,
                    idAnswer = event.idAnswer,
                    answerValue = event.answerValue
                )
            )
        }
        _state.update {
            if (listQuestions.size == event.idQuestion) {

                viewModelScope.launch(Dispatchers.IO) {
                    saveResultUseCase.invoke(
                        id = assayId,
                        answer = listAnswer,
                        answers = listAnswers)
                }

                AssayTextState.End(idAssay = assayId)
            } else {
                currentProgress++
                AssayTextState.Loaded(
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
                        AssayTextState.Error
                    }
                }

                is Resource.Success -> {
                    listQuestions = assay.data.questions
                    _state.update {
                        AssayTextState.Loaded(listQuestions.first(), 0f)
                    }
                }
            }
        }
    }
}