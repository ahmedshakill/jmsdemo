package jms.app.repository;

import jms.app.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    List<Sales> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
