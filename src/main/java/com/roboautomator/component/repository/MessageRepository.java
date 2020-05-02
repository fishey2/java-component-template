package com.roboautomator.component.repository;

import com.roboautomator.component.model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
}
