package com.navi.sample1.service

import com.navi.sample1.exception.ResourceNotFoundException
import com.navi.sample1.model.Downtime
import com.navi.sample1.model.Provider
import com.navi.sample1.repository.ProviderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ProviderService(
    @Autowired private val providerRepository: ProviderRepository
    ){
    fun getAllProviders(): Collection<Map<String,String>>{
        return providerRepository.findAll().map { it.toMap() }
    }

    fun getProvider(id: String): Map<String,String> {
        return providerRepository.findById(id).orElseThrow { ResourceNotFoundException("Provider not found with name: $id") }.toMap()
    }

    fun createProvider(providerName: String, flowName: String, downtime: Downtime): Map<String,String> {
        // update if provider already exists
        if(providerRepository.findById(providerName).isPresent) return updateProvider(providerName,flowName,downtime)
        // Else create new provider
        val newProvider = Provider(providerName,flowName, LocalDateTime.parse(downtime.downFrom), LocalDateTime.parse(downtime.downTo))
        providerRepository.save(newProvider)
        return newProvider.toMap()
    }

    fun updateProvider(id: String, flowName: String, downtime: Downtime): Map<String,String>{
        if(providerRepository.findById(id).isEmpty) throw ResourceNotFoundException("Provider not found with name: $id")
        val updateProvider = Provider(id,flowName,LocalDateTime.parse(downtime.downFrom), LocalDateTime.parse(downtime.downTo))
        providerRepository.save(updateProvider)
        return updateProvider.toMap()
    }

    fun deleteProvider(id: String){
        if(providerRepository.findById(id).isEmpty) throw ResourceNotFoundException("Provider not found with name: $id")
        providerRepository.deleteById(id)
    }
}