package com.demo.app.demoapp.repo;


import com.demo.app.demoapp.entity.BillingHeader;
import org.springframework.stereotype.Repository;
import  org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BillingHeaderRepository extends JpaRepository<BillingHeader, Long> {

}
