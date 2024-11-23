package com.virtualNumber.virtualNumber.repositores;

import com.virtualNumber.virtualNumber.entites.VirtualPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<VirtualPhoneNumber, Long> {
}
