package org.example.profile.entities;

import com.google.common.io.ByteStreams;
import org.example.profile.validators.NameValidator;
import org.example.profile.validators.PasswordValidator;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class User {
    public int id;
    public final String firstName;
    public final String nickname;
    public final String lastName;
    public final String patronymic;
    public final String password;
    public final String hobbies;
    public int enters;
    public InputStream photoStream;

    public User(int id, String firstName, String nickname, String lastName, String patronymic, String password, String hobbies,int enters) {
        this.id = id;
        this.firstName = firstName;
        this.nickname = nickname;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.password = password;
        this.hobbies = hobbies;
        this.enters = enters;
    }

    /**
     * @param request list of parameters
     * @return new user
     */
    public static User fromRequestParameters(
            HttpServletRequest request) {
        int id=-1;
        if(!Objects.equals(request.getParameter("id"), ""))
            id= Integer.parseInt(request.getParameter("id"));
        return new User(
                id,
                request.getParameter("firstname"),
                request.getParameter("nickname"),
                request.getParameter("lastname"),
                request.getParameter("patronymic"),
                request.getParameter("password"),
                request.getParameter("hobbies"),
                0
                );
    }

    /**
     * @param request for adding attributes
     */
    public void setAsRequestAttributes(HttpServletRequest request) throws IOException {
        request.setAttribute("id", id);
        request.setAttribute("firstname", firstName);
        request.setAttribute("lastname", lastName);
        request.setAttribute("patronymic", patronymic);
        request.setAttribute("hobbies",hobbies);
        request.setAttribute("photo", Base64.getEncoder().encodeToString(ByteStreams.toByteArray(photoStream)));
        request.setAttribute("enters",enters);
    }

    /**
     * @return list of errors
     */
    public List<String> validate() {
        List<String> violations = new ArrayList<>();
        if (!NameValidator.validate(nickname))
            violations.add("Длина имени от 5 символов");
        if (!PasswordValidator.validate(password)) {
            violations.add("Пароль не менеее 6 символов");
        }
        return violations;
    }
}
