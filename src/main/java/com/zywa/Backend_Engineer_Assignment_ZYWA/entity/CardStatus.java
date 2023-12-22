package com.zywa.Backend_Engineer_Assignment_ZYWA.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CardStatus")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cardId")
    private String cardId;

    @Column(name = "userContact")
    private String userContact;

    @Column(name = "timeStamp")
    private Date timeStamp;

    @Column(name = "status")
    private String status;

    public static interface IdStep {
        CardIdStep withId(Integer id);
    }

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
        CardStatus build();
    }

    public static class Builder
        implements IdStep, CardIdStep, UserContactStep, TimeStampStep, StatusStep, BuildStep {
        private Integer id;

        private String cardId;

        private String userContact;

        private Date timeStamp;

        private String status;

        private Builder() {
        }

        public static IdStep cardStatus() {
            return new Builder();
        }

        @Override
        public CardIdStep withId(Integer id) {
            this.id = id;
            return this;
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
        public CardStatus build() {
            return new CardStatus(
                this.id,
                this.cardId,
                this.userContact,
                this.timeStamp,
                this.status
            );
        }
    }
}
