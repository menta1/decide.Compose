package com.decide.app.feature.assay.assayProcess.ui.assayText

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.account.adsManager.AdsManager
import com.decide.app.activity.ShowAds
import com.decide.app.feature.assay.assayMain.modals.QuestionAssay
import com.decide.app.feature.assay.assayProcess.domain.useCase.GetAssayUseCase
import com.decide.app.feature.assay.assayProcess.domain.useCase.SaveResultUseCase
import com.decide.app.feature.assay.assayProcess.ui.Answer
import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AssayTextViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAssayUseCase: GetAssayUseCase,
    private val saveResultUseCase: SaveResultUseCase,
    private val adsManager: AdsManager
) : ViewModel() {

    private val assayId: Int = checkNotNull(savedStateHandle["idAssay"])

    init {
        getId(assayId)
    }

    private var adsLoaded: Boolean = false
    private lateinit var ads: ShowAds

    private val _state: MutableStateFlow<AssayTextState> =
        MutableStateFlow(AssayTextState.Loading)
    val state: StateFlow<AssayTextState> = _state.asStateFlow()

    private val listAnswer: MutableList<Answer> = mutableListOf()
    private val listAnswers: MutableList<Answers> = mutableListOf()
    private var listQuestions: List<QuestionAssay> = mutableListOf()
    private var currentProgress = 0

    fun onEvent(event: EventAssayText) {
        when (event) {
            is EventAssayText.Ad -> {
                viewModelScope.launch {
                    adsManager.isShowFullScreenAds().collect {
                        if (it) {
                            ads = event.ads
                            ads.loadAds(
                                onAdLoaded = {
                                    adsLoaded = true
                                },
                                onAdFailedToLoad = {
                                    adsLoaded = false
                                }
                            )
                        }
                    }
                }
            }

            is EventAssayText.AssayText -> {
                if (event.idAnswer.size == 1) {
                    listAnswer.add(//Если возможен один ответ
                        Answer(
                            idQuestion = event.idQuestion,
                            idAnswer = event.idAnswer.first(),
                            answerValue = event.answerValue.first()
                        )
                    )
                } else {
                    listAnswers.add(//Если возможены несколько ответов
                        Answers(
                            idQuestion = event.idQuestion,
                            idAnswer = event.idAnswer,
                            answerValue = event.answerValue
                        )
                    )
                }

                if (listQuestions.size == event.idQuestion) {
                    viewModelScope.launch(Dispatchers.IO) {
                        saveResultUseCase.invoke(
                            id = assayId,
                            answer = listAnswer,
                            answers = listAnswers
                        )
                    }

                    if (adsLoaded) {
                        ads.showAds(
                            onAdShown = {
                                _state.update {
                                    ads.clearAds()
                                    AssayTextState.End(idAssay = assayId)
                                }
                            },
                            onAdImpression = {
                                _state.update {
                                    ads.clearAds()
                                    AssayTextState.End(idAssay = assayId)
                                }
                            },
                            onAdFailedToShow = {
                                _state.update {
                                    ads.clearAds()
                                    AssayTextState.End(idAssay = assayId)
                                }
                            },
                            onAdDismissed = {
                                _state.update {
                                    ads.clearAds()
                                    AssayTextState.End(idAssay = assayId)
                                }
                            },
                            onAdClicked = {
                                _state.update {
                                    ads.clearAds()
                                    AssayTextState.End(idAssay = assayId)
                                }
                            }
                        )
                    } else {
                        _state.update {
                            AssayTextState.End(idAssay = assayId)
                        }
                    }
                } else {
                    currentProgress++
                    _state.update {
                        AssayTextState.Loaded(
                            listQuestions[currentProgress],
                            currentProgress.toFloat() / listQuestions.size.toFloat()
                        )
                    }

                }

            }

            EventAssayText.TryAgain -> {
                getId(assayId)
            }
        }

    }

    private fun getId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val assay = getAssayUseCase.invoke(id)) {
                is Resource.Error -> {
                    when (assay.error) {
                        is DecideException.NoInternet -> {
                            _state.update {
                                AssayTextState.NetworkError
                            }
                        }

                        else -> {
                            _state.update {
                                AssayTextState.Error
                            }
                        }
                    }
                }

                is Resource.Success -> {
                    listQuestions = assay.data.questions
                    Timber.tag("TAG").d("listQuestions $listQuestions")
                    _state.update {
                        AssayTextState.Loaded(listQuestions.first(), 0f)
                    }
                }
            }
        }
    }
}