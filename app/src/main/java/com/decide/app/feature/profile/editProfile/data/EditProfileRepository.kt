package com.decide.app.feature.profile.editProfile.data

import com.decide.app.feature.profile.editProfile.modal.ProfileEdit

interface EditProfileRepository {
    suspend fun getProfile(): ProfileEdit?
}