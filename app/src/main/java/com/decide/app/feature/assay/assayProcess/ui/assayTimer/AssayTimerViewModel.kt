package com.decide.app.feature.assay.assayProcess.ui.assayTimer

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.assay.assayMain.modals.QuestionAssay
import com.decide.app.feature.assay.assayProcess.domain.useCase.GetAssayUseCase
import com.decide.app.feature.assay.assayProcess.domain.useCase.SaveResultUseCase
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.assay.assayProcess.ui.Answers
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
class AssayTimerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAssayUseCase: GetAssayUseCase,
    private val saveResultUseCase: SaveResultUseCase
) : ViewModel() {

    private val assayId: Int = savedStateHandle["idAssay"] ?: -1

    init {
        getId(assayId)
    }

    private val _state: MutableStateFlow<AssayTimerState> =
        MutableStateFlow(AssayTimerState.Loading)
    val state: StateFlow<AssayTimerState> = _state.asStateFlow()

    private val timeLeft = MutableStateFlow<Long>(0)
    var timeForAssay: Long = 0
    val countValue: MutableStateFlow<Long>
        get() = timeLeft

    private val timer by lazy {
        object : CountDownTimer(timeForAssay * 1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timeLeft.update {
                    millisUntilFinished / 1000
                }
            }

            override fun onFinish() {
                Log.d("TAG", "timeForAssay = $timeForAssay")
                _state.update {
                    AssayTimerState.TimeOver
                }
            }
        }
    }


    private val listAnswer: MutableList<Answer> = mutableListOf()
    private val listAnswers: MutableList<Answers> = mutableListOf()
    private var listQuestions: List<QuestionAssay> = mutableListOf()
    private var currentProgress = 0

    fun onEvent(event: EventAssayTimer) {

        when (event) {
            EventAssayTimer.StartTimer -> {
                _state.update {
                    AssayTimerState.Loaded(listQuestions.first(), 0f)
                }
                timer.start()
            }

            is EventAssayTimer.selectedAnswer -> {
                countAnswers(event)

                _state.update {
                    if (listQuestions.size == event.idQuestion) {
                        viewModelScope.launch(Dispatchers.IO) {
                            saveResultUseCase.invoke(
                                id = assayId, answer = listAnswer, answers = listAnswers
                            )
                        }
                        timer.cancel()
                        AssayTimerState.End(idAssay = assayId)
                    } else {
                        currentProgress++
                        AssayTimerState.Loaded(
                            listQuestions[currentProgress],
                            currentProgress.toFloat() / listQuestions.size.toFloat()
                        )
                    }
                }
            }
        }

    }

    private fun countAnswers(event: EventAssayTimer.selectedAnswer) {
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
    }


    private fun getId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val assay = getAssayUseCase.invoke(id)) {
                is Resource.Error -> {
                    _state.update {
                        AssayTimerState.Error
                    }
                }

                is Resource.Success -> {
                    listQuestions = assay.data.questions
                    timeForAssay = assay.data.timeForTest
                    _state.update {
                        AssayTimerState.IsReady
                    }
                }
            }
        }
    }
}