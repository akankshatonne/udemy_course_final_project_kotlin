package com.carmazing.product.datasource.entity

import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import java.util.*

@Entity
@Table(name = "manufacturers")
data class Manufacturers(@Id
                         @GeneratedValue
                         val uuid: UUID,
                         @Column
                         val name: String,
                         @Column
                         val originCountry: String,
                         @Column
                         val description: String,
                         @OneToMany(mappedBy = "manufacturers")
                         @BatchSize(size = 50)
                        val series: List<Series>,

)