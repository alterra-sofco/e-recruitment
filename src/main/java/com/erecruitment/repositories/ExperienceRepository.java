package com.erecruitment.repositories;

import com.erecruitment.entities.Education;
import com.erecruitment.entities.Experience;
import com.erecruitment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query( "select x from Experience x where x.ownedBy = :user")
    Set<Experience> findByOwnedBy(@Param("user") User user);

    @Query( "select x from Experience x where x.experienceId = :experienceId and x.ownedBy = :user")
    Optional<Experience> findByIdaAndOwnedBy(@Param("experienceId") Long experienceId, @Param("user") User user);
}
