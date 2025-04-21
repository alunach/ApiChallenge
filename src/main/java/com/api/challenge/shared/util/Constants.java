package com.api.challenge.shared.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String DEFAULT_PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d).{8,}$";

    public static final String ERROR_EMAIL_EXISTS = "El correo ya registrado";
    public static final String ERROR_INVALID_EMAIL = "El correo no tiene un formato válido.";
    public static final String ERROR_INVALID_PASSWORD = "La contraseña no cumple con el formato requerido.";
}
