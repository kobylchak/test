package ua.test.dao;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Modules")
public class Module {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private List<Question> modules = new ArrayList<>();

    public Module(String name) {
        this.name = name;
    }

    public Module() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getModules() {
        return modules;
    }

    public void setModules(List<Question> modules) {
        this.modules = modules;
    }
}
