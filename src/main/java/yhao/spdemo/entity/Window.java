package yhao.spdemo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Proxy(lazy = false)
public class Window {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wid;

    @Column(nullable = false, length = 20)
    private String name;

    @Column
    private int floor;

    @Column(nullable = false, length = 10)
    private String cName;

    @Column(nullable = false, length = 20)
    private String sName;
}
