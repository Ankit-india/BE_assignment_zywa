package com.zywa.Backend_Engineer_Assignment_ZYWA.convertor;

import com.zywa.Backend_Engineer_Assignment_ZYWA.dto.CardStatusDto;
import com.zywa.Backend_Engineer_Assignment_ZYWA.entity.CardStatus;
import org.springframework.stereotype.Component;

@Component
public class CardStatusConvertor {

    public CardStatus convert(CardStatusDto cardStatusDto) {
        return CardStatus.Builder.cardStatus()
            .withId(null)
            .withCardId(cardStatusDto.getCardId())
            .withUserContact(cardStatusDto.getUserContact())
            .withTimeStamp(cardStatusDto.getTimeStamp())
            .withStatus(cardStatusDto.getStatus())
            .build();
    }
}
