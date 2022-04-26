package yhao.spdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yhao.spdemo.entity.Food;

@Repository
public interface FoodDao extends JpaRepository<Food, Integer> {

}
