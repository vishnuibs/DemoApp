package com.demo.app.demoapp.repo;

import com.demo.app.demoapp.entity.InputRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InputRequestRepository extends JpaRepository<InputRequest,Long> {
}
