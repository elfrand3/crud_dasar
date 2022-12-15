package com.frand.crud_dasar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.frand.crud.networking.ApiMock
import com.frand.crud_dasar.databinding.ActivityPostBinding
import com.frand.crud_dasar.model.ResponseMock
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Post : AppCompatActivity() {

    private val binding: ActivityPostBinding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        addTo()
//        addFormData()
    }

//    private fun addFormData() {
//        ApiMock.endpoint.postMock(
//            binding.etTitle.text.toString(),
//            true,
//            binding.etContent.text.toString()
//
//        ).enqueue(object : Callback<ResponseMock>{
//            override fun onResponse(
//                call: Call<ResponseMock>,
//                response: Response<ResponseMock>
//            ) {
//                if (response.isSuccessful){
//                    Toast.makeText(this@Post,"Add TODO Succes",Toast.LENGTH_SHORT).show()
//                    finish()
//                }else{
//                    Toast.makeText(this@Post,"Error Add TODO",Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseMock>, t: Throwable) {
//                Toast.makeText(this@Post,"$t",Toast.LENGTH_SHORT).show()
//            }
//        })
//    }

    private fun addTo() {
        binding.btnAddTODO.setOnClickListener {
            var job = JsonObject()
            job.addProperty("title", binding.etTitle.text.toString())
            job.addProperty("complete", true)
            job.addProperty("content", binding.etContent.text.toString())
            Log.d("JSON", "$job")
            if(binding.etTitle.text.isEmpty() || binding.etContent.text.isEmpty()){
                Toast.makeText(this@Post,"Masukkan data Terlebih dahulu",Toast.LENGTH_SHORT).show()
            }else{
                ApiMock.endpoint.postMock(job).enqueue(object : Callback<ResponseMock>{
                    override fun onResponse(
                        call: Call<ResponseMock>,
                        response: Response<ResponseMock>
                    ) {
                        if (response.isSuccessful){
                            Toast.makeText(this@Post,"Add TODO Succes",Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(this@Post,"Error Add TODO",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseMock>, t: Throwable) {
                        Toast.makeText(this@Post,"$t",Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}