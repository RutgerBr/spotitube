package school.oose.dea.services;

import school.oose.dea.datasources.dao.LoginDAO;

import javax.inject.Inject;

public class TokenServiceImpl implements TokenService
{
    private LoginDAO loginDao;

    @Override
    public boolean isTokenValid(String token)
    {
        var user = loginDao.getUserByToken(token);

        return null != user;
    }

    @Inject
    public void setLoginDao(LoginDAO loginDao)
    {
        this.loginDao = loginDao;
    }
}
