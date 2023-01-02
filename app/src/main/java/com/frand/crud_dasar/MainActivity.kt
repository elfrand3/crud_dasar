package com.frand.crud_dasar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.SearchView.*
import android.widget.Toast
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

        binding.btnSearch.setOnClickListener {
            if (binding.edtText.text.isEmpty()){
                Toast.makeText(this, "search...", Toast.LENGTH_SHORT).show()
            }else{
                binding.shimmer.startShimmer()
                binding.rvList.visibility = View.GONE
                binding.shimmer.visibility = View.VISIBLE
                ApiMock.endpoint.getMock(title = binding.edtText.text.toString()).enqueue(object : Callback<ResponseMock> {
                    override fun onResponse(call: Call<ResponseMock>, response: Response<ResponseMock>) {
                        if(response.isSuccessful){
                            binding.shimmer.stopShimmer()
                            binding.rvList.visibility = View.VISIBLE
                            binding.shimmer.visibility = View.GONE
                            val responseMock:ResponseMock? = response.body()
                            onResultData(responseMock!!)
                        }
                    }

                    override fun onFailure(call: Call<ResponseMock>, t: Throwable) {
                        binding.shimmer.stopShimmer()
                        binding.shimmer.visibility = View.GONE
                    }

                })
            }
        }

        binding.idRefresh.setOnRefreshListener{
            getMock()
        }
        getMock()
    }

    override fun onStart() {
        super.onStart()
        binding.shimmer.startShimmer()
        binding.shimmer.visibility = View.VISIBLE
        ApiMock.endpoint.getMock().enqueue(object : Callback<ResponseMock> {
            override fun onResponse(call: Call<ResponseMock>, response: Response<ResponseMock>) {
                if(response.isSuccessful){
                    binding.shimmer.stopShimmer()
                    binding.shimmer.visibility = View.GONE
                    val responseMock:ResponseMock? = response.body()
                    onResultData(responseMock!!)
                }
            }

            override fun onFailure(call: Call<ResponseMock>, t: Throwable) {
                binding.shimmer.stopShimmer()
                binding.shimmer.visibility = View.GONE
            }

        })
        getMock()
    }

    private fun getMock() {
//        binding.spinner.onItemSelectedListener = object : OnItemSelectedListener{
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                when(p2){
//                    0 -> {
//                        ApiMock.endpoint.getMock().enqueue(object : Callback<ResponseMock> {
//                            override fun onResponse(call: Call<ResponseMock>, response: Response<ResponseMock>) {
//                                if(response.isSuccessful){
//                                    val responseMock:ResponseMock? = response.body()
//                                    onResultData(responseMock!!)
//                                }
//                            }
//
//                            override fun onFailure(call: Call<ResponseMock>, t: Throwable) {
//                            }
//
//                        })
//                    }
//                    1 -> {
//                        ApiMock.endpoint.getMock(true,).enqueue(object : Callback<ResponseMock> {
//                            override fun onResponse(call: Call<ResponseMock>, response: Response<ResponseMock>) {
//                                if(response.isSuccessful){
//                                    val responseMock:ResponseMock? = response.body()
//                                    onResultData(responseMock!!)
//                                }
//                            }
//
//                            override fun onFailure(call: Call<ResponseMock>, t: Throwable) {
//                            }
//
//                        })
//                    }
//                    2 -> {
//                        ApiMock.endpoint.getMock(false).enqueue(object : Callback<ResponseMock> {
//                            override fun onResponse(call: Call<ResponseMock>, response: Response<ResponseMock>) {
//                                if(response.isSuccessful){
//                                    val responseMock:ResponseMock? = response.body()
//                                    onResultData(responseMock!!)
//                                }
//                            }
//
//                            override fun onFailure(call: Call<ResponseMock>, t: Throwable) {
//                            }
//
//                        })
//                    }
//                }
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//        }
    }

    private fun onResultData(responseMock: ResponseMock) {
        val vertical = responseMock.data
        MockAdapter.getMock(vertical as List<DataItem>)
    }
}