package school.oose.dea.services;

import school.oose.dea.controllers.dto.LoginRequest;
import school.oose.dea.datasources.dao.LoginDAO;
import school.oose.dea.models.LoginModel;

import javax.inject.Inject;

import java.sql.SQLException;

public class LoginServiceImpl implements LoginService
{
    private LoginDAO loginDAO;

    @Override
    public LoginModel verifyLogin(LoginRequest request)
    {
        loginDAO.updateTokenForUser(request.getUser());

        var resultSet = loginDAO.getLoginInfo(request.getUser(), request.getPassword());
        var model = new LoginModel();

        try
        {
            if (resultSet.next())
            {
                model.setUser(resultSet.getString("USERNAME"));
                model.setToken(resultSet.getString("TOKEN"));

            } else
            {
                return null;
            }
        } catch (
                SQLException e)
        {
            System.out.println("Error reading resultset");
        }
        return model;
    }

    @Inject
    public void setLoginDAO(LoginDAO loginDAO)
    {
        this.loginDAO = loginDAO;
    }
}
