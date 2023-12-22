package com.zywa.Backend_Engineer_Assignment_ZYWA.dto;

import java.util.Date;
import lombok.Data;

@Data
public class CardStatusDto {

    private String cardId;

    private String userContact;

    private Date timeStamp;

    private String status;

    public static interface CardIdStep {
        UserContactStep withCardId(String cardId);
    }

    public static interface UserContactStep {
        TimeStampStep withUserContact(String userContact);
    }

    public static interface TimeStampStep {
        StatusStep withTimeStamp(Date timeStamp);
    }

    public static interface StatusStep {
        BuildStep withStatus(String status);
    }

    public static interface BuildStep {
        CardStatusDto build();
    }

    public static class Builder
        implements CardIdStep, UserContactStep, TimeStampStep, StatusStep, BuildStep {
        private String cardId;

        private String userContact;

        private Date timeStamp;

        private String status;

        private Builder() {
        }

        public static CardIdStep cardStatusDto() {
            return new Builder();
        }

        @Override
        public UserContactStep withCardId(String cardId) {
            this.cardId = cardId;
            return this;
        }

        @Override
        public TimeStampStep withUserContact(String userContact) {
            this.userContact = userContact;
            return this;
        }

        @Override
        public StatusStep withTimeStamp(Date timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        @Override
        public BuildStep withStatus(String status) {
            this.status = status;
            return this;
        }

        @Override
        public CardStatusDto build() {
            CardStatusDto cardStatusDto = new CardStatusDto();
            cardStatusDto.setCardId(this.cardId);
            cardStatusDto.setUserContact(this.userContact);
            cardStatusDto.setTimeStamp(this.timeStamp);
            cardStatusDto.setStatus(this.status);
            return cardStatusDto;
        }
    }
}
