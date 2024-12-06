package com.carmazing.product.datasource.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "features")
data class Features(@Id
                    @GeneratedValue
                    val uuid: UUID,
                    @Column
                    val name: String,
                    @Column
                    val activeByDefault: Boolean,
                    @Column
                    val activeByRequest: Boolean,
                    @Column
                    val installationPrice: Int,
                    @Column
                    val isSafety: Boolean,
                    @Column
                    val isConvenience: Boolean,
                    @Column
                    val isPerformance: Boolean,
                    @Column
                    val isDisplay : Boolean,
                    @Column
                    val isEntertainment : Boolean,

    )