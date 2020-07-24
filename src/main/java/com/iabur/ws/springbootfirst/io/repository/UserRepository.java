package com.iabur.ws.springbootfirst.io.repository;

import com.iabur.ws.springbootfirst.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
}