package com.seenu.stocksapp.domain.model

import java.time.LocalDateTime

data class IntradayInfo(
    val data : LocalDateTime,
    val close : Double
)
