package it.smarting.smartconditioner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import it.smarting.smartconditioner.model.Groups

class GroupsViewModel(application: Application) : AndroidViewModel(application) {
    var groupList : MutableLiveData<List<Groups>> = MutableLiveData()
}