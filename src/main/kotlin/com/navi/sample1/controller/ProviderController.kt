package com.navi.sample1.controller

import com.navi.sample1.exception.ResourceNotFoundException
import com.navi.sample1.model.Downtime
import com.navi.sample1.model.Provider
import com.navi.sample1.service.ProviderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1")
class ProviderController(@Autowired private val providerService: ProviderService) {
    @GetMapping("providers")
    fun getAllProviders() = ResponseEntity.ok(providerService.getAllProviders())

    @GetMapping("provider")
    fun getProvider(@RequestParam("provider") providerName:String): ResponseEntity<Map<String,String>> = try {
        ResponseEntity.ok(providerService.getProvider(providerName))
    } catch (ex:Exception){
        throw ex
    }

    @PostMapping("provider")
    fun createProvider(@RequestParam("provider") providerName:String, @RequestParam("flow_name") flowName: String,@RequestBody downtime:Downtime): ResponseEntity<Map<String,String>> {
        return ResponseEntity.ok( providerService.createProvider(providerName,flowName,downtime))
    }

    @PutMapping("provider")
    fun updateProvider(@RequestParam("provider") providerName:String, @RequestParam("flow_name") flowName: String,@RequestBody downtime:Downtime): ResponseEntity<Map<String,String>> {
        return ResponseEntity.ok(providerService.updateProvider(providerName,flowName,downtime))
    }

    @DeleteMapping("provider")
    fun deleteProvider(@RequestParam("provider") providerName:String): ResponseEntity<Unit> =  try {
        providerService.deleteProvider(providerName)
        ResponseEntity<Unit>(HttpStatus.NO_CONTENT)
    } catch (ex: Exception){
        throw ex
    }
}