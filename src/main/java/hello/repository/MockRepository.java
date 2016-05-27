package hello.repository;

import hello.model.MockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by orbot on 28.05.16.
 */
public interface MockRepository extends JpaRepository<MockEntity, Long> {

    @Query(value = "SELECT MAX(CAST(some_Param as numeric)) from Mock_Entity " +
            "where some_Param >= '0' and some_Param < ':'", nativeQuery = true)
    Long getMaxSomeParam();
}
