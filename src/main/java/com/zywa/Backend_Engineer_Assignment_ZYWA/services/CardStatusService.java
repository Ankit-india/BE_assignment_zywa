package com.zywa.Backend_Engineer_Assignment_ZYWA.services;

import com.zywa.Backend_Engineer_Assignment_ZYWA.dto.CardStatusQueryDto;

public interface CardStatusService {
    String getCardStatus(CardStatusQueryDto cardStatusQueryDto);

    void processUploadedFiles();
}
