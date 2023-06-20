package be.kdg.programming5.repository.jparepository;

import be.kdg.programming5.domain.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampusRepository extends JpaRepository<Campus, Long> {
    @Query("select campus from Campus campus where lower(campus.name) like lower(concat('%', :searchValue, '%'))")
    List<Campus> findBySearchValue(String searchValue);
}
