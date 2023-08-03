package com.ozanarik.MvvmNewsApp.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity(tableName = "articles_db.db")
data class Article(

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    @Expose
    val author: String? = null, // Jon Porter
    @Expose
    val content: String? = null, // At the Microsoft Inspire partner event today, the Windows maker announced pricing for its AI-infused Copilot for Microsoft 365. The suite of contextual artificial intelligence tools, the fruit of the… [+2469 chars]
    @Expose
    val description: String = "", // At the Microsoft Inspire partner event today, the Windows maker announced pricing for its AI-infused Copilot for Microsoft 365.
    @Expose
    val publishedAt: String = "", // 2023-07-18T15:31:59Z
    @Expose
    val source: Source = Source(),
    @Expose
    val title: String = "", // Microsoft will charge businesses $30 per user for its 365 AI Copilot - Engadget
    @Expose
    val url: String = "", // https://www.engadget.com/microsoft-will-charge-businesses-30-per-user-for-its-365-ai-copilot-153042654.html
    @Expose
    val urlToImage: String = "" // https://s.yimg.com/uu/api/res/1.2/v6_iVgu1J1TSDWG5LoPNOw--~B/Zmk9ZmlsbDtoPTYzMDtweW9mZj0wO3c9MTIwMDthcHBpZD15dGFjaHlvbg--/https://media-mbst-pub-ue1.s3.amazonaws.com/creatr-uploaded-images/2023-07/8ad97820-24c1-11ee-8ef3-4952b6077bf1.cf.jpg
):Serializable