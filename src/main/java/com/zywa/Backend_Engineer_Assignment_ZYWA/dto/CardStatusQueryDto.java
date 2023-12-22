package com.zywa.Backend_Engineer_Assignment_ZYWA.dto;

import lombok.Data;

@Data
public class CardStatusQueryDto {
    private String cardId;

    private String phoneNumber;

    public static interface CardIdStep {
        PhoneNumberStep withCardId(String cardId);
    }

    public static interface PhoneNumberStep {
        BuildStep withPhoneNumber(String phoneNumber);
    }

    public static interface BuildStep {
        CardStatusQueryDto build();
    }

    public static class Builder implements CardIdStep, PhoneNumberStep, BuildStep {
        private String cardId;

        private String phoneNumber;

        private Builder() {
        }

        public static CardIdStep cardStatusQueryDto() {
            return new Builder();
        }

        @Override
        public PhoneNumberStep withCardId(String cardId) {
            this.cardId = cardId;
            return this;
        }

        @Override
        public BuildStep withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        @Override
        public CardStatusQueryDto build() {
            CardStatusQueryDto cardStatusQueryDto = new CardStatusQueryDto();
            cardStatusQueryDto.setCardId(this.cardId);
            cardStatusQueryDto.setPhoneNumber(this.phoneNumber);
            return cardStatusQueryDto;
        }
    }
}
