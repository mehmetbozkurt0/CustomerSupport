package com.customersupport.customersupport.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.customersupport.customersupport.database.AppDatabase
import com.customersupport.customersupport.network.NetworkClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class DummyResponse(
    val quote: String
)

data class UiTicket(
    val id: String,
    val title: String,
    val status: String,
    val lastMessage: String,
    val date: String
)

class TicketViewModel(database: AppDatabase) : ViewModel() {

    private val queries = database.appDatabaseQueries

    // --- DURUM YÖNETİMİ ---
    private val _tickets = MutableStateFlow<List<UiTicket>>(emptyList())
    val tickets: StateFlow<List<UiTicket>> = _tickets.asStateFlow()

    private val _isLoadingTickets = MutableStateFlow(true)
    val isLoadingTickets: StateFlow<Boolean> = _isLoadingTickets.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val httpClient = NetworkClient.httpClient

    init {
        val mevcutBiletler = queries.getAllTickets().executeAsList()
        if (mevcutBiletler.isEmpty()) {
            queries.insertTicket("#10482", "Ödeme Sistemi Hatası", "Açık", "Yeni kredi kartımla fatura bilgilerimi güncellemeye çalıştım ancak sistem...", 1L)
            queries.insertTicket("#10475", "Hesap Senkronizasyon Sorunu", "Beklemede", "Selam ekip, hala senkronizasyon hatasıyla ilgili logları bekliyoruz...", 2L)
        }

        _chatMessages.value = listOf(
            ChatMessage("1", "Merhaba! Destek ekibine ulaştığınız için teşekkürler. Size nasıl yardımcı olabilirim?", false, "10:42")
        )
    }

    fun fetchTickets() {
        viewModelScope.launch {
            _isLoadingTickets.value = true
            delay(1200)

            val dbTickets = queries.getAllTickets().executeAsList()
            _tickets.value = dbTickets.map { sqlTicket ->
                UiTicket(
                    id = sqlTicket.id,
                    title = sqlTicket.title,
                    status = sqlTicket.status,
                    lastMessage = sqlTicket.lastMessage ?: "",
                    date = "Şimdi"
                )
            }
            _isLoadingTickets.value = false
        }
    }

    fun sendMessage(text: String) {
        val userMessage = ChatMessage(
            id = (_chatMessages.value.size + 1).toString(),
            text = text,
            isFromUser = true,
            time = "Şimdi"
        )
        _chatMessages.update { currentList -> currentList + userMessage }

        getAgentReplyFromApi()
    }

    private fun getAgentReplyFromApi() {
        viewModelScope.launch {
            try {
                val response: DummyResponse = httpClient.get("https://dummyjson.com/quotes/random").body()

                delay(1000)

                // API'den gelen cevabı bas
                val agentMessage = ChatMessage(
                    id = (_chatMessages.value.size + 1).toString(),
                    text = response.quote,
                    isFromUser = false,
                    time = "Şimdi"
                )
                _chatMessages.update { currentList -> currentList + agentMessage }

            } catch (e: Exception) {
                println("Ktor error: ${e.message}")
                e.printStackTrace()
                val errorMessage = ChatMessage(
                    id = (_chatMessages.value.size + 1).toString(),
                    text = "Bağlantı hatası: Mesajınız iletilemedi.",
                    isFromUser = false,
                    time = "Şimdi"
                )
                _chatMessages.update { currentList -> currentList + errorMessage }
            }
        }
    }
}