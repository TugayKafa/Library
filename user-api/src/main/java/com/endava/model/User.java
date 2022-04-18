package com.endava.model;


import com.endava.exception.validator.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "egn", nullable = false, unique = true, columnDefinition = "varchar(10)")
    private String egn;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.ACTIVE;

    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "full_address")
    private String fullAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason_of_deactivation")
    private Reason reason;

    @Column(name = "date_of_deactivation")
    private LocalDate dateOfDeactivation;

    @Column(name = "date_of_last_log_in")
    private LocalDate lastLogIn;
}
