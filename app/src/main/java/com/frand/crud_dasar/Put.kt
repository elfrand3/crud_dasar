package com.frand.crud_dasar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.frand.crud.networking.ApiMock
import com.frand.crud_dasar.databinding.ActivityPutBinding
import com.frand.crud_dasar.model.ResponseMock
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Put : AppCompatActivity() {
    private val binding: ActivityPutBinding by lazy {
        ActivityPutBinding.inflate(layoutInflater)
    }

    var id: String? = ""
    var title: String? = ""
    var complete: Boolean = true
    var content: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        putTodo()

        val i = intent
        id = i.getStringExtra("id")
        title = i.getStringExtra("title")
        complete = i.getBooleanExtra("complete",true)
        content = i.getStringExtra("content")
        binding.epTitle.setText(title)
        binding.epContent.setText(content)
    }

    private fun putTodo() {
        binding.btnUpdate.setOnClickListener {
            val jobUpdate = JsonObject()
            jobUpdate.addProperty("title", binding.epTitle.text.toString())
            jobUpdate.addProperty("complete", true)
            jobUpdate.addProperty("content", binding.epContent.text.toString())
            Log.d("JSON", "$jobUpdate")
            if(binding.epTitle.text.isEmpty() || binding.epContent.text.isEmpty()){
                Toast.makeText(this@Put,"update data Terlebih dahulu",Toast.LENGTH_SHORT).show()
            }else{
                ApiMock.endpoint.putMock(id.toString(),jobUpdate).enqueue(object : Callback<ResponseMock> {
                    override fun onResponse(
                        call: Call<ResponseMock>,
                        response: Response<ResponseMock>
                    ) {
                        if (response.isSuccessful){
                            Toast.makeText(this@Put,"Update TODO Succes",Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(this@Put,"Error update TODO",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseMock>, t: Throwable) {
                        Toast.makeText(this@Put,"$t",Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

}