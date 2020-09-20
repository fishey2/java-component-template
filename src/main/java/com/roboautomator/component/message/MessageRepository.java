package com.roboautomator.component.message;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    Optional<MessageEntity> findAllByCorrelationId(@Param("correlationId") UUID correlationId);
}
