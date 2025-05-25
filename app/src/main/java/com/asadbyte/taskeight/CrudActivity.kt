package com.asadbyte.taskeight

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.asadbyte.taskeight.apiService.KtorApiService
import com.asadbyte.taskeight.apiService.ManualApiService
import com.asadbyte.taskeight.apiService.RetrofitApiService
import com.asadbyte.taskeight.model.DummyUser
import com.asadbyte.taskeight.network.RetrofitApi
import io.ktor.client.HttpClient
import io.ktor.serialization.gson.gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dummyUser = DummyUser(
    id = 1,
    name = "Asad Byte",
    email = "asadbyte@gmail.com"
)

class CrudActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var txtClientType: TextView
    private lateinit var txtResponse: TextView

    private val retrofitService = RetrofitApiService(RetrofitClient.instance)
    private val ktorService = KtorApiService(KtorClient.instance)
    private val manualService = ManualApiService()

    private lateinit var btnGet: Button
    private lateinit var btnPost: Button
    private lateinit var btnPut: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)
        initializeViews()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val clientType = intent.getStringExtra("clientType") ?: "Manual"
        txtClientType.text = "Client: $clientType"

        val apiService = when(clientType) {
            "Retrofit" -> retrofitService
            "Ktor" -> ktorService
            else -> manualService
        }
        viewModel.setApiService(apiService)
        setupClickListeners()
        setupObservers()
    }
    private fun initializeViews(){
        txtClientType = findViewById(R.id.txtClientType)
        txtResponse = findViewById(R.id.txtResponse)
        btnGet = findViewById(R.id.btnGet)
        btnPost = findViewById(R.id.btnPost)
        btnPut = findViewById(R.id.btnPut)
        btnDelete = findViewById(R.id.btnDelete)
    }
    private fun setupClickListeners() {
        btnGet.setOnClickListener { viewModel.performGet() }
        btnPost.setOnClickListener { viewModel.performPost(dummyUser) }
        btnPut.setOnClickListener { viewModel.performPut(dummyUser) }
        btnDelete.setOnClickListener { viewModel.performDelete(dummyUser.id) }
    }
    private fun setupObservers() {
        viewModel.uiState.observe(this) { state ->
            if (state.isLoading) {
                txtResponse.text = "Loading..."
                return@observe
            }

            state.result?.let { result ->
                val display = buildString {
                    append("${result.message}\n")
                    append("Status: ${result.statusCode}\n")
                    append("URL: ${result.body?.url}\n")
                    append("Origin: ${result.body?.origin}\n")
                    append("Headers:\n")
                    result.body?.headers?.forEach { (k, v) ->
                        append("• $k: $v\n")
                    }
                }
                txtResponse.text = display
            }
            state.error?.let { error ->
                txtResponse.text = "Error: $error"
            }
        }
    }
}


object RetrofitClient {
    val instance: RetrofitApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://httpbin.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitApi::class.java)
    }
}

object KtorClient {
    val instance: HttpClient by lazy {
        HttpClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                gson()
            }
        }
    }
}


