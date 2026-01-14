package eu.ase.ro.spring_mpai.response;

/**
 * StudentResponse este un DTO (Data Transfer Object) folosit pentru:
 * - a trimite date despre student din Service -> Controller -> View
 *
 * IMPORTANT:
 * - NU este Entity JPA
 * - NU este salvat în baza de date
 * - NU conține logică de business
 */
public class StudentResponse {

    /**
     * ID-ul studentului.
     * Vine din entity-ul Student (student.getId()).
     * Este util pentru:
     * - identificare
     * - viitoare operații (edit / delete / enroll)
     */
    private Long id;

    /**
     * Numele complet al studentului.
     */
    private String name;

    /**
     * Vârsta studentului.
     *
     * Folosești Integer (nu int) pentru:
     * - a permite valoare null (ex: student fără vârstă setată)
     * - compatibilitate cu binding Spring
     */
    private Integer age;

    /**
     * Constructor gol (no-args).
     *
     * FOARTE IMPORTANT:
     * - necesar pentru Spring și Thymeleaf
     * - folosit la data binding și la creare obiecte goale
     */
    public StudentResponse() {
    }

    /**
     * Constructor complet.
     *
     * Folosit în Service atunci când:
     * - transformi Student (entity) -> StudentResponse (DTO)
     */
    public StudentResponse(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    /**
     * Getter pentru id.
     * Folosit de:
     * - view (ex: ${student.id})
     * - debug / log-uri
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter pentru id.
     * Folosit la:
     * - binding automat
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter pentru name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter pentru name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter pentru age.
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Setter pentru age.
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * toString()
     *
     * Folosit doar pentru:
     * - debugging
     * - log-uri
     *
     * NU influențează afișarea în UI.
     */
    @Override
    public String toString() {
        return "StudentResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
