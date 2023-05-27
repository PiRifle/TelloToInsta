package com.prfl.libs

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.time.Instant
import java.util.*


class TellonymClient(var token: String?) {
    private val client = OkHttpClient()

    constructor(): this(null)
    init {
        if (token == null){
            token = tellonymLogin()
        }
    }

    fun getUpdates(){

        val request: Request = Request.Builder()
            .url("https://api.tellonym.me/check/updates?limit=25")
            .header("authorization", "Bearer $token")
            .build()

        val response: Response = client.newCall(request).execute()
        val json = JSONObject(response.body?.string())
    }
    fun getTells(limit: Int = 15): List<Tell> {

        val request: Request = Request.Builder()
            .url("https://api.tellonym.me/tells?limit=$limit")
            .header("authorization", "Bearer $token")
            .build()

        val response: Response = client.newCall(request).execute()
        val json = JSONObject(response.body?.string())
        val tells = json.get("tells") as JSONArray
        return tells.filter {  it-> (it as JSONObject).get("type") == "tell"}.map {
            tell->
            Tell(
                (tell as JSONObject).get("id") as Number,
                tell.get("tell") as String,
                Date.from(Instant.parse(tell.get("createdAt") as String))
            )
        }
    }
}

fun tellonymLogin(): String {
    val options = ChromeOptions()
    options.setHeadless(false)
    options.addArguments("--remote-allow-origins=*");
    val driver = ChromeDriver(options)

    driver.get("https://tellonym.me/login")
    val wait = WebDriverWait(driver, Duration.ofSeconds(40))
    wait.until(ExpectedConditions.urlContains("tellonym.me/tells"))

    val rawJson = driver.executeScript("return localStorage.getItem('reduxPersist:__app__');") as String
    val app = JSONObject(rawJson)
    val accounts = app.get("accounts") as JSONObject
    val currentAccountId = accounts.get("activeUserId")
    val currentAccount = accounts.get(currentAccountId.toString()) as JSONObject

    driver.quit()

    return currentAccount.get("accessToken") as String
}

data class Tell(val id: Number, val tell: String, val createdAt: Date)