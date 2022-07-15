package com.navi.sample1.repository

import com.navi.sample1.model.Provider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface ProviderRepository : JpaRepository<Provider, String>