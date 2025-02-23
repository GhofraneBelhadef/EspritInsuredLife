package com.example.donationmanagement.entities.LoanManagement;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long idCredit;
    @JsonProperty("credit_history")
    private  float credit_history ;
    private int credit_score ;
    private  String improvement_tips ;
    private String progress  ;

    public long getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(long idCredit) {
        this.idCredit = idCredit;
    }

    public float getCredit_history() {
        return credit_history;
    }

    public void setCredit_history(float credit_history) {
        this.credit_history = credit_history;
    }

    public int getCredit_score() {
        return credit_score;
    }

    public void setCredit_score(int credit_score) {
        this.credit_score = credit_score;
    }

    public String getImprovement_tips() {
        return improvement_tips;
    }

    public void setImprovement_tips(String improvement_tips) {
        this.improvement_tips = improvement_tips;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}

