package com.prfl.app

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class InternalPrintController(val TelloDataReturn: TelloData){
    @GetMapping("/internal/print")
    fun printHandler(model: Model, @RequestParam tell: List<Number>): String {
        model.addAttribute("posts", TelloDataReturn.getTell(tell))
        return "print"
    }
}