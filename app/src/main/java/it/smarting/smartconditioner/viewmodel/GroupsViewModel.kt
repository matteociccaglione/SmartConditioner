package it.smarting.smartconditioner.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import it.smarting.smartconditioner.model.Group

class GroupsViewModel(application: Application) : AndroidViewModel(application) {
    var groupList : MutableLiveData<List<Group>> = MutableLiveData()
}