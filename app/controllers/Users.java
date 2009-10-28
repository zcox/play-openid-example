package controllers;

import models.*;
import play.mvc.*;

public class Users extends Controller
{
    public static void edit()
    {
        User user = current();
        render(user);
    }

    public static void update(String firstName, String lastName, String email)
    {
        User user = current();
        user.firstName = firstName;
        user.lastName = lastName;
        user.email = email;
        user.save();
        edit();
    }

    //TODO pull this out into something reusable
    static User current()
    {
        User current = null;
        String idString = session.get("user");
        if (idString != null)
            current = User.findById(Long.parseLong(idString));
        return current;
    }
}
