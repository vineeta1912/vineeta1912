package com.conduent.kotlinroompractice

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.conduent.kotlinroompractice.databinding.ActivityMainBinding
import com.conduent.kotlinroompractice.db.Subscriber
import com.conduent.kotlinroompractice.db.SubscriberDAO
import com.conduent.kotlinroompractice.db.SubscriberDatabase
import com.conduent.kotlinroompractice.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var subscriberViewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        val dao:SubscriberDAO = SubscriberDatabase.getInstance(application)!!.subscriberDAO
        val repository=SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.lifecycleOwner = this
        binding.myViewModel=subscriberViewModel
        initRecylcerView()


    }
    private fun displaySubscribersList(){
        subscriberViewModel.subscribers.observe(this, Observer {
            Log.e("MYTAG","-------"+it.toString())
            //lambda expression
            val selectedItem={selectedItem:Subscriber-> listItemClicked(selectedItem)}
            binding.subscriberRecyclerView.adapter=MyRecyclerViewAdapter(it,selectedItem)
        })

    }

    private fun initRecylcerView(){
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
    }

    private fun listItemClicked(subscriber:Subscriber){
        Toast.makeText(this,"The clicked item is {${subscriber.name}",Toast.LENGTH_LONG).show()
        subscriberViewModel.initUpdateOrDelete(subscriber)
    }
}