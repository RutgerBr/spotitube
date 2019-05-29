package school.oose.dea.services;

import school.oose.dea.models.LoginRequestModel;
import school.oose.dea.datasources.dao.LoginDAO;
import school.oose.dea.models.LoginModel;

import javax.inject.Inject;

public class LoginServiceImpl implements LoginService
{
    private LoginDAO loginDAO;

    @Override
    public LoginModel verifyLogin(LoginRequestModel request)
    {
        loginDAO.updateTokenForUser(request.getUser());

        return loginDAO.getLoginInfo(request.getUser(), request.getPassword());
    }

    @Inject
    public void setLoginDAO(LoginDAO loginDAO)
    {
        this.loginDAO = loginDAO;
    }
}
