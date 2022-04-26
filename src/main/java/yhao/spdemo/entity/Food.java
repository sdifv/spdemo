package yhao.spdemo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Proxy(lazy = false)
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fid;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private int wid;

    private List<Comment> comments;

    public Food(String name, float price, int wid) {
        this.name = name;
        this.price = price;
        this.wid = wid;
    }
}
