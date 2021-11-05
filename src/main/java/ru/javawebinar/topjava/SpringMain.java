package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            adminUserController.create(new User(null, "user5", "email5@mail.ru", "password1", Role.USER));
            adminUserController.create(new User(null, "user4", "email4@mail.ru", "password1", Role.USER));
            adminUserController.create(new User(null, "user3", "email3@mail.ru", "password1", Role.USER));
            adminUserController.create(new User(null, "user1", "email6@mail.ru", "password1", Role.USER));
            adminUserController.create(new User(null, "user2", "email2@mail.ru", "password1", Role.USER));
            adminUserController.create(new User(null, "user1", "email1@mail.ru", "password1", Role.USER));

            adminUserController.getAll().forEach(user -> System.out.printf("ALL - ID - %d NAME - %s EMAIL - %s %n", user.getId(), user.getName(), user.getEmail()));
            adminUserController.delete(5);
            adminUserController.getAll().forEach(user -> System.out.printf("DELETE - ID - %d NAME - %s EMAIL - %s %n", user.getId(), user.getName(), user.getEmail()));
            User getUser = adminUserController.get(3);
            System.out.printf("GET - ID - %d NAME - %s EMAIL - %s %n", getUser.getId(), getUser.getName(), getUser.getEmail());
            User getUserMail = adminUserController.getByMail("email3@mail.ru");
            System.out.printf("GETMail - ID - %d NAME - %s EMAIL - %s %n", getUserMail.getId(), getUserMail.getName(), getUserMail.getEmail());
            User getUserUpdate = adminUserController.get(2);
            getUserUpdate.setEmail("new_email5@mail.ru");
            getUserUpdate.setName("Update User");
            adminUserController.update(getUserUpdate, 2);
            adminUserController.getAll().forEach(user -> System.out.printf("UPDATE - ID - %d NAME - %s EMAIL - %s %n", user.getId(), user.getName(), user.getEmail()));
        }
    }
}
