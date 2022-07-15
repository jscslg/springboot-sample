package com.navi.sample1.model

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "provider2")
data class Provider (
    @Id
    @Column(name = "provider_name")
    private val providerName:String,
    @Column(name = "flow_name")
    private val flowName:String,
    @Column(name = "downtime_from")
    private val downTimeFrom: LocalDateTime,
    @Column(name = "downtime_to")
    private val downTimeTo: LocalDateTime
): java.io.Serializable{
    fun toMap():Map<String,String> = mapOf(Pair("provider",providerName),Pair("flow_name",flowName),Pair("downTimeFrom",downTimeFrom.toString()),Pair("downTimeTo",downTimeTo.toString()))
}