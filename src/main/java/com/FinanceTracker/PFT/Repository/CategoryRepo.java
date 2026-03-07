package com.FinanceTracker.PFT.Repository;

import com.FinanceTracker.PFT.Entities.Category;
import com.FinanceTracker.PFT.Enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
    List<Category> findByUserUidAndType(Long uId, CategoryType type);
    List<Category> findByType(CategoryType type);

}
