package com.heditra.saga.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "saga_instances")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SagaInstance {
    
    @Id
    private String sagaId;
    
    private Long ticketId;
    
    @Enumerated(EnumType.STRING)
    private SagaStatus status;
    
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private String compensationReason;
    
    @OneToMany(mappedBy = "saga", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SagaStep> steps = new ArrayList<>();
    
    public void addStep(SagaStep step) {
        steps.add(step);
        step.setSaga(this);
    }
}
