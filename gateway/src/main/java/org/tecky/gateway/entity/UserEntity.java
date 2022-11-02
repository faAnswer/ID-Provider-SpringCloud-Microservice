package org.tecky.gateway.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("t_user")
public class UserEntity {
    @Id
    @Column("uid")
    private int uid;

    @Column("roles")
    private String roles;

    @Column("username")
    private String username;

    @Column("shapassword")
    private String shapassword;

    @Column("salt")
    private String salt;

    @Column("email")
    private String email;

}
