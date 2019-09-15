package nl.bookshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static nl.bookshop.models.ModelStrings.*;
import static nl.bookshop.security.SecurityStrings.ROLE_ADMIN;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = USERS)
public class User {

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    @NotEmpty
    @Length(max = 127)
    private String email;

    @NotEmpty
    @Length(min = 5, max = 255)
    private String password;

    @NotNull
    private boolean isAdmin;

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public org.springframework.security.core.userdetails.User createAuthUser(String... roles) {
        return new org.springframework.security.core.userdetails.User(
                id + ":" + email,
                password,
                AuthorityUtils.createAuthorityList(roles)
        );
    }

    public static User fromUserDetails(UserDetails userDetails) {
        User user = new User();
        String[] identifiers = userDetails.getUsername().split(":", 2);
        user.setId(Integer.parseInt(identifiers[0]));
        user.setEmail(identifiers[1]);
        boolean isAdmin = userDetails
                .getAuthorities()
                .contains(new SimpleGrantedAuthority(ROLE_ADMIN));
        user.setAdmin(isAdmin);
        return user;
    }
}
