package com.heditra.saga.repository;

import com.heditra.saga.model.SagaInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SagaInstanceRepository extends JpaRepository<SagaInstance, String> {
    
    Optional<SagaInstance> findByTicketId(Long ticketId);
}
