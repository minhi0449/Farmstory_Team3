package com.farmstory.repository;

import com.farmstory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findUserByUidAndPass(String uid, String pass);

    public int countByUid(String uid);
    public int countByNick(String nick);
    public int countByEmail(String email);
    public int countByHp(String hp);

    User findByUid(String uid);

}
