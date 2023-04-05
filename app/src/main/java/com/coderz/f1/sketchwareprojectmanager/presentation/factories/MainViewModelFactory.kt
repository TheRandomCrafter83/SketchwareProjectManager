package com.coderz.f1.sketchwareprojectmanager.presentation.factories

import android.app.Application
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coderz.f1.sketchwareprojectmanager.data.AppSettings
import com.coderz.f1.sketchwareprojectmanager.presentation.MainViewModel

class MainViewModelFactory(private val application: Application, private val dataStore: DataStore<AppSettings>, private val directoryPicker: ActivityResultLauncher<Uri?>): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application, dataStore, directoryPicker) as T
    }
}