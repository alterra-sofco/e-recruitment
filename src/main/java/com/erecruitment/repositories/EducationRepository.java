package com.erecruitment.repositories;

import com.erecruitment.entities.Education;
import com.erecruitment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface EducationRepository extends JpaRepository<Education, Long> {

    @Query( "select x from Education x where x.ownedBy = :user")
    Set<Education> findByOwnedBy(@Param("user") User user);

    @Query( "select x from Education x where x.educationId = :educationId and x.ownedBy = :user")
    Optional<Education> findByIdaAndOwnedBy(@Param("educationId") Long educationId,@Param("user") User user);
}
