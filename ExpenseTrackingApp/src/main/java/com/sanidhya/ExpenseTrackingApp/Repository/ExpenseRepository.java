package com.sanidhya.ExpenseTrackingApp.Repository;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sanidhya.ExpenseTrackingApp.Entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    //select * from tbl_expenses where category=?
    Page<Expense>findByCategory(String category,Pageable page);

    //select * from tbl_expenses name like '%keyword%'
    Page<Expense> findByNameContaining(String keyword, Pageable page);

    //select * from tbl_expenses where date between 'startDate' and 'endDate'
    Page<Expense> findByDateBetween(Date startDate, Date endDate, Pageable page);

    Page<Expense> findByUserId(Long userId,Pageable page);
}