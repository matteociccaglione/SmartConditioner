package it.smarting.smartconditioner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import it.smarting.smartconditioner.model.Group

class SingleGroupViewModel(application: Application) : AndroidViewModel(application) {
    var group : MutableLiveData<Group> = MutableLiveData()
}