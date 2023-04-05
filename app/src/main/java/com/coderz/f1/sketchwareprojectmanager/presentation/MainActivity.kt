package com.coderz.f1.sketchwareprojectmanager.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModelProvider
import com.coderz.f1.sketchwareprojectmanager.databinding.ActivityMainBinding
import com.coderz.f1.sketchwareprojectmanager.data.AppSettingsSerializer
import com.coderz.f1.sketchwareprojectmanager.presentation.components.RecyclerItemSpacerDecoration
import com.coderz.f1.sketchwareprojectmanager.presentation.factories.MainViewModelFactory
import com.google.android.material.snackbar.Snackbar

val Context.dataStore by dataStore("settings.json", AppSettingsSerializer)

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    private val directoryPicker = registerForActivityResult(
        ActivityResultContracts.OpenDocumentTree()){ uri ->
        if (uri != null) {
            contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                viewModel.saveUri(uri)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dirAdapter = DirectoryAdapter{df ->
            Snackbar.make(binding.recycler,df.name.toString(),Snackbar.LENGTH_SHORT).show()
        }
        val mainViewModelFactory = MainViewModelFactory(application,dataStore, directoryPicker)
        viewModel = ViewModelProvider(this,mainViewModelFactory)[MainViewModel::class.java]

        viewModel.sketchwareUri.observe(this){uri ->
            if (uri == null || uri.toString() == "") return@observe
            viewModel.getDirectoryContents(uri)
        }


        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = dirAdapter
        binding.recycler.addItemDecoration(RecyclerItemSpacerDecoration(8))
        viewModel.directoryContents.observe(this){items ->
            dirAdapter.submitList(items)
        }

    }

}