package be.kdg.programming5.repository.jparepository;

import be.kdg.programming5.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityRepository extends JpaRepository<University,String> {
    @Query("select university from University university where lower(university.name) like lower(concat('%', :searchValue, '%'))")
    List<University> findBySearchValue(String searchValue);
}
