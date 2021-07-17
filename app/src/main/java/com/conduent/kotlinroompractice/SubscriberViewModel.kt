package com.conduent.kotlinroompractice

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.conduent.kotlinroompractice.db.Subscriber
import com.conduent.kotlinroompractice.db.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository):ViewModel(),Observable{
    val subscribers = repository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete : Subscriber

    @Bindable
    var inputName = MutableLiveData<String>()
    @Bindable
    var inputEmail = MutableLiveData<String>()
    @Bindable
    var saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    var clearOrDeleteButtonText = MutableLiveData<String>()
    init{
        saveOrUpdateButtonText.value ="Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate(){
        if(isUpdateOrDelete){
            subscriberToUpdateOrDelete.name=inputName.value!!
            subscriberToUpdateOrDelete.email=inputEmail.value!!
            update(subscriberToUpdateOrDelete)
        }else {
            val name: String = inputName.value!!
            val email: String = inputEmail.value!!
            insert(Subscriber(0, name, email))
            inputEmail.value = null
            inputName.value = null
        }

    }

    fun initUpdateOrDelete(subscriber: Subscriber){
        inputEmail.value=subscriber.email
        inputName.value=subscriber.name
        isUpdateOrDelete=true
        subscriberToUpdateOrDelete =subscriber
        saveOrUpdateButtonText.value ="Update"
        clearOrDeleteButtonText.value = "Delete"

    }

    fun clearAllOrDelete(){
        if(isUpdateOrDelete){
            delete(subscriberToUpdateOrDelete)
        }else {
            clearrAll()
        }
    }

    fun insert(subscriber:Subscriber){
        viewModelScope.launch { repository.insertSubscribers(subscriber) }
    }

    fun update(subscriber:Subscriber){
        viewModelScope.launch { repository.updateSubscribers(subscriber)
        }
    }
    fun delete(subscriber:Subscriber){
        viewModelScope.launch { repository.deleteSubscribers(subscriber)
            inputEmail.value=null
            inputName.value=null
            isUpdateOrDelete=false
            saveOrUpdateButtonText.value ="Save"
            clearOrDeleteButtonText.value = "Clear All"
        }

    }

    fun clearrAll(){
        viewModelScope.launch { repository.deleteAll() }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}