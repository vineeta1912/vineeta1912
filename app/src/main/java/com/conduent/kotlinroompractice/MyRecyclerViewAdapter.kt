package com.conduent.kotlinroompractice

import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.conduent.kotlinroompractice.databinding.ListItemBinding
import com.conduent.kotlinroompractice.db.Subscriber

class MyRecyclerViewAdapter(private val subscribersList: List<Subscriber>,private val clickedListener:(Subscriber)->Unit):RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflator = LayoutInflater.from(parent.context)
        val binding:ListItemBinding=
            DataBindingUtil.inflate(layoutInflator,R.layout.list_item,parent,false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
         holder.bind(subscribersList[position],clickedListener)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }
}

class MyViewHolder(val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(subscriber: Subscriber,clickedListener:(Subscriber)->Unit){
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text =subscriber.email
        binding.listItemLayout.setOnClickListener {
            clickedListener(subscriber)
        }

    }

   /* public fun <T> MutableList<T>.removeAllTo(predicate: (T) -> Boolean): Boolean = getItDone(predicate, true)

    private fun <T> MutableList<T>.getItDone(predicate: (T) -> Boolean, sendItToMe:Boolean):Boolean{

        return predicate(this[0])
    }*/

}