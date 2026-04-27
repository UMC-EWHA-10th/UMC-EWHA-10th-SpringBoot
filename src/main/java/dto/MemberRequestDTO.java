package dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// MemberRequestDTO.java
public class MemberRequestDTO {

    @Getter
    @Setter
    public static class JoinDTO {
        private String email;
        private String password;
        private String name;
        private String birthDate; // 필요시 LocalDate로 변경 가능
        private String gender;
        private String region;
        private List<String> preferredCategories;
    }

    @Getter @Setter
    public static class UpdateProfileDTO {
        private String name;
        private String region;
        private List<String> preferredCategories;
    }
}