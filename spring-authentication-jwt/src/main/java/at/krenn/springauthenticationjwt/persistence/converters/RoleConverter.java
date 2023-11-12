package at.krenn.springauthenticationjwt.persistence.converters;

import at.krenn.springauthenticationjwt.domain.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter to handle database conversion for user roles
 */
@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
         if(role==null) return null;
         return switch (role){
             case USER -> "USER";
             case ADMIN -> "ADMIN";
         };
    }

    @Override
    public Role convertToEntityAttribute(String dbValue) {
        if (dbValue==null) return null;
        return switch (dbValue){
            case "USER" -> Role.USER;
            case "ADMIN" -> Role.ADMIN;
            default -> throw new IllegalArgumentException(dbValue + " is unknown for User Role");
        };
    }
}
