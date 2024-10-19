package com.decide.app.feature.profile.settings.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.decide.app.feature.profile.settings.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val repository: SettingsRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsScreenState.Initial)
    val state: StateFlow<SettingsScreenState> = _state.asStateFlow()

    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            SettingsScreenEvent.LogOut -> {
                _state.update { SettingsScreenState.Loading }
                viewModelScope.launch(Dispatchers.IO) {
                    repository.logOut {
                        _state.update { SettingsScreenState.LogOut }
                    }
                }
            }

            SettingsScreenEvent.PrivacyPolicy -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(LINK_PRIVACY_POLICY))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            SettingsScreenEvent.SendEmail -> {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(EMAIL))
                    this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }

                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        const val LINK_PRIVACY_POLICY = "https://decideapp.tilda.ws/"
        const val EMAIL = "decideapp@yandex.ru"
    }
}