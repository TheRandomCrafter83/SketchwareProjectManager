package com.coderz.f1.sketchwareprojectmanager.data

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val sketchwareUri: String = ""
)
