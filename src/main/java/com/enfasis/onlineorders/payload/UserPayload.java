package com.enfasis.onlineorders.payload;

import com.enfasis.onlineorders.constants.StringExceptions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPayload {
    @NotNull
    @Email
    private String email;

    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W]).{8,20})",
            message = StringExceptions.INVALID_PASSWORD)
    @NotNull
    private String password;

}
