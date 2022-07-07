package com.navi.sample1.controller

import com.navi.sample1.exception.ResourceNotFoundException
import com.navi.sample1.model.Downtime
import com.navi.sample1.model.Provider
import com.navi.sample1.repository.ProviderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.configurationprocessor.json.JSONArray
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
import java.net.http.HttpResponse
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1")
class ProviderController {
    @Autowired
    private lateinit var providerRepository: ProviderRepository

    @GetMapping("providers")
    fun getAllProviders() = JSONObject(mapOf(Pair("providers",providerRepository.findAll().map { it.toMap() }))).toString(4)

    @GetMapping("provider")
    fun getProvider(@RequestParam("provider") providerName:String): ResponseEntity<String> {
        // Throw Exception if ID does not exist else return provider details
        return ResponseEntity.ok(JSONObject(providerRepository.findById(providerName).orElseThrow { ResourceNotFoundException("Provider not found with name: $providerName") }.toMap()).toString())
    }

    @PostMapping("provider")
    fun createProvider(@RequestParam("provider") providerName:String, @RequestParam("flow_name") flowName: String,@RequestBody downtime:Downtime): ResponseEntity<String> {
        // If that name already exists
        if(providerRepository.findById(providerName).isPresent) return updateProvider(providerName,flowName,downtime)
        // Else create new provider
        val newProvider = Provider(providerName,flowName,LocalDateTime.parse(downtime.downFrom), LocalDateTime.parse(downtime.downTo))
        providerRepository.save(newProvider)
        return ResponseEntity.ok(JSONObject(newProvider.toMap()).toString(4))
    }

    @PutMapping("provider")
    fun updateProvider(@RequestParam("provider") providerName:String, @RequestParam("flow_name") flowName: String,@RequestBody downtime:Downtime): ResponseEntity<String> {
        // Throw Exception if ID does not exist
        if(providerRepository.findById(providerName).isEmpty) throw ResourceNotFoundException("Provider not found with name: $providerName")

        val updateProvider = Provider(providerName,flowName,LocalDateTime.parse(downtime.downFrom), LocalDateTime.parse(downtime.downTo))
        providerRepository.save(updateProvider)
        return ResponseEntity.ok(JSONObject(updateProvider.toMap()).toString(4))
    }

    @DeleteMapping("provider")
    fun deleteProvider(@RequestParam("provider") providerName:String): ResponseEntity.BodyBuilder {
        // Throw Exception if ID does not exist
        if(providerRepository.findById(providerName).isEmpty) throw ResourceNotFoundException("Provider not found with name: $providerName")
        providerRepository.deleteById(providerName)
        return ResponseEntity.ok()
    }
}