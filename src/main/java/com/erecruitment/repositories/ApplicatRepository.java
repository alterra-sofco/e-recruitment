package com.erecruitment.repositories;

import com.erecruitment.entities.Applicant;
import com.erecruitment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ApplicatRepository extends JpaRepository<Applicant, Long> {

    @Query( "select x from Applicant x where x.ownedBy = :user")
    Optional<Applicant> findByOwnedBy(@Param("user") User user);

}
