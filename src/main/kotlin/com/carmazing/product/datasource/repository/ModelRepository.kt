package com.carmazing.product.datasource.repository

import com.carmazing.product.datasource.entity.Models
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.*

interface ModelRepository: JpaRepository<Models, UUID>, JpaSpecificationExecutor<Models> {
}