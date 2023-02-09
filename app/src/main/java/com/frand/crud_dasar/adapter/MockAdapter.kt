package com.frand.crud_dasar.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.frand.crud.networking.ApiMock
import com.frand.crud_dasar.Put
import com.frand.crud_dasar.R
import com.frand.crud_dasar.model.DataItem
import com.frand.crud_dasar.model.ResponseMock
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MockAdapter (private var listData:  ArrayList<DataItem>):
    RecyclerView.Adapter<MockAdapter.myviewHolder>() {

    class myviewHolder (itemView: View ) : RecyclerView.ViewHolder(itemView){
        val tl : TextView = itemView.findViewById(R.id.tv_title)
        val ct : TextView = itemView.findViewById(R.id.tv_content)
        val cm : TextView = itemView.findViewById(R.id.tv_complete)
        val sampah : Button = itemView.findViewById(R.id.delete)
        val update : Button = itemView.findViewById(R.id.update)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return myviewHolder(view)

    }

    override fun onBindViewHolder(holder: myviewHolder, position: Int) {
        val data = listData[position]
        holder.tl.text = data.title
        holder.ct.text = data.content
        var isComplete = ""
        isComplete = if (data.complete){
            "true"
        }else{
            "false"
        }
        holder.cm.text = isComplete
//        holder.pr.text = data.price.toString()
//        Glide.with(holder.itemView)
//            .load("${data.thumbnail}")
//            .apply (RequestOptions.overrideOf(150,150)).into(holder.th)
        holder.update.setOnClickListener {
            val i = Intent(holder.itemView.context, Put::class.java)
            i.putExtra("id", data.id)
            i.putExtra("title", data.title)
            i.putExtra("content", data.content)
            i.putExtra("complete", data.complete)
            holder.itemView.context.startActivity(i)
        }

        holder.sampah.setOnClickListener{
            ApiMock.endpoint.delMock("${data.id}").enqueue(object : Callback<ResponseMock>{
                override fun onResponse(
                    call: Call<ResponseMock>,
                    response: Response<ResponseMock>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(
                            holder.itemView.context, "Delete TODO Succes", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(
                        holder.itemView.context, "Gagal TODO ", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseMock>, t: Throwable) {
                    Toast.makeText(holder.itemView.context,"$t",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun getItemCount() = listData.size

    fun getMock(data: List<DataItem>){
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }
}