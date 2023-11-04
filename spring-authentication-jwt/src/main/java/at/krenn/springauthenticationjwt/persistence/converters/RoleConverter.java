package at.krenn.springauthenticationjwt.persistence.converters;

import at.krenn.springauthenticationjwt.domain.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
         if(role==null) return null;
         return switch (role){
             case ROLE_USER -> "ROLE_USER";
             case ROLE_ADMIN -> "ROLE_ADMIN";
         };
    }

    @Override
    public Role convertToEntityAttribute(String dbValue) {
        if (dbValue==null) return null;
        return switch (dbValue){
            case "ROLE_USER" -> Role.ROLE_USER;
            case "ROLE_ADMIN" -> Role.ROLE_ADMIN;
            default -> throw new IllegalArgumentException(dbValue + " is unknown for User Role");
        };
    }
}
