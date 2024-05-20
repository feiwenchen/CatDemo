package com.example.catfacts.viewmodel

import androidx.databinding.ObservableField
import com.example.common_plugin.base.viewmodel.BaseViewModel
import com.example.data_plugin.model.FactResponse

class CatFactsViewModel : BaseViewModel() {

    var facts = ObservableField<FactResponse>()
}