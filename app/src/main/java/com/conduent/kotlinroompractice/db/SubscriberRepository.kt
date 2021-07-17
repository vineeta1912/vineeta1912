package com.conduent.kotlinroompractice.db

class SubscriberRepository(private val dao:SubscriberDAO) {

    val subscribers = dao.getAllSubscribers()

    suspend fun insertSubscribers(subscriber: Subscriber){
        dao.insertSubscriber(subscriber)
    }

    suspend fun updateSubscribers(subscriber: Subscriber){
        dao.updateSubscriber(subscriber)
    }

    suspend fun deleteSubscribers(subscriber: Subscriber){
        dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }


}