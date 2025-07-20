package com.ubaid.auth_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID user_id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private List<Roles> roles= Collections.singletonList(Roles.USER);
}
