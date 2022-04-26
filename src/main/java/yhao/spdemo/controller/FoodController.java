package yhao.spdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import yhao.spdemo.dao.FoodDao;
import yhao.spdemo.entity.Food;
import yhao.spdemo.entity.Result;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {
    @Autowired
    private FoodDao foodDao;

    @GetMapping("/")
    public Result defaultRes() {
        return  Result.OK("welcome to query food!");
    }

    @GetMapping("/getById/{fid}")
    public Food getOneFood(@PathVariable("fid") int fid) {
        return foodDao.getById(fid);
    }

    @GetMapping("/getByRange")
    public Result getBatchFood(
            @RequestParam(value = "start", required = true) int start,
            @RequestParam(value = "size", required = false) int size
    ) {
        PageRequest pageRequest = PageRequest.of(start, size);
        Page<Food> page = foodDao.findAll(pageRequest);
        List<Food> foodList = page.getContent();
        return Result.OK(foodList);
    }

    @PostMapping("/save")
    public Result saveOneFood(@RequestBody Food food) {
        System.out.println("get: " + food);
        Food ret = foodDao.save(food);
        return Result.OK("save: " + ret);
    }
}
