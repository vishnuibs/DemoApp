package com.demo.app.demoapp.repo;

import com.demo.app.demoapp.entity.InputRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputRequestRepository extends JpaRepository<InputRequest,Long> {
}
