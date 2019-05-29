package school.oose.dea.services;

import school.oose.dea.models.LoginRequestModel;
import school.oose.dea.models.LoginModel;

public interface LoginService
{
    LoginModel verifyLogin(LoginRequestModel request);
}
