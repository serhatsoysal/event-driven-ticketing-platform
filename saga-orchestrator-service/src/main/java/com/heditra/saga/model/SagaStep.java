package com.heditra.saga.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "saga_steps")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagaStep {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saga_id")
    private SagaInstance saga;
    
    private String stepName;
    
    @Enumerated(EnumType.STRING)
    private StepStatus status;
    
    private String compensationAction;
    private LocalDateTime executedAt;
    private LocalDateTime compensatedAt;
    private String errorMessage;
}
