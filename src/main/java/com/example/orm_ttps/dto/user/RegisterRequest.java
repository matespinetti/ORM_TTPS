package com.example.orm_ttps.dto.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RegisterRequest {
    @NotBlank(message = "El dni es obligatorio")
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    private String surname;

    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;




    @NotBlank(message = "El rol de usuario es obligatorio")
    @Pattern(regexp = "CLIENT|ADMIN|WORKER", message = "El rol de usuario debe ser CLIENT, ADMIN o WORKER")
    private String role;

}
