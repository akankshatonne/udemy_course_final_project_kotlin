package com.carmazing.product.datasource.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "engines")
data class Engine (@Id
                   @GeneratedValue
                   val uuid: UUID,
                   @Column
                   val description: String,
                   @Column
                   val horsePower: Int,
                   @Column
                   val torque: Int,
                   @Column
                   val capacityCc: Int,

)