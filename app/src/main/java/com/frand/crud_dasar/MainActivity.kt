package com.frand.crud_dasar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.frand.crud.networking.ApiMock
import com.frand.crud_dasar.adapter.MockAdapter
import com.frand.crud_dasar.databinding.ActivityMainBinding
import com.frand.crud_dasar.model.DataItem
import com.frand.crud_dasar.model.ResponseMock
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val MockAdapter = MockAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.addTo.setOnClickListener{
            startActivity(Intent(this, Post::class.java))
        }

        supportActionBar?.hide()
        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list.adapter = MockAdapter
        getMock()

        binding.idRefresh.setOnRefreshListener{
            getMock()
        }
        getMock()
    }

    override fun onStart() {
        super.onStart()
        getMock()
    }

    private fun getMock() {
        ApiMock.endpoint.getMock().enqueue(object : Callback<ResponseMock> {
            override fun onResponse(call: Call<ResponseMock>, response: Response<ResponseMock>) {
                if(response.isSuccessful){
                    val responseMock:ResponseMock? = response.body()
                    onResultData(responseMock!!)
                }
            }

            override fun onFailure(call: Call<ResponseMock>, t: Throwable) {
            }

        })
    }

    private fun onResultData(responseMock: ResponseMock) {
        val vertical = responseMock.data
        MockAdapter.getMock(vertical as List<DataItem>)
    }
}