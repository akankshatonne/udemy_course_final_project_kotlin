package com.carmazing.product.datasource.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "characteristics")
data class Characteristics(@Id
                            @GeneratedValue
                            val uuid: UUID,
                           @Column
                            val name: String,

)