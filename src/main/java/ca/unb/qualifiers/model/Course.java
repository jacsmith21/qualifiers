package ca.unb.qualifiers.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String section;

    @ManyToMany
    private List<User> students;

    @ManyToOne
    private User instructor;

    @OneToMany(mappedBy = "course")
    private List<Deliverable> deliverables;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }

    public boolean hasSubmission(Deliverable deliverable) {
        if(deliverables == null) {
            return false;
        }

        for(Deliverable s : deliverables) {
            if(deliverable.getId().equals(s.getId())) {
                return true;
            }
        }

        return false;
    }

    public List<Deliverable> getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(List<Deliverable> deliverables) {
        this.deliverables = deliverables;
    }
}
