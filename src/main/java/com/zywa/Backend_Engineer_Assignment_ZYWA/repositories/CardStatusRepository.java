package com.zywa.Backend_Engineer_Assignment_ZYWA.repositories;

import com.zywa.Backend_Engineer_Assignment_ZYWA.entity.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardStatusRepository extends JpaRepository<CardStatus, Integer> {

    @Query(value = "SELECT status FROM card_status WHERE user_contact = ?1", nativeQuery = true)
    String getCardStatusByUserContact(String mobileNumber);

    @Query(value = "SELECT status FROM card_status WHERE card_id = ?1", nativeQuery = true)
    String getCardStatusByCardId(String cardId);

    @Query(value = "SELECT * FROM card_status WHERE card_id = ?1", nativeQuery = true)
    CardStatus getCardByCardId(String cardId);
}
