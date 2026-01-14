package eu.ase.ro.spring_mpai.response;

import java.util.List;

/**
 * CourseResponse este un DTO (Data Transfer Object) folosit pentru:
 * - a trimite datele despre curs din Service -> Controller -> View
 *
 * IMPORTANT:
 * - NU este Entity JPA
 * - NU este salvat în baza de date
 * - este doar un "container de date" pentru UI
 */
public class CourseResponse {

    /**
     * ID-ul cursului.
     * Vine din entity-ul Course (course.getId()).
     * Este folosit:
     * - pentru editare
     * - pentru link-uri (/courses/{id}/edit)
     */
    private Long id;

    /**
     * Titlul cursului.
     * Observație:
     * - în entity se numește "name"
     * - în response se numește "title"
     *   (diferența de naming e ok, dar trebuie mapată manual)
     */
    private String title;

    /**
     * Numele trainerului cursului.
     */
    private String trainer;

    /**
     * Descrierea cursului.
     */
    private String description;

    /**
     * Lista de studenți înscriși la curs.
     *
     * Tip:
     * - List<StudentResponse>, NU List<Student>
     *
     * Motiv:
     * - nu vrei să expui entity-urile direct în view
     * - eviți probleme de lazy loading și dependențe DB în UI
     */
    private List<StudentResponse> students;

    /**
     * Constructor gol (no-args).
     *
     * FOARTE IMPORTANT:
     * - Spring / Thymeleaf îl folosesc pentru data binding
     * - când creezi: new CourseResponse() în controller (ADD course)
     */
    public CourseResponse() {
    }

    /**
     * Constructor complet.
     *
     * Folosit în Service când:
     * - transformi Course (entity) -> CourseResponse (DTO)
     *
     * Toate câmpurile sunt setate explicit.
     */
    public CourseResponse(Long id,
                          String title,
                          String trainer,
                          String description,
                          List<StudentResponse> students) {
        this.id = id;
        this.title = title;
        this.trainer = trainer;
        this.description = description;
        this.students = students;
    }

    /**
     * Getter pentru id.
     * Folosit de:
     * - view (ex: ${course.id})
     * - controller (pentru a construi link-uri)
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter pentru id.
     * Folosit la:
     * - binding automat (ex: formulare)
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter pentru title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter pentru title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter pentru trainer.
     */
    public String getTrainer() {
        return trainer;
    }

    /**
     * Setter pentru trainer.
     */
    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    /**
     * Getter pentru description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter pentru description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter pentru lista de studenți.
     *
     * Notă:
     * - dacă lista e goală, view-ul trebuie să funcționeze fără erori
     */
    public List<StudentResponse> getStudents() {
        return students;
    }

    /**
     * Setter pentru lista de studenți.
     */
    public void setStudents(List<StudentResponse> students) {
        this.students = students;
    }

    /**
     * toString()
     *
     * Folosit mai ales pentru:
     * - debugging
     * - log-uri (System.out.println / logger)
     *
     * NU este folosit de Spring MVC pentru afișare în view.
     */
    @Override
    public String toString() {
        return "CourseResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", trainer='" + trainer + '\'' +
                ", description='" + description + '\'' +
                ", students=" + students +
                '}';
    }
}
