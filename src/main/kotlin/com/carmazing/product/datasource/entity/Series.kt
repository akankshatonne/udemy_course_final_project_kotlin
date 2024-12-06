package com.carmazing.product.datasource.entity


import jakarta.persistence.*
import org.hibernate.annotations.BatchSize
import java.util.*

@Entity
@Table(name = "series")
data class Series(@Id
             @GeneratedValue
             val uuid: UUID,
             @Column
             val name: String,

             @OneToMany
             @JoinColumn(name = "series_uuid")
             @BatchSize(size = 50)
             val characteristics: List<Characteristics>,

             @ManyToOne
            @JoinColumn(name = "manufacturer_uuid")
            val manufacturers: Manufacturers,

            @OneToMany(mappedBy = "series")
            @BatchSize(size = 50)
            val models: List<Models>,


             )