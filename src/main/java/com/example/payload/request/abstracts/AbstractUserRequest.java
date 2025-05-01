package com.example.payload.request.abstracts;




import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;





@SuperBuilder // Odev : bu anotazyon oncesinde , @SuperBuilder in cozdugu sorun nasil cozuluyordu.
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractUserRequest {

    @Size(min = 4, max = 16,message = "Your username should be at least 4 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your username must consist of the characters .")
    private String username;

    @Size(min = 4, max = 16,message = "Your name should be at least 4 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your name must consist of the characters .")
    private String name;

    @Size(min = 4, max = 16,message = "Your surname should be at least 4 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your surname must consist of the characters .")
    private String surname;


    @Size(min = 12, max = 12,message = "Your phone number should be 12 characters long")
    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter valid phone number")
    private String phoneNumber;


    @Email(message = "Please enter valid email")
    @Size(min=5, max=50 , message = "Your email should be between 5 and 50 chars")
    private String email;
}
