package com.service.user.repository;

import com.service.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findUserByEmail(String email);

    UserEntity findUserByUserId(String userId);

    @Query(value = "select * from Users u where u.EMAIL_VERIFICATION_STATUS = 'true'", nativeQuery = true)
    Page<UserEntity> findAllUsersWithConfirmedEmailAddress(Pageable pageableRequest);

    @Query(value = "select * from Users u where u.USER_FIRST_NAME = ?1", nativeQuery = true)
    List<UserEntity> findUserByFirstName(String firstName);

    @Query(value = "select * from Users u where u.USER_LAST_NAME = :lastName", nativeQuery = true)
    List<UserEntity> findUserByLastName(@Param("lastName") String lastName);

    @Query(value = "select * from Users u where u.USER_FIRST_NAME LIKE %:keyWord% or u.USER_LAST_NAME LIKE %:keyWord%", nativeQuery = true)
    List<UserEntity> findUsersByKeyWord(@Param("keyWord") String keyWord);

    @Query(value = "select u.USER_FIRST_NAME, u.USER_LAST_NAME from Users u where u.USER_FIRST_NAME LIKE %:keyWord% or u.USER_LAST_NAME LIKE %:keyWord%", nativeQuery = true)
    List<Object[]> findUserFirstNamesAndLastNameByKeyWord(@Param("keyWord") String keyWord);

    @Modifying
    @Transactional
    @Query(value = "update Users u set u.EMAIL_VERIFICATION_STATUS=:emailVerificationStatus where u.USER_ID=:userId", nativeQuery = true)
    void updateUserEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus,
                                           @Param("userId") String userId);

    @Query("select user from UserEntity user where user.userId=:userId")
    UserEntity findUserEntityByUserId(@Param("userId") String userId);

    @Query("select user.firstName, user.lastName from UserEntity user where user.userId=:userId")
    List<Object[]> getUserEntityFullNameByUserId(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("update UserEntity u set u.emailVerificationStatus =:emailVerificationStatus where u.userId=:userId")
    void updateUserEntityEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus,
                                                 @Param("userId") String userId);

}
