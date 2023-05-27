package com.prfl.app

import com.prfl.libs.Tell
import com.prfl.libs.TellonymPersistence
import jakarta.servlet.http.HttpServletRequest
import org.openqa.selenium.Dimension
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.io.FileInputStream
import java.util.*

@Controller
class TelloData{
    val client = TellonymPersistence()
    val tells: List<Tell>
        get() = client.getPosts()
    fun getTell(id: Number): Tell{
        return client.getPosts(listOf(id))[0]
    }
    fun getTell(id: List<Number>): List<Tell>{
        return client.getPosts(id)
    }
}

@Controller
class PrintOutputController(){
    @PostMapping("/print")
    fun print(@RequestParam tell: List<Number>, request: HttpServletRequest): ResponseEntity<InputStreamResource> {
        val options = ChromeOptions()
        options.setHeadless(true)
        options.addArguments("--remote-allow-origins=*", "--hide-scrollbars");
        val driver = ChromeDriver(options)
        driver.manage().window().size = Dimension(1000,1000)
        val requestUrl: String = tell.joinToString("&") { "tell=$it" }
        driver.get("http://${request.serverName}:${request.serverPort}/internal/print?$requestUrl")
        val screenshot = (driver as TakesScreenshot).getScreenshotAs(OutputType.FILE)
        val imageResource = InputStreamResource(FileInputStream(screenshot))
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .contentLength(screenshot.length())
            .body(imageResource)
    }
}
@Controller
class TelloController(val TelloDataReturn: TelloData) {
    @GetMapping("/sup")
    fun myHandler(model: Model): String {
        model.addAttribute("posts", TelloDataReturn.tells)
        return "posts"
    }
}
