package ru.nikita.labs.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateFilterConfig {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void enableFilters() {
        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        if (sessionFactory != null) {
            try (Session session = sessionFactory.openSession()) {
                session.enableFilter("activeUserRoleFilter");
                session.enableFilter("activeRolePermissionFilter");
            }
        }
    }
}
