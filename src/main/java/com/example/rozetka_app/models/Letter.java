package com.example.rozetka_app.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "java_letter_list")
public class Letter {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @Size(min = 10, max = 400)
    @NotNull
    private String message;

    @Column
    @Size(min = 10, max = 40)
    @NotNull
    private String senderEmail;

    @Temporal(TemporalType.DATE)
    private Date date;

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public Date getDate() {
        return date;
    }
}
