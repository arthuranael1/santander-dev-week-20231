package me.dio.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "tb_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String number;

    @NotNull
    private String agency;

    @PositiveOrZero
    @Column(precision = 13, scale = 2)
    private BigDecimal balance;

    @PositiveOrZero
    @Column(name = "additional_limit", precision = 13, scale = 2)
    private BigDecimal limit;

    public Account() {
        // Construtor padrão
    }

    public Account(String number, String agency, BigDecimal balance, BigDecimal limit) {
        this.number = number;
        this.agency = agency;
        this.balance = balance;
        this.limit = limit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            this.balance = this.balance.add(amount);
        } else {
            throw new IllegalArgumentException("Amount to deposit must be positive.");
        }
    }

    public void withdraw(BigDecimal amount) {
        BigDecimal totalAvailable = this.balance.add(this.limit);
        if (amount.compareTo(BigDecimal.ZERO) > 0 && amount.compareTo(totalAvailable) <= 0) {
            this.balance = this.balance.subtract(amount);
        } else {
            throw new IllegalArgumentException("Insufficient funds or invalid amount.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(number, account.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }
}
