package dev.skonan.bank.dtos;

import dev.skonan.bank.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LightUserDto {

    private Integer id;

    @NotNull(message = "The first name must not be empty")
    @NotEmpty(message = "The first name must not be empty")
    @NotBlank(message = "The first name must not be empty")
    private String firstName;

    @NotNull(message = "The last name must not be empty")
    @NotEmpty(message = "The last name must not be empty")
    @NotBlank(message = "The last name must not be empty")
    private String lastName;

    public static LightUserDto fromEntity(User user) {
        return LightUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public static User toEntity(LightUserDto user) {
        return User.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
