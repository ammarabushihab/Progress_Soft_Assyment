package com.progressoftassesment.progressoftassesment.repository;

import com.progressoftassesment.progressoftassesment.model.FxDeals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FxDealRepository extends JpaRepository<FxDeals,Long> {


}
