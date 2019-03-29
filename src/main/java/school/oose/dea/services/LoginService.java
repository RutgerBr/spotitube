package school.oose.dea.services;

import school.oose.dea.controllers.dto.LoginRequest;
import school.oose.dea.models.LoginModel;

public interface LoginService
{
    LoginModel verifyLogin(LoginRequest request);
}
