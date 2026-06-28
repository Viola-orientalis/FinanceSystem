package com.exam.dto;

import org.apache.ibatis.type.Alias;

@Alias("AccountDTO")
public class AccountDTO {

    private Long id;
    private String userid;
    private String accountNumber;
    private String ownerName;
    private Long balance;

    public AccountDTO() {
    }

    public AccountDTO(Long id, String userid, String accountNumber, String ownerName, Long balance) {
        this.id = id;
        this.userid = userid;
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", balance=" + balance +
                '}';
    }
}