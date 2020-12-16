package com.przemkeapp.housingassociationapp.dao;

import com.przemkeapp.housingassociationapp.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
