package com.carmazing.product.datasource.repository

import com.carmazing.product.datasource.entity.Manufacturers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.UUID

interface ManufacturerRepository: JpaRepository<Manufacturers, UUID>, JpaSpecificationExecutor<Manufacturers>