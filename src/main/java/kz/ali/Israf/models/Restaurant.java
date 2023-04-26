package kz.ali.Israf.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Restaurant")
public class Restaurant  {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn (name = "person_id",referencedColumnName = "id")
    @JsonIgnore
    private Person person;

    @Column(name="name")
    private String name;
    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "is_open")
    private boolean isOpen;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public Restaurant(Person person, String name, String description, String address, boolean isOpen) {
        this.person = person;
        this.name = name;
        this.description = description;
        this.address = address;
        this.isOpen = isOpen;
    }

    public Restaurant() {

    }
}
