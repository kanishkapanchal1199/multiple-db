package com.demo.db.multipledb.mysql.repository;

import com.demo.db.multipledb.mysql.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    public User findByIdAndEmail(Integer id,String email);
}
