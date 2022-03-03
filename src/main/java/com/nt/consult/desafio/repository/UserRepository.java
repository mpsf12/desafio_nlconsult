package com.nt.consult.desafio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nt.consult.desafio.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
