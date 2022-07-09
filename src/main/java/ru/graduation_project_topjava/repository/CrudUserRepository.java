package ru.graduation_project_topjava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation_project_topjava.model.User;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
