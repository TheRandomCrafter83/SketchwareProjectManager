package com.coderz.f1.sketchwareprojectmanager.presentation

import android.app.Application
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.datastore.core.DataStore
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.coderz.f1.sketchwareprojectmanager.data.AppSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(application: Application, private val dataStore: DataStore<AppSettings>, private val directoryPicker: ActivityResultLauncher<Uri?>) : AndroidViewModel(application) {
    //Chosen Directory Uri
    private val _sketchwareUri:MutableLiveData<Uri> = MutableLiveData(Uri.EMPTY)
    val sketchwareUri: LiveData<Uri> = _sketchwareUri

    //Contents of the chosen directory
    private val _directoryContents:MutableLiveData<ArrayList<DocumentFile>> = MutableLiveData(ArrayList())
    val directoryContents:LiveData<ArrayList<DocumentFile>> = _directoryContents

    init {
        getUri()
    }

    fun saveUri(uri:Uri){
        viewModelScope.launch {
            dataStore.updateData { settings ->
                settings.copy(
                    sketchwareUri = uri.toString()
                )
            }
            _sketchwareUri.postValue(uri)
        }
    }

    private fun getUri(){
        viewModelScope.launch {
            dataStore.data.collectLatest { settings ->
                if(settings.sketchwareUri == ""){
                    directoryPicker.launch(Uri.EMPTY)
                } else {
                    _sketchwareUri.postValue(Uri.parse(settings.sketchwareUri))
                }
            }
        }
    }

    fun getDirectoryContents(uri: Uri){
        try {
            val documentsTree = DocumentFile.fromTreeUri(getApplication(), uri)?: return
            val childDocuments = documentsTree.listFiles()

            viewModelScope.launch {
                val sortedItems = withContext(Dispatchers.IO){
                    childDocuments.toList().sortedBy { it.name }
                }
                //_directoryContents.postValue(sortedItems as ArrayList<DocumentFile>)
                val items:ArrayList<DocumentFile> = ArrayList()
                sortedItems.map { df ->
                    items.add(df)
                }
                _directoryContents.postValue(items)
            }
        } catch (exception:java.lang.Exception){
            throw Throwable(exception)
        }

    }
}

